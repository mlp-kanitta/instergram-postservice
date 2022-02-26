package com.kanittalab.instergram.postservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kanittalab.instergram.postservice.constant.CommonConstants;
import com.kanittalab.instergram.postservice.model.Post;
import com.kanittalab.instergram.postservice.model.request.PostRequest;
import com.kanittalab.instergram.postservice.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PostService postService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void post_whenCaptionIsExceed_thenReturnsStatus400() throws Exception {
        String caption = "testcaptionloggerthan250_testttttttttttttttttttttttttestcaptionloggerthan250_testttttttttttttttttttttttttestcaptionloggerthan250_testttttttttttttttttttttttttestcaptionloggerthan250_testttttttttttttttttttttttttestcaptionloggerthan250_testttttttttttttttttttttttttestcaptionloggerthan250_testttttttttttttttttttttttttestcaptionloggerthan250_testttttttttttttttttttttttttestcaptionloggerthan250_testttttttttttttttttttttttt";

        MockMultipartFile imageFile
                = new MockMultipartFile(
                "imageFile",
                "test.jpeg",
                MediaType.IMAGE_JPEG_VALUE,
                "test".getBytes()
        );

        mvc.perform(multipart("/posts")
                .file(imageFile)
                .param("caption",caption)
                .header("userid", "userTest"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value(CommonConstants.STATUS_CODE.STATUS_CODE_VALIDATION_ERROR));
    }

    @Test
    void post_whenFileIsEmpty_thenReturnsStatus400() throws Exception {
        String caption = "test caption";


        mvc.perform(multipart("/posts")
                .param("caption",caption)
                .header("userid", "userTest"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void post_whenFileIsNotImage_thenReturnsStatus400() throws Exception {
        String caption = "test caption";

        MockMultipartFile imageFile
                = new MockMultipartFile(
                "imageFile",
                "test.pdf",
                MediaType.APPLICATION_PDF_VALUE,
                "test".getBytes()
        );

        mvc.perform(multipart("/posts")
                .file(imageFile)
                .param("caption",caption)
                .header("userid", "userTest"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value(CommonConstants.STATUS_CODE.STATUS_CODE_INVALID_FILE_TYPE));
    }

    @Test
    void post_success_200() throws Exception {
        String caption = "test caption";

        MockMultipartFile imageFile
                = new MockMultipartFile(
                "imageFile",
                "test.jpeg",
                MediaType.IMAGE_JPEG_VALUE,
                "test".getBytes()
        );

        when(postService.createPost(any(),anyString())).thenReturn(new Post());

        mvc.perform(multipart("/posts")
                .file(imageFile)
                .param("caption",caption)
                .header("userid", "userTest"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("code").value(CommonConstants.STATUS_CODE.STATUS_CODE_SUCCESS));
    }

}