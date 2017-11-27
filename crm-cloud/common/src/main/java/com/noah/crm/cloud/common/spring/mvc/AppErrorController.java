package com.noah.crm.cloud.common.spring.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.noah.crm.cloud.apis.api.ApisErrorCode;
import com.noah.crm.cloud.apis.exception.ErrorInfo;
import com.noah.crm.cloud.utils.mapper.JsonMapper;
import org.springframework.boot.autoconfigure.web.AbstractErrorController;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 */
@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class AppErrorController extends AbstractErrorController {

    private final ErrorProperties errorProperties;

    public AppErrorController(ErrorAttributes errorAttributes, ErrorProperties errorProperties) {
        super(errorAttributes);
        Assert.notNull(errorProperties, "ErrorProperties must not be null");
        this.errorProperties = errorProperties;
    }

    @Override
    public String getErrorPath() {
        return this.errorProperties.getPath();
    }

    @RequestMapping(produces = "text/html")
    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {

        HttpStatus status = getStatus(request);
        ApisErrorCode errorCode = ApisErrorCode.fromHttpStatus(status.value());
        ErrorInfo error = new ErrorInfo(errorCode.getRespCode(), request.getRequestURI(), status.getReasonPhrase());
        ModelAndView mav = new ModelAndView();
        ObjectMapper om = JsonMapper.defaultMapper().getMapper();
        MappingJackson2JsonView view = new MappingJackson2JsonView(om);
        view.setAttributesMap(om.convertValue(error, Map.class));
        mav.setView(view);
        return mav;

    }


    @RequestMapping
    @ResponseBody
    public ResponseEntity<String> error(HttpServletRequest request) {
        HttpStatus status = getStatus(request);
        ApisErrorCode errorCode = ApisErrorCode.fromHttpStatus(status.value());
        ErrorInfo error = new ErrorInfo(errorCode.getRespCode(), request.getRequestURI(), status.getReasonPhrase());
        return new ResponseEntity<>(JsonMapper.defaultMapper().toJson(error), status);
    }

}

