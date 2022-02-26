package com.kanittalab.instergram.postservice.service;

import com.kanittalab.instergram.postservice.constant.CommonConstants;
import com.kanittalab.instergram.postservice.exception.BusinessException;
import com.kanittalab.instergram.postservice.model.Post;
import com.kanittalab.instergram.postservice.model.request.PostRequest;
import com.kanittalab.instergram.postservice.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service("postService")
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CassandraTemplate cassandraTemplate;

    public String getReleaseVersion() {
        return cassandraTemplate
                .getCqlOperations()
                .queryForObject("select release_version from system.local", String.class);
    }

    public Post createPost(PostRequest postRequest,String userId) throws IOException {
        Post post = new Post();
        post.setCaption(postRequest.getCaption());
        post.setContentType(postRequest.getImageFile().getContentType());
        post.setImage( Base64.getEncoder().encodeToString(postRequest.getImageFile().getBytes()));
        post.setUserId(userId);

        return postRepository.insert(post);
    }

    public void deletePost(String id, String userId) throws BusinessException {
        Post post = this.getPostById(id);

        if(null == post){
            throw new BusinessException(CommonConstants.STATUS_CODE.STATUS_CODE_RECORD_NOT_FOUND,"Post not found");
        }
        postRepository.deletePostbyId(UUID.fromString(id),userId);
    }

    public List<Post> getPostsByUserId(String userId) {
        return postRepository.findPostByUserId(userId);
    }

    public Post updatePost(String postId, String caption, String userId) throws BusinessException {
        Post post = this.getPostById(postId);

        if(null == post){
            throw new BusinessException(CommonConstants.STATUS_CODE.STATUS_CODE_RECORD_NOT_FOUND,"Post not found");
        }

        post.setCaption(caption);
        post.setUpdatedDate(Instant.now());
        return postRepository.save(post);
    }


    public Post getPostById(String id) {
        Optional<Post> postOptional = postRepository.findById(UUID.fromString(id));
        if(postOptional!=null && postOptional.isPresent()) {
            return postOptional.get();
        }
        return null;
    }

}
