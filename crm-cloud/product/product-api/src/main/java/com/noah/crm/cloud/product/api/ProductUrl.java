package com.noah.crm.cloud.product.api;

/**
 */
public interface ProductUrl {

    String SERVICE_NAME = "PRODUCT";

    String SERVICE_HOSTNAME = "http://PRODUCT";

    String ALL_PRODUCT_LIST_URL = "/products/all";

    String PRODUCT_LIST_URL = "/products";

    static String buildUrl(String url) {
        return SERVICE_HOSTNAME + url;
    }

}
