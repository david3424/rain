package com.noah.crm.cloud.common.test.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.noah.crm.cloud.apis.event.constants.EventType;
import com.noah.crm.cloud.apis.event.domain.NotifyEvent;

import java.time.LocalDateTime;

/**
 * @author xdw9486
 */
public class NotifyFirstTestEvent extends NotifyEvent {

    public static final EventType EVENT_TYPE = EventType.NOTIFY_FIRST_TEST_EVENT;

    @Override
    public EventType getType() {
        return EVENT_TYPE;
    }

    private String name;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime registerTime;

    @JsonCreator
    public NotifyFirstTestEvent(
            @JsonProperty("name") String name,
            @JsonProperty("registerTime") LocalDateTime registerTime) {
        this.name = name;
        this.registerTime = registerTime;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getRegisterTime() {
        return registerTime;
    }

    @Override
    public String toString() {
        return "NotifyFirstTestEvent{" +
                "name='" + name + '\'' +
                ", registerTime=" + registerTime +
                "} " + super.toString();
    }
}