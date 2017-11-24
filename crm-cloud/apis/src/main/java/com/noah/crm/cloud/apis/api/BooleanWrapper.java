package com.noah.crm.cloud.apis.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author xdw9486
 */
public class BooleanWrapper {

    private boolean success;

    private String message;

    public BooleanWrapper(boolean success) {
        this(success, null);
    }

    @JsonCreator
    public BooleanWrapper(
            @JsonProperty("success") boolean success,
            @JsonProperty("message") String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
