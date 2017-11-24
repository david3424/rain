package com.noah.crm.cloud.apis.event.constants;

/**
 * @author xdw9486
 */
public enum EventType {

    ASK_RESPONSE,

    REVOKE_ASK,

    //account service
    ASK_REDUCE_BALANCE,

    //user service
    USER_CREATED,

    //order service
    ORDER_CREATE_PENDING,

    //coupon service
    ASK_USE_COUPON,


    NOTIFY_FIRST_TEST_EVENT,

    NOTIFY_SECOND_TEST_EVENT,

    ASK_TEST_EVENT,

    REVOKABLE_ASK_TEST_EVENT;


    public static EventType valueOfIgnoreCase(String name) {
        if (name == null) {
            return null;
        }
        return valueOf(name.toUpperCase());
    }

}
