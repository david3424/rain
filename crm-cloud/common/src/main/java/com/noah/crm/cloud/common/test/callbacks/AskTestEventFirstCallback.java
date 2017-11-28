package com.noah.crm.cloud.common.test.callbacks;

import com.google.common.collect.Lists;
import com.noah.crm.cloud.common.test.domain.AskTestEvent;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 */
public class AskTestEventFirstCallback {

    public static final List<CallbackParam> successParams = new CopyOnWriteArrayList<>();

    public void onSuccess(AskTestEvent event) {
        CallbackParam callbackParam = new CallbackParam(Lists.newArrayList(event), null, null);
        successParams.add(callbackParam);
    }

}
