package com.noah.crm.cloud.common.event.handler;


import com.noah.crm.cloud.apis.event.domain.NotifyEvent;

/**
 */
public interface NotifyEventHandler<E extends NotifyEvent> {

    void notify(E event);

}
