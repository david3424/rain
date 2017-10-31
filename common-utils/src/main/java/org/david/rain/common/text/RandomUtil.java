package org.david.rain.common.text;

import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * Time: 12:11:54
 * 随机标识类.
 *
 * @author xdw9486
 */
public class RandomUtil {

    /**
     * 获取随机字符串
     *
     * @param length
     * @return
     */
    public static String getRandomString(int length) {
        String bufferStr = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ=+\\*&@()-";
        StringBuilder sb = new StringBuilder();
        Random r = new Random();
        int range = bufferStr.length();
        for (int i = 0; i < length; i++) {
            sb.append(bufferStr.charAt(r.nextInt(range)));
        }
        return sb.toString();
    }

    public static int getRandomInt(int range) {
        Random r = new Random();
        return r.nextInt(range);
    }
}
