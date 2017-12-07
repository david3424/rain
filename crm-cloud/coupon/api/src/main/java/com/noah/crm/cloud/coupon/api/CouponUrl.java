package com.noah.crm.cloud.coupon.api;

/**
 * @author xdw9486
 */
public interface CouponUrl {

    String SERVICE_NAME = "COUPON";

    String SERVICE_HOSTNAME = "http://COUPON";

    String CHECK_VALID_URL = "/coupons/{couponId}/valid";

    String COUPON_LIST_URL = "/coupons";

    String USER_COUPON_LIST_URL = "/coupons/{userId}";

    static String buildUrl(String url) {
        return SERVICE_HOSTNAME + url;
    }

}
