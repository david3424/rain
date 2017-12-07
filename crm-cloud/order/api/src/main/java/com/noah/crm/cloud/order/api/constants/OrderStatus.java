package com.noah.crm.cloud.order.api.constants;

/**
 * @author xdw9486
 */
public enum OrderStatus {

    CREATE_PENDING("正在下单"),

    CREATED("已下单"),

    CREATE_FAILED("下单失败");

    public String desc;

    OrderStatus(String desc) {
        this.desc = desc;
    }



}
