package com.noah.crm.cloud.user.api;

/**
 * @author xdw9486
 * 用户操作API
 */
public interface UserUrl {

    String SERVICE_NAME = "USER";

    String SERVICE_HOSTNAME = "http://USER";

    String USER_REGISTER_URL = "/users/register";

    String USER_REGISTER_TEST_URL = "/users/register_test";


    static String buildUrl(String url) {
        return SERVICE_HOSTNAME + url;
    }

}
