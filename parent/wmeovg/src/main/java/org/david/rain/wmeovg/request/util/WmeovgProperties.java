package org.david.rain.wmeovg.request.util;

import org.david.rain.wmeovg.request.service.PrizeServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @version 1.0
 * @ClassName PrizeUtil
 * @Description 发奖辅助类
 * @date 2010-8-11 下午01:25:46
 */
public class WmeovgProperties {

    protected static Logger log = LoggerFactory.getLogger(WmeovgProperties.class);

    private static String cid;
    private static String serverUrl;
    private static String keystorePath;
    private static String keystorePwd;
    private static String privateKey;

    static {
        String propertiesFileName = "/wmeovg.properties";
        InputStream is = PrizeServiceImpl.class.getResourceAsStream(propertiesFileName);
        Properties properties = new Properties();
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("虚拟物品兑换系统：加载属性文件失败：", e);
        }
        cid = properties.getProperty("client_id").trim();
        setServerUrl(properties.getProperty("server_url").trim());
        setKeystorePath(properties.getProperty("ssl_keystore_path").trim());
        setKeystorePwd(properties.getProperty("ssl_keystore_pwd").trim());
        setPrivateKey(properties.getProperty("private_key").trim());
    }

    public static String getCid() {
        return cid;
    }

    public static void setCid(String cid) {
        WmeovgProperties.cid = cid;
    }

    public static void setServerUrl(String serverUrl) {
        WmeovgProperties.serverUrl = serverUrl;
    }

    public static String getServerUrl() {
        return serverUrl;
    }

    public static void setKeystorePath(String keystorePath) {
        WmeovgProperties.keystorePath = keystorePath;
    }

    public static String getKeystorePath() {
        return keystorePath;
    }

    public static void setKeystorePwd(String keystorePwd) {
        WmeovgProperties.keystorePwd = keystorePwd;
    }

    public static String getKeystorePwd() {
        return keystorePwd;
    }

    public static void setPrivateKey(String privateKey) {
        WmeovgProperties.privateKey = privateKey;
    }

    public static String getPrivateKey() {
        return privateKey;
    }
}
