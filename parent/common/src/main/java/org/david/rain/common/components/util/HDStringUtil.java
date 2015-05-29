package org.david.rain.common.components.util;

/**
 * Date: 12-8-1
 * Time: 下午3:59
 */
public class HDStringUtil {
    public static String hideUsername(String username) {
        if(null == username){
            return "";
        }
        if(username.length()<=4){
            return "****";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(username.substring(0, 2));
        for (int j = 0; j < username.length() - 4; j++) {
            sb.append("*");
        }
        sb.append(username.substring(username.length() - 2));
        return sb.toString();
    }

    public static String hidePhone(String phone){
        if(null == phone){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(phone.substring(0, 3));
        sb.append("****");
        sb.append(phone.substring(phone.length() - 4));
        return sb.toString();
    }

}
