package com.kanittalab.instergram.postservice.utils;

import io.netty.util.internal.StringUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileUtils {
    private FileUtils(){
        throw new IllegalStateException("FileUtils class");
    }
    public static boolean isImageFile(MultipartFile file) {
        String mimeType = file.getContentType();
        return !StringUtils.isEmpty(mimeType) && mimeType.startsWith("image/");
    }
}
