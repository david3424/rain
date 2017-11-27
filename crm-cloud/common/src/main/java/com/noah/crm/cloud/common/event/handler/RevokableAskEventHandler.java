package com.noah.crm.cloud.common.event.handler;


import com.noah.crm.cloud.apis.event.constants.FailureInfo;
import com.noah.crm.cloud.apis.event.domain.AskEvent;
import com.noah.crm.cloud.apis.event.domain.Revokable;

/**
 * @author xdw9486
 */
public interface RevokableAskEventHandler<E extends AskEvent & Revokable> extends AskEventHandler<E> {

    void processRevoke(E originEvent, FailureInfo failureInfo);

}
