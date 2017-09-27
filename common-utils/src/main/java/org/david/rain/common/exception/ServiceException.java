package org.david.rain.common.exception;

import org.apache.commons.lang3.StringUtils;

/**
 * 公共异常类
 * 只需要实现ErrorCode就能轻松传递unchecked异常
 */

public class ServiceException extends RuntimeException {


    private IErrorCode errorCode;
    private String userDefinedReason;

    public ServiceException(IErrorCode errorCode) {
        this(errorCode, null, null);
    }

    public ServiceException(IErrorCode errorCode, String userDefinedReason) {

        this(errorCode, userDefinedReason, null);
    }

    public ServiceException(IErrorCode errorCode, Throwable cause) {
        this(errorCode, null, cause);
    }

    public ServiceException(IErrorCode errorCode, String userDefinedReason, Throwable cause) {
        super(cause);
        if (errorCode == null) {
            throw new IllegalArgumentException("error code could not null.");
        }
        this.errorCode = errorCode;
        this.userDefinedReason = userDefinedReason;
    }

    /**
     * todo 没有errorCode貌似没意义
     *
     * @param code
     * @param message
     */
    public ServiceException(int code, String message) {
        // TODO Auto-generated constructor stub
    }

    @Override
    public String getMessage() {
        if (this.errorCode == null) {
            return super.getMessage();
        } else if (StringUtils.isNotEmpty(userDefinedReason)) {
            return String.format(errorCode.getRespMsg(), userDefinedReason);
        } else {
            return this.errorCode.getRespMsg();
        }
    }

    // 这个是导致异常性能不佳的地方，所以自定义异常要覆写这个方法<br/>

    /**
     * 除非throw该异常时，把异常堆栈给传递出来了，
     * 否则外面catch，填充堆栈信息就到此为止（一般是业务约束校验失败场景用到，比如对方没异常，但是返回的code不对，我们业务要block的）
     */
/*    @Override
    public Throwable fillInStackTrace() {
        return this;
    }*/
}
