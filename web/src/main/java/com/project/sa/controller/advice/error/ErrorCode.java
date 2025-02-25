package com.project.sa.controller.advice.error;

public class ErrorCode {
    public static final int RESOURCE_NOT_FOUND = 40401;
    public static final int RESOURCE_ALREADY_EXIST = 40901;
    public static final int BAD_REQUEST_VALUE = 40001;
    public static final int BAD_REQUEST_PATH = 40002;
    public static final int BAD_REQUEST_PARAM_ABSENT = 40003;
    public static final int METHOD_NOT_ALLOWED = 40501;
    public static final int INTERNAL_SERVER_ERROR = 50001;

    private ErrorCode() {
    }
}
