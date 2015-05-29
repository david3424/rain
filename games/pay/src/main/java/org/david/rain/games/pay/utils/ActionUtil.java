package org.david.rain.games.pay.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by david on 2015/4/30.
 * 获取request相关信息
 */
public class ActionUtil {


    /**
     * 获得请求ip
     *
     * @return String ip地址
     */
    public static String getRealIp() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return IpUtils.getRealIp(request);
    }

}
