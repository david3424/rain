package com.noah.crm.cloud.common.event.scheduler;

import com.noah.crm.cloud.common.event.service.EventBus;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author xdw9486
 */
public class EventScheduler {

    EventBus eventBus;

    public EventScheduler(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Scheduled(fixedDelay = 1000 * 10L)
    public void sendUnpublishedEvent() {
        eventBus.sendUnpublishedEvent();
    }

    @Scheduled(fixedRate = 1000 * 30)
    public void searchAndHandleUnprocessedEvent() {
        eventBus.searchAndHandleUnprocessedEvent();
    }




}
