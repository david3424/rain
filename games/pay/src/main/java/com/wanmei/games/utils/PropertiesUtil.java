package com.wanmei.games.utils;


import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * User: joeytang
 * Date: 2012-06-05 02:11
 * 属性文件工具类
 */
public class PropertiesUtil {

    //文件名key value是文件里包含的所有属性
    private static Map<String, Properties> propertiesMap = new ConcurrentHashMap<String, Properties>();

    public static Properties getProperties(String filename) {
        Properties p = new Properties();
        InputStream in = null;
        try {
            in = PropertiesUtil.class.getResource(filename).openStream();
            p.load(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return p;
    }

    public static String getProperty(String filename, String key) {
        if (StringUtils.isBlank(filename) || StringUtils.isBlank(key)) {
            return null;
        }
        Properties p = propertiesMap.get(filename);
        if (null == p) {
            p = getProperties(filename);
            if (null != p) {
                propertiesMap.put(filename, p);
                return p.getProperty(key);
            } else {
                return null;
            }
        }
        return p.getProperty(key);
    }
}
