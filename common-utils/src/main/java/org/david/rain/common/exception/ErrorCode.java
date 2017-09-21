package org.david.rain.common.exception;


public enum ErrorCode implements IErrorCode {


    /**
     * SERVER_ERROR
     */
    SERVER_ERROR(500, "System error"),
    SERVICE_EXCEPTION(600, "System error"),

    REQUEST_ENTITY_TOO_LARGE(10000, "Request Entity too large"),

    AUTH_FAILED(10001, "Authorization failure"),

    PARAM_ILLEGAL(10002, "Parameter illegal:%s"),

    METHOD_NOT_SUPPORED(10003, "Method is not suppored"),

    PROTOCAL_INVALID(10004, "Invalid JSON-RPC 2.0 request"),

    POLICY_OTHER_FAIL(10005, "other error：%s");

    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getRespCode() {
        return code;
    }

    @Override
    public String getRespMsg() {
        return message;
    }
}
