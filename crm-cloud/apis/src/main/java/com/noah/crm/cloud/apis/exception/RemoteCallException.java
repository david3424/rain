package com.noah.crm.cloud.apis.exception;

import com.noah.crm.cloud.utils.exception.ServiceException;

/**
 * hystrix会忽略这个异常, 不会触发熔断
 *
 * @author xdw9486
 */
public class RemoteCallException extends ServiceException {

    private static final long serialVersionUID = -5646585116001992664L;
    private Error originError;

    public RemoteCallException(Error error, int httpStatus) {
        super("调用远程服务异常" + httpStatus + " cause:" + error.getMessage());
        this.originError = error;
    }

    public Error getOriginError() {
        return originError;
    }
}
