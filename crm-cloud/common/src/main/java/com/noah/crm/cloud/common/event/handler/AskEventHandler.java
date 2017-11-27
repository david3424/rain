package com.noah.crm.cloud.common.event.handler;


import com.noah.crm.cloud.apis.api.BooleanWrapper;
import com.noah.crm.cloud.apis.event.domain.AskEvent;

/**
 */
public interface AskEventHandler<E extends AskEvent> {

    BooleanWrapper processRequest(E event);

}
