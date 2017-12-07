package com.noah.crm.cloud.order.api;

/**
 */
public interface OrderUrl {

    String SERVICE_NAME = "ORDER";

    String SERVICE_HOSTNAME = "http://ORDER";

    String PLACE_ORDER = "/orders/place";

    String ORDER_INFO = "/orders/{orderId}";

    static String buildUrl(String url) {
        return SERVICE_HOSTNAME + url;
    }

}
