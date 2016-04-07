package org.david.rain.monitor.monitor.util.Security;
/**
 * Created by hddev.
 * User: 孟佳
 * Date: Nov 16, 2011
 * Time: 3:40:25 PM
 */
public class SecurityUtil {
	/**
	 *@Description: 通过key把originalStr进行“AES”加密，请勿在jsp中使用。
	 *@return:String 加密后的密文
	 * @param originalStr 待加密的明文
	 * @param key 密钥
	 */
	public static String aesEncode(String originalStr,String key)throws Exception{
		return AES.encrypt(key, originalStr);
	}
	
	/**
	 *@Description:通过key把codeStr进行“AES”解密，请勿在jsp中使用。
	 *@return:String 解密后的明文
	 * @param codeStr 待解密的密文
	 * @param key 密钥
	 */
	public static String aesDecode(String codeStr,String key)throws Exception{
		return AES.decrypt(key, codeStr);
	}
	
	
	
	
	/**
	 *@Description: 通过key把originalStr进行“DES”加密，请勿在jsp中使用。
	 *@return:String 加密后的密文
	 * @param originalStr 待加密的明文
	 * @param key 密钥
	 */
	public static String desEncode(String originalStr,String key)throws Exception{
		return DES.encrypt(key, originalStr);
	}
	
	/**
	 *@Description:通过key把codeStr进行“DES”解密，请勿在jsp中使用。
	 *@return:String 解密后的明文
	 * @param codeStr 待解密的密文
	 * @param key 密钥
	 */
	public static String desDecode(String codeStr,String key)throws Exception{
		return DES.decrypt(key, codeStr);
	}
	
	public static void main(String[] args) {
		String originalStr="pwmengjia";
		String key="aaaa";
		try{
			String mw=desEncode(aesEncode(originalStr, key),key);
			System.out.println(mw);
			String miw=aesDecode(desDecode(mw, key),key);
			System.out.println(miw);
			
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
}


