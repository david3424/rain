/*
 *  时间： 2008-7-30 17:17:26<br>
 */
package org.david.rain.common.util.ip;


import org.david.rain.common.util.DateUtils;
import org.david.rain.common.util.memcached.MemcachedManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 对原来的缓存，使用memcached重写，接口与原来相同
 * <p/>
 * date: 2011-10-11
 *
 * @version v1.0
 */

@Component
public class MemcachedIpUtils {


    @Autowired
    @Qualifier("memcachedManager")
    private MemcachedManager memManager;

    /**
     * 判断缓存里是否存在，如果不存在 或 已经过期，则更新缓存并返回false
     * 如果存在且没有过期则直接返回true
     */
    public boolean ipExist(String id, String ip, int m) {
        Map<String, Long> ipmap = (HashMap<String, Long>) memManager.get(id);
        long expiry = 1000 * 60 * m;


        if (ipmap == null) {
            ipmap = new HashMap<>();
            ipmap.put(ip, System.currentTimeMillis());
            memManager.set(id, ipmap);
            return false;
        }
        Long setTime = ipmap.get(ip);
        //不存在或已过时
        if (setTime == null || (setTime + expiry) < System.currentTimeMillis()) {
            ipmap.put(ip, System.currentTimeMillis());
            memManager.set(id, ipmap);
            return false;
        } else {
            return true;
        }

    }


    /**
     * 只用于判断是否过期
     */
    public boolean isIpExisted(String id, String ip, int m) {
        Map<String, Long> ipmap = (HashMap<String, Long>) memManager.get(id);
        if (ipmap == null) {
            ipmap = new HashMap<>();
        }
        long expiry = 1000 * 60 * m;
        Long setTime = ipmap.get(ip);

        if (ipmap == null || setTime == null || (setTime + expiry) < System.currentTimeMillis()) {
            return false;
        }
        return true;
    }


    /**
     * 只用于将限制放入缓存
     */
    public boolean saveIp(String id, String ip) {
        Map<String, Long> ipmap = (HashMap<String, Long>) memManager.get(id);
        if (ipmap == null) ipmap = new HashMap<>();
        ipmap.put(ip, DateUtils.getCurrentTimeMillis());
        return memManager.set(id, ipmap);
    }
}
