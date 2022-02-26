package com.kanittalab.instergram.postservice.service;

import com.kanittalab.instergram.postservice.constant.CommonConstants;
import com.kanittalab.instergram.postservice.exception.BusinessException;
import com.kanittalab.instergram.postservice.model.Post;
import com.kanittalab.instergram.postservice.model.request.PostRequest;
import com.kanittalab.instergram.postservice.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
class PostServiceTest {
    @Autowired
    PostService postService;

    @MockBean
    PostRepository postRepository;

    @Test
    void createPost_success() throws Exception {
        String caption = "new caption";

        MockMultipartFile imageFile
                = new MockMultipartFile(
                "imageFile",
                "test.jpeg",
                MediaType.IMAGE_JPEG_VALUE,
                "test".getBytes()
        );

        PostRequest postRequest = new PostRequest("caption",imageFile);
        Post expected = new Post();
        expected.setCaption(caption);
        expected.setImage(Base64.getEncoder().encodeToString(imageFile.getBytes()));

        when(postRepository.insert(any(Post.class))).thenReturn(expected);

        assertDoesNotThrow(() -> postService.createPost(postRequest,"userTest"));

    }

    @Test
    void createPost_exception() throws Exception {
        String caption = "new caption";

        PostRequest postRequest = new PostRequest("caption",null);
        when(postRepository.insert(any(Post.class))).thenThrow(new RuntimeException());

        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            postService.createPost(postRequest,"userTest");
        });

    }

    @Test
    void updatePost_success() throws Exception {
        String caption = "updated caption";
        UUID postId = UUID.randomUUID();

        MockMultipartFile imageFile
                = new MockMultipartFile(
                "imageFile",
                "test.jpeg",
                MediaType.IMAGE_JPEG_VALUE,
                "test".getBytes()
        );

        Post expected = new Post();
        expected.setCaption(caption);
        expected.setImage(Base64.getEncoder().encodeToString(imageFile.getBytes()));

        when(postRepository.findById(postId)).thenReturn(Optional.of(expected));
        when(postRepository.save(any(Post.class))).thenReturn(expected);

        assertDoesNotThrow(() -> postService.updatePost(postId.toString(),caption));

    }

    @Test
    void updatePost_postNotFound() throws Exception {
        String caption = "updated caption";
        UUID postId = UUID.randomUUID();

        MockMultipartFile imageFile
                = new MockMultipartFile(
                "imageFile",
                "test.jpeg",
                MediaType.IMAGE_JPEG_VALUE,
                "test".getBytes()
        );

        Post expected = new Post();
        expected.setCaption(caption);
        expected.setImage(Base64.getEncoder().encodeToString(imageFile.getBytes()));

        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            postService.updatePost(postId.toString(),caption);
        });

        assertEquals(CommonConstants.STATUS.STATUS_CODE_RECORD_NOT_FOUND,exception.getCode());

    }

    @Test
    void getPostsByUserId_success() throws Exception {
        String userId = "userTest";

        MockMultipartFile imageFile
                = new MockMultipartFile(
                "imageFile",
                "test.jpeg",
                MediaType.IMAGE_JPEG_VALUE,
                "test".getBytes()
        );

        List<Post> posts = new ArrayList<Post>();
        Post post1 = new Post();
        post1.setCaption("\"test caption\"");
        post1.setUserId("userTest");
        post1.setUid(UUID.randomUUID());
        posts.add(post1);

        when( postRepository.findPostByUserId(userId)).thenReturn(posts);

        assertDoesNotThrow(() -> postService.getPostsByUserId(userId));

    }

    @Test
    void deletePostById_success() throws Exception {
        String userId = "userTest";
        UUID postId = UUID.randomUUID();


        MockMultipartFile imageFile
                = new MockMultipartFile(
                "imageFile",
                "test.jpeg",
                MediaType.IMAGE_JPEG_VALUE,
                "test".getBytes()
        );

        Post expected = new Post();
        expected.setCaption("caption");
        expected.setImage(Base64.getEncoder().encodeToString(imageFile.getBytes()));

        when(postRepository.findById(postId)).thenReturn(Optional.of(expected));

        assertDoesNotThrow(() -> postService.deletePost(postId.toString(),userId));

    }

    @Test
    void deletePostById_postNotFound() throws Exception {
        String userId = "userTest";
        UUID postId = UUID.randomUUID();

        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            postService.deletePost(postId.toString(),userId);
        });

        assertEquals(CommonConstants.STATUS.STATUS_CODE_RECORD_NOT_FOUND,exception.getCode());

    }

}