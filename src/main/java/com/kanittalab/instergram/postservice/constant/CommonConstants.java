package com.kanittalab.instergram.postservice.constant;

public class CommonConstants {
    public class STATUS {

        private STATUS(){
            throw new IllegalStateException("STATUS class");
        }
        public static final String STATUS_CODE_SUCCESS = "1000";
        public static final String STATUS_CODE_RECORD_NOT_FOUND = "4000";
        public static final String STATUS_CODE_GENERAL_ERROR = "9000";
        public static final String STATUS_CODE_MISSING_MANDATORY_HEADER = "9001";
        public static final String STATUS_CODE_VALIDATION_ERROR = "9002";
        public static final String STATUS_CODE_FILE_EXCEED = "9003";
        public static final String STATUS_CODE_INVALID_FILE_TYPE = "9004";
    }
}
