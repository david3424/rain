package com.noah.crm.cloud.common.exception;

import com.netflix.hystrix.exception.HystrixBadRequestException;
import com.noah.crm.cloud.apis.exception.ErrorInfo;
import com.noah.crm.cloud.apis.exception.RemoteCallException;
import com.noah.crm.cloud.utils.mapper.JsonMapper;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static java.lang.String.format;

/**
 * @author xdw9486
 */
public class UserErrorDecoder implements ErrorDecoder {

    private Logger logger = LoggerFactory.getLogger(getClass());
    public static final int STATUS_UP = 500;
    public static final int STATUS_DOWN = 400;

    @Override
    public Exception decode(String methodKey, Response response) {
        String body = null;
        try {
            body = Util.toString(response.body().asReader());
        } catch (IOException ignored) { // NOPMD
            logger.error("response analysis error .", ignored);
        }
        logger.debug("response exception info  is{},code:{} ", body, response.status());
        if (body == null || body.length() == 0 || response.status() > STATUS_UP || response.status() < STATUS_DOWN) {
            String message = format("status %s reading %s", response.status(), methodKey);
            return new Exception(message);
        }
        ErrorInfo error = JsonMapper.defaultMapper().fromJson(body, ErrorInfo.class);
        return new HystrixBadRequestException("request exception wrapper", new RemoteCallException(error, response.status()));

    }
}
