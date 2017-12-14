package com.noah.crm.cloud.apis.exception;

import com.noah.crm.cloud.apis.api.ApisErrorCode;

/**
 * hystrix会忽略这个异常, 不会触发熔断
 *
 * @author xdw9486
 */
public class RemoteCallException extends RuntimeException {


    private static final IErrorCode DEFAULT_CODE = ApisErrorCode.INTERNAL_ERROR;
    private static final long serialVersionUID = -1412116779572909757L;

    private String code = DEFAULT_CODE.getRespCode();

    private int httpStatus = DEFAULT_CODE.getStatus();

    public RemoteCallException(String code, int httpStatus, String message) {
        super(message);
        this.code = code;
        this.httpStatus = httpStatus;
    }

    public RemoteCallException(String message) {
        super(message);
    }

    /**
     * @param errorCode 状态码, 这个字段会在错误信息里返回给客户端.
     * @param message
     */
    public RemoteCallException(ErrorCode errorCode, String message) {
        this(errorCode.getRespCode(), errorCode.getStatus(), message);
    }


    public RemoteCallException(ErrorInfo error, int httpStatus) {
        this(error.getCode(), httpStatus, "调用远程服务异常, cause: " + error.getMessage());
    }

    public RemoteCallException(ErrorCode errorCode) {
        this(errorCode, errorCode.getRespMsg());
    }

    public String getCode() {
        return code;
    }

    public int getHttpStatus() {
        return httpStatus;
    }


}
