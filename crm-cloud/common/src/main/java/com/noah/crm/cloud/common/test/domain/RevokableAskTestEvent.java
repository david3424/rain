package com.noah.crm.cloud.common.test.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.noah.crm.cloud.apis.event.constants.EventType;
import com.noah.crm.cloud.apis.event.domain.AskEvent;
import com.noah.crm.cloud.apis.event.domain.Revokable;

public class RevokableAskTestEvent extends AskEvent implements Revokable {

    public static final EventType EVENT_TYPE = EventType.REVOKABLE_ASK_TEST_EVENT;

    @Override
    public EventType getType() {
        return EVENT_TYPE;
    }

    private String name;

    @JsonCreator
    public RevokableAskTestEvent(
            @JsonProperty("name") String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "RevokableAskTestEvent{" +
                "name='" + name + '\'' +
                "} " + super.toString();
    }
}