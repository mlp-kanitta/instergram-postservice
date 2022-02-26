package com.kanittalab.instergram.postservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Max;

@Data
@AllArgsConstructor
public class PostRequest {
    @Max(250)
    private String caption;

    private MultipartFile imageFile;
}
