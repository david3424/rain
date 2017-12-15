package com.noah.crm.cloud.common.event.handler;


import com.noah.crm.cloud.apis.event.constants.FailureInfo;
import com.noah.crm.cloud.apis.event.domain.BaseEvent;

/**
 * @author xdw9486
 */
public interface RevokableAskEventHandler<E extends BaseEvent> extends AskEventHandler<E> {

    void processRevoke(E originEvent, FailureInfo failureInfo);

}
