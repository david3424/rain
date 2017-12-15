package com.noah.crm.cloud.apis.event.constants;

/**
 * 队列用
 *
 * @author xdw9486
 */
public enum EventType {

    //user service
    USER_CREATED;


    public static EventType valueOfIgnoreCase(String name) {
        if (name == null) {
            return null;
        }
        return valueOf(name.toUpperCase());
    }

}
