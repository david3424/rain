package org.david.rain.cloud.start.web.exception;

import org.david.rain.common.exception.ErrorInfo;
import org.david.rain.common.exception.IErrorCode;
import org.david.rain.common.exception.ServiceException;
import org.david.rain.common.logback.LoggerUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
class GlobalExceptionHandler {
    private static final String DEFAULT_ERROR_VIEW = "exception";

    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        LoggerUtil.error("异常统一处理：", e);
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName(DEFAULT_ERROR_VIEW);
        return mav;
    }


    @ExceptionHandler(value = ServiceException.class)
    @ResponseBody
    public ErrorInfo jsonErrorHandler(HttpServletRequest req, ServiceException e) throws Exception {

        ErrorInfo<String> r = new ErrorInfo<>();
        r.setMessage(e.getMessage());
        r.setCode(e.getErrorCode().getRespCode());
        r.setData("Some Data");
        r.setUrl(req.getRequestURL().toString());
        return r;
    }
}
