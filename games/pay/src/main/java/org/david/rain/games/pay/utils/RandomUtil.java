package org.david.rain.games.pay.utils;

import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * Time: 12:11:54
 * 随机标识类.
 */
public class RandomUtil {
     // 得到随机字符串
    public static String getRandomString(int length) {
        String buffer = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ=+\\*&@()-";
        StringBuilder sb = new StringBuilder();
        Random r = new Random();
        int range = buffer.length();
        for (int i = 0; i < length; i++) {
            sb.append(buffer.charAt(r.nextInt(range)));
        }
        return sb.toString();
    }
}
