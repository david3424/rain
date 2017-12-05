package com.noah.crm.cloud.common.event.scheduler;

import com.noah.crm.cloud.common.event.service.EventBus;
import org.springframework.scheduling.annotation.Scheduled;

/**
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

    @Scheduled(cron = "0 0/20 * * * *")
    public void handleUnprocessedEventWatchProcess() {
        eventBus.handleUnprocessedEventWatchProcess();
    }

    @Scheduled(cron = "0 0/40 * * * *")
    public void handleTimeoutEventWatch() {
        eventBus.handleTimeoutEventWatch();
    }


}
