package com.noah.crm.cloud.user.api.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.noah.crm.cloud.apis.event.constants.EventType;
import com.noah.crm.cloud.apis.event.domain.NotifyEvent;

import java.time.LocalDateTime;

/**
 */
public class UserCreated extends NotifyEvent {

    public static final EventType EVENT_TYPE = EventType.USER_CREATED;

    @Override
    public EventType getType() {
        return EVENT_TYPE;
    }

    private Long userId;

    private String username;

    private LocalDateTime registerTime;

    @JsonCreator
    public UserCreated(
            @JsonProperty("userId") Long userId,
            @JsonProperty("username") String username,
            @JsonProperty("registerTime") LocalDateTime registerTime) {
        this.userId = userId;
        this.username = username;
        this.registerTime = registerTime;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public LocalDateTime getRegisterTime() {
        return registerTime;
    }

    @Override
    public String toString() {
        return "UserCreated{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", registerTime=" + registerTime +
                "} " + super.toString();
    }
}
