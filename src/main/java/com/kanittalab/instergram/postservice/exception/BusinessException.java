package com.kanittalab.instergram.postservice.exception;

import lombok.Data;

@Data
public class BusinessException extends Exception{

    public BusinessException(String code,String message){
        super(message);
        this.code  = code;
    }
    private String code;
}
