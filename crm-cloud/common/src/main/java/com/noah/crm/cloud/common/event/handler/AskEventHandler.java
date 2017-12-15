package com.noah.crm.cloud.common.event.handler;


import com.noah.crm.cloud.apis.api.BooleanWrapper;
import com.noah.crm.cloud.apis.event.domain.BaseEvent;

/**
 */
public interface AskEventHandler<E extends BaseEvent> {

    BooleanWrapper processRequest(E event);

}
