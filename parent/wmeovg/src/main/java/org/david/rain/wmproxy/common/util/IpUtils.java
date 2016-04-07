package org.david.rain.wmproxy.common.util;

import javax.servlet.http.HttpServletRequest;
/**
 * 
 */
public class IpUtils {

    /**
     * 取用户的真实ip地址
     * 可以避免因为网关造成
     *
     * @param request
     * @return ip
     */
    public static String gerRealIp(HttpServletRequest request) {

        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip == null || ip.length() == 0)
            ip = "127.127.127.127";
        return ip;

    }
}
