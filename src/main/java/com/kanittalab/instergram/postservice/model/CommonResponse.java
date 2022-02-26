package com.kanittalab.instergram.postservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class CommonResponse<T extends Serializable> implements Serializable {
    private String code;
    private String message;
    @JsonIgnore
    protected T data;

    public CommonResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
