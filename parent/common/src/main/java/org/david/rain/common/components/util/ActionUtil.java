package org.david.rain.common.components.util;

import org.david.rain.common.util.ip.IpUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Date: 12-7-30
 * Time: 下午5:29
 */
public class ActionUtil {
    public static final String IE_SESSION_NAME = "USER";

    /**
     * 获得请求ip
     *
     * @return String ip地址
     */
    public static String getRealIp() {
      /*  HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return IpUtils.gerRealIp(request);*/
        return "127.0.0.1" ;
    }

    public static String getUserName() {
        return "david3424";
    }
}
