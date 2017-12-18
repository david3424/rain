package com.noah.crm.cloud.account.api.constants;

/**
 * @author xdw9486
 */
public class AccountUrl {

    public final static String SERVICE_NAME = "ACCOUNT";
    public final static String SERVICE_HOSTNAME = "http://ACCOUNT";
    public final static String CHECK_ENOUGH_BALANCE = "/accounts/{userId}/enough";
    public final static String ACCOUNT_BALANCE = "/accounts/{userId}/balance";
    public final static String ACCOUNT_TRANSACTIONS = "/accounts/{userId}/transactions";
    public final static String ACCOUNT_CREATE = "/accounts/create";

    public static String buildUrl(String url) {
        return SERVICE_HOSTNAME + url;
    }


}
