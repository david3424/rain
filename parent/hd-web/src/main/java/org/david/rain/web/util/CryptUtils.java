package org.david.rain.web.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.*;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class CryptUtils {
    private static Logger logger = LoggerFactory.getLogger(CryptUtils.class);

    /**
     * 生成token
     * 算法：
     * 先对data进行aes加密，然后计算md5
     *
     * @param data     明文字符串
     * @param password aes密码
     * @return 加密后的md5串
     * @throws java.io.UnsupportedEncodingException
     *
     */
    public static String createToken(String data, String password) throws UnsupportedEncodingException {

        if (data != null && password != null) {
            byte[] encryptdate = encrypt(data.getBytes("UTF-8"), password);
            if (encryptdate != null) {
                return getMd5str(encryptdate);
            }
        }
        return null;
    }

    /**
     * 生成token
     * 算法：
     * 计算md5值
     *
     * @param data 明文字符串
     * @return 加密后的md5串
     */
    public static String createToken(String data) {
        if (data != null) {
            return getMd5str(data);
        }
        return null;
    }

    /**
     * 取字节数组的md5值的十六进制串表示
     *
     * @param b
     * @return
     */
    public static String getMd5str(byte[] b) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return bytetoHexStringLowwer(md.digest(b));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 取字节数组的md5值的十六进制串表示
     *
     * @return
     * @throws java.io.UnsupportedEncodingException
     *
     */
    public static String getMd5str(String str) {
        try {
            return getMd5str(str.getBytes("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 取字节数组的md5值的十六进制串表示
     *
     * @return
     * @throws java.io.UnsupportedEncodingException
     *
     */
    public static String getMd5str(Object... strs) {
        StringBuffer sbuf = new StringBuffer();
        if (null != strs) {
            for (Object s : strs) {
                sbuf.append(s != null ? s.toString() : "");
            }
        }
        return getMd5str(sbuf.toString());
    }

    /**
     * 将字节数组，转换为字符串
     *
     * @param data
     * @return
     */
    public static String bytetoHexStringUpper(byte[] data) {
        char hexdigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        return bytetoHexString(data, hexdigits);
    }

    /**
     * 将字节数组，转换为字符串
     *
     * @param data
     * @return
     */
    public static String bytetoHexStringLowwer(byte[] data) {
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

    public static String getBase64str(byte[] b) {
        BASE64Encoder be = new BASE64Encoder();
        return be.encode(b);
    }


    /**
     * 通过aes加密
     *
     * @param data
     * @return
     * @throws java.io.UnsupportedEncodingException
     *
     */
    public static byte[] encrypt(byte[] data, String password) {
        try {
            KeyGenerator keyG = KeyGenerator.getInstance("AES");
            SecureRandom instance = SecureRandom.getInstance("SHA1PRNG");
            instance.setSeed(password.getBytes("UTF-8"));
            keyG.init(128, instance);
            SecretKey key = keyG.generateKey();
            byte[] encodeFormat = key.getEncoded();
            SecretKeySpec sks = new SecretKeySpec(encodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, sks);
            return cipher.doFinal(data);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过aes解密
     *
     * @param data
     * @return
     */
    public static byte[] dencrypt(byte[] data, String password) {
        try {
            KeyGenerator keyG = KeyGenerator.getInstance("AES");
            SecureRandom instance = SecureRandom.getInstance("SHA1PRNG");
            instance.setSeed(password.getBytes("UTF-8"));
            keyG.init(128, instance);
            SecretKey key = keyG.generateKey();
            byte[] encodeFormat = key.getEncoded();
            SecretKeySpec sks = new SecretKeySpec(encodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, sks);
            return cipher.doFinal(data);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] getByteOfBase64str(String base64str) {
        BASE64Decoder be = new BASE64Decoder();
        try {
            return be.decodeBuffer(base64str);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String base64Encoder(String str) throws UnsupportedEncodingException {
        BASE64Encoder encode = new BASE64Encoder();
        if (str != null) {
            return encode.encode((str.getBytes("UTF-8")));
        }
        return "";
    }

    public static String sha1Encoder(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return bytetoHexStringLowwer(messageDigestEncrypt(str.getBytes("UTF-8"), "SHA1"));
    }

    public static String sha1Strings(Object... strs) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        StringBuffer sbuf = new StringBuffer();
        if (null != strs) {
            for (Object s : strs) {
                sbuf.append(s != null ? s.toString() : "");
            }
        }
        return sha1Encoder(sbuf.toString());
    }

    public static String encrypt(String strSrc, String encName) {

        MessageDigest md = null;
        String strDes = null;

        try {
            if (StringUtils.isEmpty(encName)) {

                encName = "SHA-256";
            }
            md = MessageDigest.getInstance(encName);
            md.update(strSrc.getBytes());
            strDes = bytetoHexStringLowwer(md.digest());
        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }

        return strDes;
    }


    public static byte[] messageDigestEncrypt(byte[] data, String key) throws NoSuchAlgorithmException {
        MessageDigest sha = MessageDigest.getInstance(key);
        sha.update(data);
        return sha.digest();
    }

    public static byte[] hmacSha(String key, byte[] datas, String secret) {
        try {
            if (null == key) {
                key = "HmacSHA1";
            }
            // Get an hmac_sha1 key from the raw key bytes
            byte[] keyBytes = key.getBytes();
            SecretKeySpec signingKey = new SecretKeySpec(keyBytes, "HmacSHA1");

            // Get an hmac_sha1 Mac instance and initialize with the signing key
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);
            // Compute the hmac on input data bytes
            return mac.doFinal(datas);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 参数签名
     *
     * @param params
     * @param secret
     * @return
     */
    public static String signature(Map<String, Object> params, String secret) {
        StringBuffer sbuf = new StringBuffer();
        if (null != params) {
            Map<String, Object> treeParam = new TreeMap<String, Object>();
            treeParam.putAll(params);
            int i = 0;
            for (Map.Entry<String, Object> e : treeParam.entrySet()) {
                if (i++ != 0) {
                    sbuf.append("&");
                }
                sbuf.append(e.getKey());
                sbuf.append("=");
                sbuf.append(e.getValue() == null ? "" : e.getValue().toString());
            }
            sbuf.append("&secret=");
            sbuf.append(secret);
            try {
                return getMd5str(sbuf.toString().getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }

        }
        return "";
    }

    /**
     * 生成客户端密钥
     *
     * @param clientId
     * @return
     */
    public static String createSecret(int clientId) {
        StringBuffer sbuf = new StringBuffer();
        sbuf.append(clientId);
        sbuf.append(System.currentTimeMillis());
        try {
            return getMd5str(sbuf.toString().getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static int parse(char c) {
        if (c >= 'a')
            return (c - 'a' + 10) & 0x0f;
        if (c >= 'A')
            return (c - 'A' + 10) & 0x0f;
        return (c - '0') & 0x0f;
    }

    // 从十六进制字符串到字节数组转换
    public static byte[] hexString2Bytes(String hexstr) {
        byte[] b = new byte[hexstr.length() / 2];
        int j = 0;
        for (int i = 0; i < b.length; i++) {
            char c0 = hexstr.charAt(j++);
            char c1 = hexstr.charAt(j++);
            b[i] = (byte) ((parse(c0) << 4) | parse(c1));
        }
        return b;
    }

    public static String parse4GamesCookie(String key, String cookie) {
        if (cookie == null || cookie.trim().length() == 0) {
            logger.info("cookie value is empty");
            return null;
        }
        String[] vs = cookie.split("\\|");
        if (vs == null || vs.length != 3) {
            logger.info("cookie value is not valid: cookie={}", cookie);
            return null;
        }
        String md5str = getMd5str(key);
        if (!md5str.equals(vs[1])) {
            logger.info("cookie value(md5) is not valid: md5str={}", md5str);
            return null;
        }
        long expire = Long.parseLong(vs[2]);
        System.out.println("vs[2]=="+expire+",expire="+expire);
        if (expire < new Date().getTime()) {
            System.out.println("date.gettime=="+new Date().getTime()+",expire="+expire);
            logger.info("cookie value is expired: expire={}", expire);
            return null;
        }
        try {
//		return new String(dencrypt(hexString2Bytes(vs[0]),key),"UTF-8");
            return aesIvDecode(vs[0], key, key);
        } catch (Exception e) {
            logger.info("cookie value parse exception: cookie={}", cookie);
        }
        return null;
    }


    public static final String AES_CBC_NO_PADDING = "AES/CBC/NoPadding";
     public static final String AES = "AES";
     public static final Charset CHAR_SET_UTF_8 = Charset.forName("UTF-8");
     public static String aesIvEncode(String dataEncrypt,String key,String iv) throws Exception {
         try {
         	Cipher cipher = Cipher.getInstance(AES_CBC_NO_PADDING);

             byte[] dataBytes = dataEncrypt.getBytes(CHAR_SET_UTF_8);

             int plaintextLength = dataBytes.length;
             int blockSize = cipher.getBlockSize();
             if (plaintextLength % blockSize != 0) {
           	  plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
             }
             byte[] plaintext = new byte[plaintextLength];

             System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);

             SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(CHAR_SET_UTF_8), AES);
             IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes(CHAR_SET_UTF_8));

             cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);

             byte[] encrypted = cipher.doFinal(plaintext);

   //          return new sun.misc.BASE64Encoder().encode(encrypted).replaceAll("[\r\n]", "");
   //          return new sun.misc.BASE64Encoder().encode(encrypted);
             return bytetoHexStringLowwer(encrypted);
         } catch (Exception e) {
             e.printStackTrace();
             return null;
         }
     }

     public static String aesIvDecode(String dataDesEncrypt,String key,String iv) throws Exception {
         try {
   //    	  byte[] encrypted1 = new BASE64Decoder().decodeBuffer(dataDesEncrypt);
             byte[] encrypted1 = hexString2Bytes(dataDesEncrypt);

             Cipher cipher = Cipher.getInstance(AES_CBC_NO_PADDING);

             SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(CHAR_SET_UTF_8), "AES");
             IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes(CHAR_SET_UTF_8));

             cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);

             byte[] original = cipher.doFinal(encrypted1);
             int i = 0;
             int c = original.length;
             while(original[--c] == 0 && c >= 0){
           	  i ++ ;
             }
             int ocLength = original.length - i;
             byte[] origCopy = new byte[ocLength];
             System.arraycopy(original, 0, origCopy, 0, ocLength);

             String originalString = new String(origCopy,CHAR_SET_UTF_8);

             return originalString;
         } catch (Exception e) {
             e.printStackTrace();
             return null;
         }
     }

    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException, IOException, NoSuchProviderException {
//    	System.out.println(encrypt("mycardwmskGFD008719000040714gamesingame", "SHA-256"));
       /* String username = "1@1.com";
        Integer userId = 310010760;
        String k = "L50%1YQ?L10!VHE$";
        String md5str = CryptUtils.getMd5str(k);
        StringBuffer sbuf = new StringBuffer();
        sbuf.append(CryptUtils.bytetoHexStringLowwer(CryptUtils.encrypt((userId + "," + username).getBytes("UTF-8"), k)));
        sbuf.append("|");
        sbuf.append(md5str);
        sbuf.append("|");
        sbuf.append(new Date().getTime() + 3 * 60 * 60 * 1000);
        System.out.println(sbuf.toString());
        System.out.println(parse4GamesCookie(k, sbuf.toString()));
        System.out.println(getMd5str(k));*/
//    	System.out.println(parse4GamesCookie(k, "18521930ba35b476535f7e8ca872f1b01ea5cf97884a16532bbb05ec3a0c88ba|b9108381e3a0917a0abd758524ad856c|1358864605728"));
        String k = "L50%1YQ?L10!VHE$";
    	System.out.println(parse4GamesCookie(k, "c5fc3d96c597d0ef2524d5501d51d1450f7c264dc63f06addfbe30588743556c75b8abb849f4b0ee3f02156c63377c7ced7d0335b34dbf6e52d9750bac65a7a269c2c371f239ba4f444a61e9a604ca20|b9108381e3a0917a0abd758524ad856c|1368167865904"));
        System.out.println(12|4444<<16);
        System.out.println(291241984>>16);

        System.out.println(291241996^12);
        System.out.println((291241996^12)>>16);

    }
}
