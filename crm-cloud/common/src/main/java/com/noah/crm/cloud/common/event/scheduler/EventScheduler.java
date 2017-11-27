package com.noah.crm.cloud.common.event.scheduler;

import com.noah.crm.cloud.common.event.service.EventBus;
import org.springframework.scheduling.annotation.Scheduled;

/**
 */
public class EventScheduler{

    EventBus eventBus;

    public EventScheduler(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Scheduled(fixedRate = 500L)
    public void sendUnpublishedEvent() {
        eventBus.sendUnpublishedEvent();
    }

    @Scheduled(fixedRate = 500L)
    public void searchAndHandleUnprocessedEvent() {
        eventBus.searchAndHandleUnprocessedEvent();
    }

    @Scheduled(fixedRate = 500L)
    public void handleUnprocessedEventWatchProcess() {
        eventBus.handleUnprocessedEventWatchProcess();
    }

    @Scheduled(fixedRate = 1000L)
    public void handleTimeoutEventWatch() {
        eventBus.handleTimeoutEventWatch();
    }



}
