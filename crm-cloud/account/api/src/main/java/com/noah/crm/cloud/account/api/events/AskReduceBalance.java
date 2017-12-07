package com.noah.crm.cloud.account.api.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.noah.crm.cloud.apis.event.constants.EventType;
import com.noah.crm.cloud.apis.event.domain.AskEvent;
import com.noah.crm.cloud.apis.event.domain.Revokable;

/**
 * @author xdw9486
 */
public class AskReduceBalance extends AskEvent implements Revokable {

    public static final EventType EVENT_TYPE = EventType.ASK_REDUCE_BALANCE;

    @Override
    public EventType getType() {
        return EVENT_TYPE;
    }

    private Long userId;

    private Long balance;

    @JsonCreator
    public AskReduceBalance(
            @JsonProperty("userId") Long userId,
            @JsonProperty("balance") Long balance) {
        this.userId = userId;
        this.balance = balance;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return "AskReduceBalance{" +
                "userId=" + userId +
                ", balance=" + balance +
                "} " + super.toString();
    }
}
