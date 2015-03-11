package org.david.rain.pay.utils;

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
    public static String signature(Map<String, ?> params, String secret) {
        StringBuilder sbuf = new StringBuilder();
        if (null != params) {
            Map<String, Object> treeParam = new TreeMap<String, Object>();
            treeParam.putAll(params);
            int i = 0;
            for (Map.Entry<String, Object> e : treeParam.entrySet()) {
                if (e.getValue() != null && !e.getValue().equals("")) {
                   /* if (i++ != 0) {
                        sbuf.append("&");
                    }
                    sbuf.append(e.getKey());
                    sbuf.append("=");*/
                    sbuf.append(e.getValue().toString());
                }
            }
            //把私钥放在最后
            sbuf.append(secret);
            System.out.println(" subf md5str:" + sbuf.toString());
            return getMd5str(sbuf.toString());
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
}
