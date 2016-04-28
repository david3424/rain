package org.david.rain.games.pay.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.TreeMap;

public class SignatureUtil {
    /**
     * sig param
     *
     * @param params
     * @param secret
     * @return
     */
    private final static Logger logger = LoggerFactory.getLogger(SignatureUtil.class);

    /**
     * 加密非空参数拼接结果*
     *
     * @param params
     * @param secret
     * @param type
     * @return
     */
    public static String signature(Map<String, ?> params, String secret, String type) {
        StringBuilder sbuf = new StringBuilder();
        if (null != params) {
            Map<String, Object> treeParam = new TreeMap<String, Object>();
            treeParam.putAll(params);
            int i = 0;
            for (Map.Entry<String, Object> e : treeParam.entrySet()) {
                if (e.getValue() != null && !e.getValue().equals("")) {
                    sbuf.append(e.getValue().toString());
                }
            }
            //把私钥放在最后
            sbuf.append(secret);
            logger.info(" singed md5str:{}", sbuf.toString());
            try {
                return StringUtils.equals("md5", type) ? getMd5str(sbuf.toString()) : getSHAStr(sbuf.toString());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                logger.error("sha error:{}", e.getMessage());
                return "";
            }
        }
        return "";
    }

    /**
     * md5 to hex in lowwer case
     *
     * @param b
     * @return
     */
    private static String getMd5str(byte[] b) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return bytetoHexStringLowwer(md.digest(b));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getMd5str(String str) {
        try {
            return getMd5str(str.getBytes("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * byte array to hex lowwer string
     *
     * @param data
     * @return
     */
    private static String bytetoHexStringLowwer(byte[] data) {
        char hexdigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        return bytetoHexString(data, hexdigits);
    }

    private static String bytetoHexString(byte[] data, char[] hexdigits) {
        char[] tem = new char[data.length * 2];
        for (int i = 0; i < data.length; i++) {
            byte b = data[i];
            tem[i * 2] = hexdigits[b >>> 4 & 0x0f];
            tem[i * 2 + 1] = hexdigits[b & 0x0f];
        }
        return new String(tem);
    }

    public static String getSHAStr(String data) throws NoSuchAlgorithmException {
        return getSHA(data.getBytes());
    }

    /**
     * SHA加密
     *
     * @param data
     * @return
     * @throws java.security.NoSuchAlgorithmException
     * @throws Exception
     */
    public static String getSHA(byte[] data) throws NoSuchAlgorithmException {

        MessageDigest sha = MessageDigest.getInstance("SHA1");
        sha.update(data);
        return toHex(sha.digest());
    }


    /**
     * sha1 摘要转16进制
     *
     * @param digest
     * @return
     */
    private static String toHex(byte[] digest) {
        StringBuilder sb = new StringBuilder();
        String out;
        for (byte aDigest : digest) {
//          out = Integer.toHexString(0xFF & digest[i] + 0xABCDEF); //加任意 salt
            out = Integer.toHexString(0xFF & aDigest);//原始方法
            if (out.length() == 1) {
                sb.append("0");//如果为1位 前面补个0
            }
            sb.append(out);
        }
        return sb.toString();
    }
}
