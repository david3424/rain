package org.david.rain.pay.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
 * @ClassName Signature
 * @Description 加密及数字签名类
 * @version 1.0
 * @date 2010-8-5 下午06:01:55
 */
public class Signature {
	public static final String KEY_SHA = "SHA";
	/**
	 * BASE64解密
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptBASE64(String key) throws Exception {
		return (new BASE64Decoder()).decodeBuffer(key);
	}

	/**
	 * BASE64加密
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encryptBASE64(byte[] key) throws Exception {
		return (new BASE64Encoder()).encodeBuffer(key);
	}
	/**
	 * SHA加密
	 * 
	 * @param data
	 * @return
	 * @throws java.security.NoSuchAlgorithmException
	 * @throws Exception
	 */
	public static byte[] encryptSHA(byte[] data) throws NoSuchAlgorithmException{

		MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
		sha.update(data);

		return sha.digest();
	}
}
