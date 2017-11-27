package com.noah.crm.cloud.apis.exception;


public enum ErrorCode implements IErrorCode {


    /**
     * SERVER_ERROR
     * 下划线表示需要补充
     */
    SERVER_ERROR(500, "System error"),
    SERVICE_EXCEPTION_(600, "service exception :%s"),

    REQUEST_ENTITY_TOO_LARGE(10000, "Request Entity too large"),

    AUTH_FAILED(10001, "Authorization failure"),

    PARAM_ILLEGAL_(10002, "Parameter illegal:%s"),

    METHOD_NOT_SUPPORED(10003, "Method is not suppored"),

    PROTOCAL_INVALID(10004, "Invalid JSON-RPC 2.0 request"),

    POLICY_OTHER_FAIL_(10005, "other error：%s");

    private int status;
    private String message;

    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }

    @Override
    public String getRespCode() {
        return this.name();
    }

    @Override
    public String getRespMsg() {
        return message;
    }

    @Override
    public int getStatus() {
        return status;
    }
}
