package com.noah.crm.cloud.utils;

import com.noah.crm.cloud.apis.exception.ErrorInfo;
import com.noah.crm.cloud.utils.mapper.JsonMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.function.Consumer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

/**
 * @author xdw9486
 */
public interface TestUtils {


    /**
     * 当使用RestTemplate调用测试接口时, 可能会抛出HttpClientErrorException或HttpClientErrorException
     * 这个方法运行runnable, 将捕获的异常交给consumer执行.
     * 如果runnable没有抛出指定异常, 则抛出AssertionError
     *
     * @param runnable
     * @param consumer
     */
    static void assertServerError(Runnable runnable, Consumer<ErrorInfo> consumer) throws AssertionError {

        Exception unexpected = null;

        try {
            runnable.run();
        } catch (HttpServerErrorException | HttpClientErrorException e) {
            String json = e.getResponseBodyAsString();
            assertThat(json, notNullValue());
            ErrorInfo error = JsonMapper.defaultMapper().fromJson(json, ErrorInfo.class);
            consumer.accept(error);
            return;
        } catch (Exception e) {
            unexpected = e;
        }
        if (unexpected == null) {
            throw new AssertionError("HttpServerErrorException or HttpClientErrorException was expected here, " +
                    "but nothing happened");
        } else {
            throw new AssertionError("HttpServerErrorException or HttpClientErrorException was expected here, " +
                    "but exception was " + unexpected.toString());
        }

    }

    static HttpEntity<String> createJsonEntity(Object object) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(JsonMapper.defaultMapper().toJson(object), requestHeaders);
    }

}
