package com.noah.crm.cloud.account.api.constants;

/**
 * @author xdw9486
 */
public enum AccountUrl {

    SERVICE_NAME("ACCOUNT"),
    SERVICE_HOSTNAME("http://ACCOUNT"),
    CHECK_ENOUGH_BALANCE("/accounts/{userId}/enough"),
    ACCOUNT_BALANCE("/accounts/{userId}/balance"),
    ACCOUNT_TRANSACTIONS("/accounts/{userId}/transactions");

    public String url;

    AccountUrl(String url) {
        this.url = url;
    }

    static String buildUrl(String url) {
        return SERVICE_HOSTNAME + url;
    }


}
