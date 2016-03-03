package org.david.rain.monitor.monitor.util.aes;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by hddev. User: 孟佳 Date: Nov 16, 2011 Time: 2:52:46 PM 完美时空活动�?��
 */
public class AES {
	public static final String VIPARA = "0102030405060708";  
	public static final String bm = "GBK";  
	 

	public static String encrypt(String dataPassword, String cleartext)  
	        throws Exception {  
	    IvParameterSpec zeroIv = new IvParameterSpec(VIPARA.getBytes());  
	    SecretKeySpec key = new SecretKeySpec(dataPassword.getBytes(), "AES");  
	    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");  
	    cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);  
	    byte[] encryptedData = cipher.doFinal(cleartext.getBytes(bm));  
	  
	    return Base64.encode(encryptedData);  
	}  
	  
	public static String decrypt(String dataPassword, String encrypted)  
	        throws Exception {  
	    byte[] byteMi = Base64.decode(encrypted);  
	    IvParameterSpec zeroIv = new IvParameterSpec(VIPARA.getBytes());  
	    SecretKeySpec key = new SecretKeySpec(dataPassword.getBytes(), "AES");  
	    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");  
	    cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);  
	    byte[] decryptedData = cipher.doFinal(byteMi);  
	  
	    return new String(decryptedData,bm);  
	}  

	private static byte[] getRawKey(byte[] seed) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		sr.setSeed(seed);
		kgen.init(128, sr);
		//192 and 256 bits may not be available
		SecretKey skey = kgen.generateKey();
		byte[] raw = skey.getEncoded();
		return raw;
	}

	private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(clear);
		return encrypted;
	}

	private static byte[] decrypt(byte[] raw, byte[] encrypted)
			throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, skeySpec);
		byte[] decrypted = cipher.doFinal(encrypted);
		return decrypted;
	}

	public static String toHex(String txt) {
		return toHex(txt.getBytes());
	}

	public static String fromHex(String hex) {
		return new String(toByte(hex));
	}

	public static byte[] toByte(String hexString) {
		int len = hexString.length() / 2;
		byte[] result = new byte[len];
		for (int i = 0; i < len; i++)
			result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2),
					16).byteValue();
		return result;
	}

	public static String toHex(byte[] buf) {
		if (buf == null)
			return "";
		StringBuffer result = new StringBuffer(2 * buf.length);
		for (int i = 0; i < buf.length; i++) {
			appendHex(result, buf[i]);
		}
		return result.toString();
	}

	private final static String HEX = "0123456789ABCDEF";

	private static void appendHex(StringBuffer sb, byte b) {
		sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
	}
	
	private static String masterPassword="pwmengjia";
	private static String originalText="pwmengjia";
	
	
	public static String parseByte2HexStr(byte buf[]) {  
        StringBuffer sb = new StringBuffer();  
        for (int i = 0; i < buf.length; i++) {  
                String hex = Integer.toHexString(buf[i] & 0xFF);  
                if (hex.length() == 1) {  
                        hex = '0' + hex;  
                }  
                sb.append(hex.toUpperCase());  
        }  
        return sb.toString();  
}  
	
	public static byte[] parseHexStr2Byte(String hexStr) {  
        if (hexStr.length() < 1)  
                return null;  
        byte[] result = new byte[hexStr.length()/2];  
        for (int i = 0;i< hexStr.length()/2; i++) {  
                int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);  
                int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);  
                result[i] = (byte) (high * 16 + low);  
        }  
        return result;  
} 
	
//	public static void main(String[] args) {
//		try {
//			String step1des= DES.encrypt(originalText,masterPassword);
//			System.out.println("step1des:"+step1des);
//			// 加密�?
//			String step2aes = AES.encrypt(masterPassword, step1des);
//			System.out.println("step2aes:"+step2aes);
//			// 解密�?
//			String step3aes = AES.decrypt(masterPassword, step2aes);
//			System.out.println("step3aes:"+step3aes);
//			String step4des = DES.decrypt(step3aes,masterPassword);
//			System.out.println("step4des:"+step4des);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}

	
}
