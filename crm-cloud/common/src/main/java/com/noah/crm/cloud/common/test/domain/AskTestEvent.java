package com.noah.crm.cloud.common.test.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.noah.crm.cloud.apis.event.constants.EventType;
import com.noah.crm.cloud.apis.event.domain.AskEvent;

public class AskTestEvent extends AskEvent {

    public static final EventType EVENT_TYPE = EventType.ASK_TEST_EVENT;

    @Override
    public EventType getType() {
        return EVENT_TYPE;
    }

    private String name;

    @JsonCreator
    public AskTestEvent(
            @JsonProperty("name") String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "AskTestEvent{" +
                "name='" + name + '\'' +
                "} " + super.toString();
    }
}