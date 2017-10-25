package org.david.rain.cloud.start.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.david.rain.common.logback.LoggerUtil;
import org.springframework.web.util.HtmlUtils;

/**
 * 描述 : 跨站请求防范
 *
 * @author xdw9486
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    /**
     * 描述 : 构造函数
     *
     * @param request 请求对象
     */
    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        return HtmlUtils.htmlEscape(value);
    }

    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        LoggerUtil.debug("getParameter:{},{}",name,value);
        return HtmlUtils.htmlEscape(value);
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (values != null) {
            int length = values.length;
            String[] escapeValues = new String[length];
            for (int i = 0; i < length; i++) {
                escapeValues[i] = HtmlUtils.htmlEscape(values[i]);
                LoggerUtil.debug("getParameterValues:{},{}",name,escapeValues[i] );
            }
            return escapeValues;
        }
        return super.getParameterValues(name);
    }

}