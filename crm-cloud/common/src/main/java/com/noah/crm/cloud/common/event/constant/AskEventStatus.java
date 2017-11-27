package com.noah.crm.cloud.common.event.constant;

/**
 */
public enum AskEventStatus {

    PENDING("请求中"),

    TIMEOUT("已超时"),

    FAILED("已失败"),

    SUCCESS("完成"),

    CANCELLED("已取消");

    public String desc;

    AskEventStatus(String desc) {
        this.desc = desc;
    }

}
