/*
 *  时间： 2008-7-30 17:17:26<br>
 *  北京完美时空网络技术有限公司<br>
 */
package org.david.rain.common.util.ip;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by IntelliJ IDEA.<br>
 * date: 2008-7-30<br>
 * time: 17:17:26<br>
 * vision: 1.0<br>
 * Ip操作相关 <br>
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
