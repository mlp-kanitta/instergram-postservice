package com.kanittalab.instergram.postservice.service;

import com.kanittalab.instergram.postservice.constant.CommonConstants;
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

import java.util.Base64;

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

    void createPost_exception() throws Exception {
        String caption = "new caption";
        
        PostRequest postRequest = new PostRequest("caption",null);
        when(postRepository.insert(any(Post.class))).thenThrow(new RuntimeException());

        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            postService.createPost(postRequest,"userTest");
        });

    }


}