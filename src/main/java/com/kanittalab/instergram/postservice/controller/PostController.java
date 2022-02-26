package com.kanittalab.instergram.postservice.controller;

import com.kanittalab.instergram.postservice.constant.CommonConstants;
import com.kanittalab.instergram.postservice.exception.BusinessException;
import com.kanittalab.instergram.postservice.exception.FileNotFoundException;
import com.kanittalab.instergram.postservice.model.CommonResponse;
import com.kanittalab.instergram.postservice.model.Post;
import com.kanittalab.instergram.postservice.model.request.PostRequest;
import com.kanittalab.instergram.postservice.model.response.PostListResponse;
import com.kanittalab.instergram.postservice.service.PostService;
import jdk.jfr.ContentType;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import java.io.IOException;
import java.net.URI;
import java.util.List;

@Slf4j
@Controller
@Validated
public class PostController {

    @Autowired
    @Qualifier("postService")
    private PostService postService;

    @Autowired
    private HttpHeaders headers;

    /*
        POST /posts - Create new post
     */
    @RequestMapping(path = "/posts", method = RequestMethod.POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<CommonResponse> createPost(@RequestHeader("userid") String userId,
                                         @RequestPart  @NonNull MultipartFile imageFile,
                                       @RequestParam @Max(250)  String caption) throws BusinessException, IOException {

        log.info("Received a posting request for userId : {}", userId);

        if(imageFile.isEmpty()){
            throw  new FileNotFoundException("Post image not found");
        }

        PostRequest postRequest = new PostRequest(caption,imageFile);

        Post post = postService.createPost(postRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/posts/{id}")
                .buildAndExpand(post.getUid()).toUri();

        return ResponseEntity
                .created(location)
                .body(new CommonResponse(CommonConstants.STATUS_CODE.STATUS_CODE_SUCCESS, "Post created successfully"));
    }

    /*
        DELETE /posts/{id} - Delete post by ID
        TODO : To allow only authorized user to called this endpoint
     */
    @DeleteMapping("/posts/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@RequestHeader("userid") String userId, @PathVariable("id") String id) {
        log.info("Received request to delete a post id {} posted by userId {}", id, userId);
        postService.deletePost(id, userId);
    }


    /*
        GET /posts - List user posts
     */
    @GetMapping("/posts")
    public ResponseEntity<CommonResponse> findUserPosts(@RequestHeader("userid") String userId) {
        log.info("Received request to list a post for user {}", userId);

        List<Post> posts = postService.getPostsByUserId(userId);

        log.info("Found {} posts for user {}",posts !=null ?  posts.size() : 0, userId);

        return ResponseEntity.ok(new CommonResponse(CommonConstants.STATUS_CODE.STATUS_CODE_SUCCESS, "Post created successfully",new PostListResponse(posts)));
    }


    /*
        PATCH  /posts/{id} - Update post by ID
     */
    @PatchMapping("/posts/{id}")
    public ResponseEntity<CommonResponse> updatePost(@RequestHeader("userid") String userId,
                                        @RequestBody PostRequest postRequest) throws IOException {

        log.info("Received an updating request for userId : {}", userId);

        Post post = postService.createPost(postRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/posts/{id}")
                .buildAndExpand(post.getUid()).toUri();

        return ResponseEntity
                .created(location)
                .body(new CommonResponse(CommonConstants.STATUS_CODE.STATUS_CODE_SUCCESS, "Post updated successfully"));
    }

}