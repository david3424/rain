package org.david.rain.monitor.util.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.StreamUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * 应用场景：页面和后台通过json交互 目的：将对象转化成字符串 注意：controller需要与@ResponseBody配合使用
 * 
 * @author chenqi
 */

public class MappingGson2HttpMessageConverter extends AbstractHttpMessageConverter<Object> {

    /**
     * UTF-8
     */
    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
    /**
     * gson builder
     */
    private GsonBuilder gsonBuilder;

    /**
     * date pattern
     */
    private String datePattern = "yyyy-MM-dd HH:mm:ss";

    /**
     * MappingGson2HttpMessageConverter constructor
     */
    public MappingGson2HttpMessageConverter() {
        List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
        supportedMediaTypes.add(new MediaType("application", "json", DEFAULT_CHARSET));
        supportedMediaTypes.add(new MediaType("text", "html", DEFAULT_CHARSET));
        supportedMediaTypes.add(new MediaType("text", "json", DEFAULT_CHARSET));
        super.setSupportedMediaTypes(supportedMediaTypes);
        gsonBuilder = new GsonBuilder().setDateFormat(datePattern);
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    protected Object readInternal(Class<? extends Object> clazz, HttpInputMessage inputMessage) throws IOException {
        try {
            Gson gson = gsonBuilder.create();
            String s = StreamUtils.copyToString(inputMessage.getBody(), DEFAULT_CHARSET);
            return gson.fromJson(s, clazz);
        } catch (JsonParseException e) {
            throw new HttpMessageNotReadableException("Could not read JSON: " + e.getMessage(), e);
        }
    }

    @Override
    protected void writeInternal(Object t, HttpOutputMessage outputMessage) throws IOException {
        Type genericType = TypeToken.get(t.getClass()).getType();

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputMessage.getBody(), DEFAULT_CHARSET));
        try {
            Gson gson = gsonBuilder.create();
            writer.append(gson.toJson(t, genericType));
        } finally {
            writer.flush();
            writer.close();
        }

    }

}
