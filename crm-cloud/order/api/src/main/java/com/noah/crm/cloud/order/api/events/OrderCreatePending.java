package com.noah.crm.cloud.order.api.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.noah.crm.cloud.apis.event.constants.EventType;
import com.noah.crm.cloud.apis.event.domain.NotifyEvent;

/**
 * @author xdw9486
 */
public class OrderCreatePending extends NotifyEvent {

    public static final EventType EVENT_TYPE = EventType.ORDER_CREATE_PENDING;

    @Override
    public EventType getType() {
        return EVENT_TYPE;
    }

    private Long orderId;

    private Long orderNo;

    private Long totalAmount;

    private Long payAmount;

    private Long userId;

    @JsonCreator
    public OrderCreatePending(
            @JsonProperty("orderId") Long orderId,
            @JsonProperty("orderNo") Long orderNo,
            @JsonProperty("totalAmount") Long totalAmount,
            @JsonProperty("payAmount") Long payAmount,
            @JsonProperty("userId") Long userId) {
        this.orderId = orderId;
        this.orderNo = orderNo;
        this.totalAmount = totalAmount;
        this.payAmount = payAmount;
        this.userId = userId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getOrderNo() {
        return orderNo;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public Long getPayAmount() {
        return payAmount;
    }

    public Long getUserId() {
        return userId;
    }
}
