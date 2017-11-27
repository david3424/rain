package com.noah.crm.cloud.apis.exception;

/**
 * hystrix会忽略这个异常, 不会触发熔断
 *
 * @author xdw9486
 */
public class RemoteCallException extends ServiceException {

    private static final long serialVersionUID = -5646585116001992664L;
    private ErrorInfo originError;

    public RemoteCallException(ErrorInfo error, int httpStatus) {
        super("调用远程服务异常" + httpStatus + " cause:" + error.getMessage());
        this.originError = error;
    }

    public ErrorInfo getOriginError() {
        return originError;
    }
}
