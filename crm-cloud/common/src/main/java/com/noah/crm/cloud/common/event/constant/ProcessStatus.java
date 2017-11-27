package com.noah.crm.cloud.common.event.constant;

/**
 */
public enum ProcessStatus {

    NEW("未处理"),

    PROCESSED("已处理"),

    IGNORE("忽略");

    public String desc;

    ProcessStatus(String desc) {
        this.desc = desc;
    }

}
