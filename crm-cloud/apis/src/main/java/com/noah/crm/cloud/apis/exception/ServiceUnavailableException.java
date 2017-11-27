package com.noah.crm.cloud.apis.exception;


import com.noah.crm.cloud.apis.api.ApisErrorCode;

/**
 * @author xdw9486
 */
public class ServiceUnavailableException extends ServiceException {

    private static final IErrorCode ERROR_CODE = ApisErrorCode.SERVICE_UNAVAILABLE_;
    private static final long serialVersionUID = 7823692017522203009L;

    public ServiceUnavailableException(String reason) {
        super(ERROR_CODE, reason);
    }

}
