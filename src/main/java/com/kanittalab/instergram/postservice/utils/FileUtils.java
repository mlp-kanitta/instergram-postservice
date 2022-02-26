package com.kanittalab.instergram.postservice.utils;

import org.springframework.web.multipart.MultipartFile;

public class FileUtils {
    private FileUtils(){
        throw new IllegalStateException("FileUtils class");
    }
    public static boolean isImageFile(MultipartFile file) {
        String mimeType = file.getContentType();
        if(null== mimeType){
            return false;
        }
        return  mimeType.startsWith("image/");
    }
}
