package org.david.rain.web.init.servlet.filter;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * <code>{@link CrossScriptingRequestWrapper}</code>
 *
 * @author wqf
 */
public class CrossScriptingRequestWrapper extends HttpServletRequestWrapper {
    HttpServletRequest orgRequest = null;

    public CrossScriptingRequestWrapper(HttpServletRequest request) {
        super(request);
        orgRequest = request;
    }

    /**
     * 覆盖getParameter方法，将参数名和参数值都做xss过滤。<br/>
     * 如果需要获得原始的值，则通过super.getParameterValues(name)来获取<br/>
     * getParameterNames,getParameterValues和getParameterMap也可能需要覆盖
     */
    @Override
    public String getParameter(String name) {
        String value = super.getParameter(htmlEncode(name));
        if (value != null) {
            value = htmlEncode(value);
        }
        return value;
    }

    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);
        if (values == null) {
            return null;
        }
        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            encodedValues[i] = htmlEncode(values[i]);
        }
        return encodedValues;
    }

    /**
     * 覆盖getHeader方法，将参数名和参数值都做xss过滤。<br/>
     * 如果需要获得原始的值，则通过super.getHeaders(name)来获取<br/>
     * getHeaderNames 也可能需要覆盖
     */
    @Override
    public String getHeader(String name) {
//        String value = super.getHeader(xssEncode(name));
        String value = super.getHeader(htmlEncode(name));
        if (value != null) {
            value = htmlEncode(value);
        }
        return value;
    }

    /**
     * 将容易引起xss漏洞的半角字符直接替换成全角字符
     */
    public static String xssEncode(String value) {
        if (StringUtils.isBlank(value)) return value;
        value = StringEscapeUtils.escapeSql(value);
        value = StringEscapeUtils.escapeJavaScript(value);
        return StringEscapeUtils.escapeHtml(value);
//或者下面的方法直接过滤特殊字符，但有时候不能这么做。
//		value = value.replace('<',' ');
//		value = value.replace('>',' ');
//		value = value.replace('"',' ');
//		value = value.replace('\'',' ');
//		value = value.replace('/',' ');
//		value = value.replace('%',' ');
//		value = value.replace(';',' ');
//		value = value.replace('(',' ');
//		value = value.replace(')',' ');
//		value = value.replace('&',' ');
//		value = value.replace('+','_');

//或者直接转换成全角字符，有时候也不适用。
//		StringBuilder sb = new StringBuilder(s.length() + 16);
//		for (int i = 0; i < s.length(); i++) {
//			char c = s.charAt(i);
//			switch (c) {
//			case '>':
//				sb.append('＞');//全角大于号
//				break;
//			case '<':
//				sb.append('＜');//全角小于号
//				break;
//			case '\'':
//				sb.append('‘');//全角单引号
//				break;
//			case '\"':
//				sb.append('“');//全角双引号
//				break;
//			case '&':
//				sb.append('＆');//全角
//				break;
//			case '\\':
//				sb.append('＼');//全角斜线
//				break;
//			case '#':
//				sb.append('＃');//全角井号
//				break;
//			default:
//				sb.append(c);
//				break;
//			}
//		}
//		return sb.toString();
    }

    /**
     * 获取最原始的request
     *
     * @return
     */
    public HttpServletRequest getOrgRequest() {
        return orgRequest;
    }

    /**
     * 获取最原始的request的静态方法
     *
     * @return
     */
    public static HttpServletRequest getOrgRequest(HttpServletRequest req) {
        if (req instanceof CrossScriptingRequestWrapper) {
            return ((CrossScriptingRequestWrapper) req).getOrgRequest();
        }
        return req;
    }

    /**
     * self definded
     * @param value
     * @return
     */
    public static String htmlEncode(String value) {
        if (StringUtils.isBlank(value)) return value;
//        value = StringEscapeUtils.escapeSql(value);
        String html;
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            switch (c) {
                case '<':
                    buffer.append("&lt;");
                    break;
                case '>':
                    buffer.append("&gt;");
                    break;
                case '&':
                    buffer.append("&amp;");
                    break;
                case '"':
                    buffer.append("&quot;");
                    break;
                case '\'':
                    buffer.append("&apos;");
                    break;
                case 10:
                case 13:
                    break;
                default:
                    buffer.append(c);
            }
        }
        html = buffer.toString();
        return html;
    }

}
