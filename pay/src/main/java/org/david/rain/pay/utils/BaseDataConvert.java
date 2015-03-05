package org.david.rain.pay.utils;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class BaseDataConvert {

    /**
     * java二进制,字节数组,字符,十六进制,BCD编码转换2007-06-07 00:17
     * <p/>
     * 把16进制字符串转换成字节数组
     *
     * @param hex
     * @return
     */

    public static byte[] hexStringToByte(String hex) {

        int len = (hex.length() / 2);

        byte[] result = new byte[len];

        char[] achar = hex.toCharArray();

        for (int i = 0; i < len; i++) {

            int pos = i * 2;

            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));

        }

        return result;

    }

    private static byte toByte(char c) {

        byte b = (byte) "0123456789ABCDEF".indexOf(c);

        return b;

    }

    /**
     * 把字节数组转换成16进制字符串
     *
     * @param bArray
     * @return
     */

    public static final String bytesToHexString(byte[] bArray) {

        StringBuffer sb = new StringBuffer(bArray.length);

        String sTemp;

        for (int i = 0; i < bArray.length; i++) {

            sTemp = Integer.toHexString(0xFF & bArray[i]);

            if (sTemp.length() < 2)

                sb.append(0);

            sb.append(sTemp.toUpperCase());

        }

        return sb.toString();

    }

    /**
     * 把字节数组转换为对象
     *
     * @param bytes
     * @return
     * @throws java.io.IOException
     * @throws ClassNotFoundException
     */

    public static final Object bytesToObject(byte[] bytes) throws IOException,
            ClassNotFoundException {

        ByteArrayInputStream in = new ByteArrayInputStream(bytes);

        ObjectInputStream oi = new ObjectInputStream(in);

        Object o = oi.readObject();

        oi.close();

        return o;

    }

    /**
     * 把可序列化对象转换成字节数组
     *
     * @param s
     * @return
     * @throws java.io.IOException
     */

    public static final byte[] objectToBytes(Serializable s) throws IOException {

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        ObjectOutputStream ot = new ObjectOutputStream(out);

        ot.writeObject(s);

        ot.flush();

        ot.close();

        return out.toByteArray();

    }

    public static final String objectToHexString(Serializable s)
            throws IOException {

        return bytesToHexString(objectToBytes(s));

    }

    public static final Object hexStringToObject(String hex)
            throws IOException, ClassNotFoundException {

        return bytesToObject(hexStringToByte(hex));

    }

    /**
     * @函数功能: BCD码转为10进制串(阿拉伯数据)
     * @输入参数: BCD码
     * @输出结果: 10进制串
     */

    public static String bcd2Str(byte[] bytes) {

        StringBuffer temp = new StringBuffer(bytes.length * 2);

        for (int i = 0; i < bytes.length; i++) {

            temp.append((byte) ((bytes[i] & 0xf0) >>> 4));

            temp.append((byte) (bytes[i] & 0x0f));

        }

        return temp.toString().substring(0, 1).equalsIgnoreCase("0") ? temp
                .toString().substring(1) : temp.toString();

    }

    /**
     *
     * @函数功能: 10进制串转为BCD码
     *
     * @输入参数: 10进制串
     *
     * @输出结果: BCD码


    public static byte[] str2Bcd(String asc) {

    int len = asc.length();

    int mod = len % 2;

    if (mod != 0) {

    asc = "0" + asc;

    len = asc.length();

    }

    byte abt[] = new byte[len];

    if (len >= 2) {

    len = len / 2;

    }

    byte bbt[] = new byte[len];

    abt = asc.getBytes();

    int j, k;

    for (int p = 0; p < asc.length() / 2; p++) {

    if ((abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {

    j = abt[2 * p] - '0';

    } else if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {

    j = abt[2 * p] - 'a' + 0x0a;

    } else {

    j = abt[2 * p] - 'A' + 0x0a;

    }

    if ((abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {

    k = abt[2 * p + 1] - '0';

    } else if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {

    k = abt[2 * p + 1] - 'a' + 0x0a;

    } else {

    k = abt[2 * p + 1] - 'A' + 0x0a;

    }

    int a = (j << 4) + k;

    byte b = (byte) a;

    bbt[p] = b;

    }

    return bbt;

    } */

    /**
     *
     * @函数功能: BCD码转ASC码
     *
     * @输入参数: BCD串
     *
     * @输出结果: ASC码
     *
     *
     *
     *        public static String BCD2ASC(byte[] bytes) {
     *
     *        StringBuffer temp = new StringBuffer(bytes.length * 2);
     *
     *        for (int i = 0; i < bytes.length; i++) {
     *
     *        int h = ((bytes[i] & 0xf0) >>> 4);
     *
     *        int l = (bytes[i] & 0x0f);
     *
     *        temp.append(BToA[h]).append( BToA[l]);
     *
     *        }
     *
     *        return temp.toString() ;
     *
     *        }
     */

    /**
     * MD5加密字符串，返回加密后的16进制字符串
     *
     * @param origin
     * @return
     */

    public static String MD5EncodeToHex(String origin) {

        return bytesToHexString(MD5Encode(origin));

    }

    /**
     * MD5加密字符串，返回加密后的字节数组
     *
     * @param origin
     * @return
     */

    public static byte[] MD5Encode(String origin) {

        return MD5Encode(origin.getBytes());

    }

    /**
     * MD5加密字节数组，返回加密后的字节数组
     *
     * @param bytes
     * @return
     */

    public static byte[] MD5Encode(byte[] bytes) {

        MessageDigest md = null;

        try {

            md = MessageDigest.getInstance("MD5");

            return md.digest(bytes);

        } catch (NoSuchAlgorithmException e) {

            e.printStackTrace();

            return new byte[0];

        }

    }

    public static String stringToHexString(String strPart) {
        String hexString = "";
        for (int i = 0; i < strPart.length(); i++) {
            int ch = (int) strPart.charAt(i);
            String strHex = Integer.toHexString(ch);
            hexString = hexString + strHex;
        }
        return hexString;
    }

    private static String hexString = "0123456789ABCDEF";

    /*
      * 将字符串编码成16进制数字,适用于所有字符（包括中文）
      */

    public static String encode(String str) {
        // 根据默认编码获取字节数组
        byte[] bytes = str.getBytes();
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        // 将字节数组中每个字节拆解成2位16进制整数
        for (int i = 0; i < bytes.length; i++) {
            sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
            sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
        }
        return sb.toString();
    }

    /*
      * 将16进制数字解码成字符串,适用于所有字符（包括中文）
      */

    public static String decode(String bytes) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(
                bytes.length() / 2);
        // 将每2位16进制整数组装成一个字节
        for (int i = 0; i < bytes.length(); i += 2)
            baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString
                    .indexOf(bytes.charAt(i + 1))));
        return new String(baos.toByteArray());
    }

    private static byte uniteBytes(byte src0, byte src1) {
        byte _b0 = Byte.decode("0x" + new String(new byte[]{src0}))
                .byteValue();
        _b0 = (byte) (_b0 << 4);
        byte _b1 = Byte.decode("0x" + new String(new byte[]{src1}))
                .byteValue();
        byte ret = (byte) (_b0 | _b1);
        return ret;
    }

    public static byte[] HexString2Bytes(String src) {
        byte[] ret = new byte[6];
        byte[] tmp = src.getBytes();
        for (int i = 0; i < 6; ++i) {
            ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
		}
		return ret;
	}


//	private byte charToByte(char c) {
//			return (byte) "0123456789ABCDEF".indexOf(c);
//	　　}

}