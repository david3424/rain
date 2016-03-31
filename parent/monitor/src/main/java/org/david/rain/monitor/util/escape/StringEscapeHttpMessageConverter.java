package org.david.rain.monitor.util.escape;

import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.StreamUtils;
import org.springframework.web.util.HtmlUtils;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * 应用场景：页面与后台通过json串进行交互时 目的：对字符串进行转义防止xss攻击
 * 
 * @author chenqi
 */
public class StringEscapeHttpMessageConverter extends StringHttpMessageConverter {
    /**
     * UTF-8
     */
    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    @Override
    protected void writeInternal(String s, HttpOutputMessage outputMessage) throws IOException,
            HttpMessageNotWritableException {
        s = HtmlUtils.htmlEscape(s);
        StreamUtils.copy(s, DEFAULT_CHARSET, outputMessage.getBody());
    }
}
