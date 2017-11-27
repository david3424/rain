package com.noah.crm.cloud.common.spring.mvc;

import com.google.common.base.Joiner;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.netflix.hystrix.exception.HystrixTimeoutException;
import com.noah.crm.cloud.apis.api.ApisErrorCode;
import com.noah.crm.cloud.apis.exception.RemoteCallException;
import com.noah.crm.cloud.apis.exception.ServiceUnavailableException;
import com.noah.crm.cloud.apis.exception.ErrorInfo;
import com.noah.crm.cloud.apis.exception.IErrorCode;
import com.noah.crm.cloud.apis.exception.ServiceException;
import com.noah.crm.cloud.utils.mapper.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 统一异常处理
 * @author xdw9486
 */
@ControllerAdvice
public class AppExceptionHandlerController extends ResponseEntityExceptionHandler {

    protected Logger logger = LoggerFactory.getLogger(AppExceptionHandlerController.class);


    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers,
                                                         HttpStatus status, WebRequest request) {

        IErrorCode errorCode = ApisErrorCode.BAD_REQUEST;
        List<ObjectError> allErrors = ex.getAllErrors();
        String errorMessage = extractErrorMessageFromObjectErrors(allErrors, errorCode.getRespMsg());
        return createResponseEntity(errorCode, request.getDescription(false), errorMessage);

    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {

        IErrorCode errorCode = ApisErrorCode.BAD_REQUEST;
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        String errorMessage = extractErrorMessageFromObjectErrors(allErrors, errorCode.getRespMsg());
        return createResponseEntity(errorCode, request.getDescription(false), errorMessage);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body,
                                                             HttpHeaders headers, HttpStatus status,
                                                             WebRequest request) {

        logger.error("spring mvc 异常: " + ex.getMessage(), ex);
        IErrorCode errorCode = ApisErrorCode.fromHttpStatus(status.value());
        return createResponseEntity(errorCode, request.getDescription(false), errorCode.getRespMsg());
    }

    @ExceptionHandler(value = {ServiceUnavailableException.class, RemoteCallException.class})
    public ResponseEntity<Object> handleRemoteCallException(HttpServletRequest request, ServiceException e) {

        logger.error(e.getMessage(), e);
        return createResponseEntity(e.getErrorCode().getRespCode(), e.getErrorCode().getStatus(), request.getRequestURI(), e.getMessage());
    }

    @ExceptionHandler(value = HystrixTimeoutException.class)
    public ResponseEntity<Object> handleHystrixTimeoutException(HttpServletRequest request, HystrixTimeoutException e) {

        logger.error(e.getMessage(), e);
        IErrorCode errorCode = ApisErrorCode.GATEWAY_TIMEOUT;
        return createResponseEntity(errorCode, request.getRequestURI(), e.getMessage());
    }

    @ExceptionHandler(value = HystrixRuntimeException.class)
    public ResponseEntity<Object> handleHystrixRuntimeException(HttpServletRequest request, HystrixRuntimeException e) {

        logger.error("Hystrix Command 运行报错: " + e.getMessage(), e);
        IErrorCode errorCode = ApisErrorCode.INTERNAL_ERROR;
        return createResponseEntity(errorCode, request.getRequestURI(), e.getMessage());
    }


    @ExceptionHandler(value = ServiceException.class)
    public ResponseEntity<Object> handleAppBusinessException(HttpServletRequest request, ServiceException e) {

        //业务异常
        return createResponseEntity(e.getErrorCode().getRespCode(), e.getErrorCode().getStatus(), request.getRequestURI(), e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> handleException(HttpServletRequest request, Exception e) {

        logger.error("服务器发生错误: " + e.getMessage(), e);
        IErrorCode errorCode = ApisErrorCode.INTERNAL_ERROR;
        return createResponseEntity(errorCode, request.getRequestURI(), errorCode.getRespMsg());

    }


    private ResponseEntity<Object> createResponseEntity(IErrorCode errorCode, String requestUri, String message) {
        return createResponseEntity(errorCode.getRespCode(), errorCode.getStatus(), requestUri, message);
    }

    private ResponseEntity<Object> createResponseEntity(String code, int httpStatus, String requestUri, String message) {
        ErrorInfo error = new ErrorInfo(code, requestUri, message);
        String json = JsonMapper.defaultMapper().toJson(error);

        return ResponseEntity.status(HttpStatus.valueOf(httpStatus)).body(json);

    }


    private String extractErrorMessageFromObjectErrors(List<ObjectError> allErrors, String defaultMessage) {
        if (allErrors == null || allErrors.isEmpty()) {
            return defaultMessage;
        } else {
            List<String> errorMessages = allErrors.stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            return Joiner.on(",").skipNulls().join(errorMessages);
        }
    }
}