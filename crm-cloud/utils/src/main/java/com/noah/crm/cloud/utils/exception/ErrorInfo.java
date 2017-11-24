package com.noah.crm.cloud.utils.exception;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author xdw9486
 * for GlobException
 */
public class ErrorInfo<T> {
    private String code;
    private String message;
    private String url;
    private T data;

    @JsonCreator
    public ErrorInfo(@JsonProperty("code") String code,
                     @JsonProperty("url") String url,
                     @JsonProperty(value = "message", defaultValue = "") String message) {
        this.code = code;
        this.url = url;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("code", code)
                .append("message", message)
                .append("url", url)
                .append("data", data)
                .toString();
    }
}
