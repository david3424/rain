package com.noah.crm.cloud.common.test.callbacks;


import com.noah.crm.cloud.apis.event.constants.FailureInfo;
import com.noah.crm.cloud.apis.event.domain.AskEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 */
public class CallbackParam {

    private List<AskEvent> askEvents;

    private FailureInfo failureInfo;

    private Map<String, String> params;

    public CallbackParam(List<AskEvent> askEvents, FailureInfo failureInfo, Map<String, String> params) {
        this.askEvents = askEvents;
        this.failureInfo = failureInfo;
        this.params = params == null ? new HashMap<>() : params;
    }


    public List<AskEvent> getAskEvents() {
        return askEvents;
    }

    public FailureInfo getFailureInfo() {
        return failureInfo;
    }

    public Map<String, String> getParams() {
        return params;
    }
}
