package com.noah.crm.cloud.common.test.callbacks;

import com.google.common.collect.Lists;
import com.noah.crm.cloud.apis.event.constants.FailureInfo;
import com.noah.crm.cloud.common.test.domain.AskTestEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 */
public class AskTestEventSecondCallback {

    public static final List<CallbackParam> successParams = new CopyOnWriteArrayList<>();
    public static final List<CallbackParam> failureParams = new CopyOnWriteArrayList<>();


    public void onSuccess(AskTestEvent event, String param1, String param2) {
        Map<String, String> params = new HashMap<>();
        params.put("param1", param1);
        params.put("param2", param2);
        CallbackParam callbackParam = new CallbackParam(Lists.newArrayList(event), null, params);
        successParams.add(callbackParam);
    }

    public void onFailure(AskTestEvent event, FailureInfo failureInfo, String param3, String param4) {
        Map<String, String> params = new HashMap<>();
        params.put("param3", param3);
        params.put("param4", param4);
        CallbackParam callbackParam = new CallbackParam(Lists.newArrayList(event), failureInfo, params);
        failureParams.add(callbackParam);
    }

}
