package com.noah.crm.cloud.common.test.handlers;


import com.noah.crm.cloud.common.event.handler.NotifyEventHandler;
import com.noah.crm.cloud.common.test.domain.NotifyFirstTestEvent;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 */
public class NotifyFirstTestEventFirstHandler implements NotifyEventHandler<NotifyFirstTestEvent> {

    public static final List<NotifyFirstTestEvent> events = new CopyOnWriteArrayList<>();

    @Override
    public void notify(NotifyFirstTestEvent event) {
        events.add(event);
    }

}
