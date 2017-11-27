package com.noah.crm.cloud.common.test.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.noah.crm.cloud.apis.event.constants.EventType;
import com.noah.crm.cloud.apis.event.domain.NotifyEvent;

public class NotifySecondTestEvent extends NotifyEvent {

    public static final EventType EVENT_TYPE = EventType.NOTIFY_SECOND_TEST_EVENT;

    @Override
    public EventType getType() {
        return EVENT_TYPE;
    }

    private String name;

    @JsonCreator
    public NotifySecondTestEvent(
            @JsonProperty("name") String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "NotifySecondTestEvent{" +
                "name='" + name + '\'' +
                "} " + super.toString();
    }
}