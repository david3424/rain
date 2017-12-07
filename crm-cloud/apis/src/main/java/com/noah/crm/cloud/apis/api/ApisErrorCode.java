package com.noah.crm.cloud.apis.api;

import com.noah.crm.cloud.apis.exception.IErrorCode;

/**
 * @author xdw9486
 */
public enum ApisErrorCode implements IErrorCode {

    BAD_REQUEST(400, "请求的参数个数或格式不符合要求"),
    INVALID_ARGUMENT(400, "请求的参数不正确"),
    UNAUTHORIZED(401, "无权访问"),
    FORBIDDEN(403, "禁止访问"),
    NOT_FOUND(404, "请求的地址不正确"),
    NOT_FOUND_S(404, "请求的地址不正确,%s"),
    METHOD_NOT_ALLOWED(405, "不允许的请求方法"),
    NOT_ACCEPTABLE(406, "不接受的请求"),
    CONFLICT(409, "资源冲突"),
    UNSUPPORTED_MEDIA_TYPE(415, "不支持的Media Type"),
    INTERNAL_ERROR(500, "服务器内部错误"),
    SERVICE_UNAVAILABLE_(503, "服务不可用,%s"),
    GATEWAY_TIMEOUT(504, "请求服务超时");

    private int status;
    private String message;

    ApisErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public static ApisErrorCode fromHttpStatus(int httpStatus) {
        for (ApisErrorCode errorCode : values()) {
            if (errorCode.getStatus() == httpStatus) {
                return errorCode;
            }
        }
        return INTERNAL_ERROR;
    }


    @Override
    public int getStatus() {
        return status;
    }


    @Override
    public String getRespCode() {
        return this.name();
    }

    @Override
    public String getRespMsg() {
        return message;
    }
}
