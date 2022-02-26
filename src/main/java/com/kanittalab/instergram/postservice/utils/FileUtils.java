package com.kanittalab.instergram.postservice.utils;

import org.springframework.web.multipart.MultipartFile;

public  class FileUtils {
    public static boolean isImageFile(MultipartFile file){
        String mimeType = file.getContentType();
        if (mimeType.startsWith("image/")) {
            return true;
        }
        return false;
    }
}
