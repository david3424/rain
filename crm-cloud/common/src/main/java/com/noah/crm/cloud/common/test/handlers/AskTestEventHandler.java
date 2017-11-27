package com.noah.crm.cloud.common.test.handlers;


import com.noah.crm.cloud.apis.api.BooleanWrapper;
import com.noah.crm.cloud.common.event.handler.AskEventHandler;
import com.noah.crm.cloud.common.test.domain.AskTestEvent;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author xdw9486
 */
public class AskTestEventHandler implements AskEventHandler<AskTestEvent> {

    public static final String SUCCESS_EVENT_NAME = "克尔苏加德";

    public static final List<AskTestEvent> events = new CopyOnWriteArrayList<>();

    @Override
    public BooleanWrapper processRequest(AskTestEvent event) {
        events.add(event);
        return new BooleanWrapper(event.getName().equals(SUCCESS_EVENT_NAME));
    }

}
