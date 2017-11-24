package com.noah.crm.cloud.apis.event.constants;

/**
 * @author xdw9486
 */
public enum FailureReason {

    TIMEOUT("事件超时"),

    FAILED("事件失败"),

    CANCELLED("事件取消");

    public String desc;

    FailureReason(String desc) {
        this.desc = desc;
    }

}
