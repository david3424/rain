package com.noah.crm.cloud.coupon.api.constants;

/**
 * @author xdw9486
 */
public enum CouponState {

    VALID("有效"),

    USED("已使用"),

    INVALID("已失效");

    public String desc;

    CouponState(String desc) {
        this.desc = desc;
    }



}
