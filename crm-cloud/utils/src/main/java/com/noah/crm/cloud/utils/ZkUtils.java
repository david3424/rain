package com.noah.crm.cloud.utils;

/**
 * @author xdw9486
 */
public class ZkUtils {

    public static final String ZK_ROOT = "/mysteam";

    public static String createZkSchedulerLeaderPath(String applicationName) {
        return String.format("%s/%s/schedulers", ZK_ROOT, applicationName);
    }

}
