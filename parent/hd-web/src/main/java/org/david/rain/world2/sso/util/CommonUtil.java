// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CommonUtil.java

package org.david.rain.world2.sso.util;

import java.io.UnsupportedEncodingException;

public class CommonUtil {

    public static final String USER = "USER";
    public static final String SSOSERVER_LOGIN = "/SSOServerLogin";

    public CommonUtil() {
    }

    public static String byte2hex(byte b[]) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0xff);
            if (stmp.length() == 1)
                hs = (new StringBuilder(String.valueOf(hs))).append("0").append(stmp).toString();
            else
                hs = (new StringBuilder(String.valueOf(hs))).append(stmp).toString();
        }

        return hs;
    }

    public static byte[] hex2Byte(String b) {
        char data[] = b.toCharArray();
        int l = data.length;
        byte out[] = new byte[l >> 1];
        int i = 0;
        for (int j = 0; j < l; ) {
            int f = Character.digit(data[j++], 16) << 4;
            f |= Character.digit(data[j++], 16);
            out[i] = (byte) (f & 0xff);
            i++;
        }

        return out;
    }

    public static String string2hex(String str) {
        String hexStr = "";
        byte b[] = (byte[]) null;
        try {
            b = str.getBytes("ISO-8859-1");
        } catch (UnsupportedEncodingException ex) {
            System.out.println(ex);
        }
        hexStr = byte2hex(b);
        return hexStr;
    }

    public static byte[] copyBytes(byte arr[], int start, int length) {
        byte newArr[] = (byte[]) null;
        newArr = new byte[length];
        for (int i = 0; i < length; i++)
            newArr[i] = arr[i + start];

        return newArr;
    }
}
