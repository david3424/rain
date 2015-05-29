package org.david.rain.web.util.security;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.apache.commons.lang.ArrayUtils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 */
public class RSA {
	/**
	 * @title:PublicKey
	 * @Description:得到公钥
	 * @param: key 密钥字符串（base64编码）
	 * @return:PublicKey
	 * @throws:Exception
	 */
	public static PublicKey getPublicKey(String key) throws Exception {
		byte[] keyBytes;
		keyBytes = (new BASE64Decoder()).decodeBuffer(key);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(keySpec);
		return publicKey;
	}

	/**
	 * 
	 * @title:getPrivateKey
	 * @Description:得到私钥
	 * @param key 密钥字符串（base64编码）
	 * @throws Exception
	 * @return:PrivateKey
	 */
	public static PrivateKey getPrivateKey(String key) throws Exception {
		byte[] keyBytes;
		keyBytes=(new BASE64Decoder()).decodeBuffer(key);
		PKCS8EncodedKeySpec keySpec=new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory=KeyFactory.getInstance("RSA");
		PrivateKey privateKey=keyFactory.generatePrivate(keySpec);
		return privateKey;
	}
	
	/**
	 * 
	 *@title:getKeyString
	 *@Description:得到密钥字符串（base64编码）
	 *@return:String
	 * @param key
	 * @throws Exception
	 */
	public static String getKeyString(Key key)throws Exception{
		byte[] keyBytes=key.getEncoded();
		String s=(new BASE64Encoder()).encode(keyBytes);
		return s;
	}
	
	
	public static String encrypt(String public_key,String originalStr)throws Exception{
		PublicKey publicKey=getPublicKey(public_key);
		Cipher cipher=Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] plainText=originalStr.getBytes();
		byte[] encodeByteArray=new byte[]{};
		for(int i=0;i<plainText.length;i+=106){
			byte[] subarray=ArrayUtils.subarray(plainText, i, i+106);
			encodeByteArray=ArrayUtils.addAll(encodeByteArray, cipher.doFinal(subarray));
		}
		String enStr=new BASE64Encoder().encode(encodeByteArray);
		return enStr;
	}
	
	public static String decrypt(String private_key,String codeStr)throws Exception{
		PrivateKey privateKey=getPrivateKey(private_key);
		Cipher cipher=Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] deBytes=new BASE64Decoder().decodeBuffer(codeStr);
		byte[] decodeByteArray=new byte[]{};
		for(int i=0;i<deBytes.length;i+=128){
			byte[] subarray=ArrayUtils.subarray(deBytes, i, i+128);
			decodeByteArray=ArrayUtils.addAll(decodeByteArray, cipher.doFinal(subarray));
		}
		String deStr=new String(decodeByteArray);
		return deStr;
	}
	
	
	public static Map<String,String> genRsaKey()throws Exception{
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
		keyPairGen.initialize(1024);
		KeyPair keyPair=keyPairGen.generateKeyPair();
		PublicKey publicKey=(RSAPublicKey)keyPair.getPublic();
		PrivateKey privateKey=(RSAPrivateKey)keyPair.getPrivate();
		String publicKeyString=getKeyString(publicKey);
		String privateKeyString=getKeyString(privateKey);
		Map<String,String> map = new HashMap();
		map.put("private_key", privateKeyString);
		map.put("public_key",publicKeyString);
		return map;
	}
	
	public static void main(String[] args) throws Exception{
		String publicKeyString="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCvU/CDGV51iTpCnAC0yP/V7UumIeNs/KoHPB/t9MQK/XFaXeTjQVF8Rb9smOHdyMfiQIShJNVzhrO/1CnJSLgTxNhgngI5mdbCSh8R+OnnvxVNpEtRShQgMwe6EO253EX4zyCtJih8EOSH5lkuv8koLsWCVV6wLLjgmLugnl6kDwIDAQAB";
		String privateKeyString="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAK9T8IMZXnWJOkKcALTI/9XtS6Yh42z8qgc8H+30xAr9cVpd5ONBUXxFv2yY4d3Ix+JAhKEk1XOGs7/UKclIuBPE2GCeAjmZ1sJKHxH46ee/FU2kS1FKFCAzB7oQ7bncRfjPIK0mKHwQ5IfmWS6/ySguxYJVXrAsuOCYu6CeXqQPAgMBAAECgYEAoTtj9muh/hyQQykLTTV6b9SkuMZ5SoSgJ78W7dElZ6XiU+P81TAAK3RIsSl2rGozxtDmJvqkgHTjYAlP1BbXj3DBFlQKmb5+Mv/bD2pZhTJM4egSVcDwiK97zVn2Wp0W85YuGzrEuUFndOUs3p0rQMwPzi0xx/slPXTqGWoGO0ECQQDXze6sTVym8CzvG8/xLr9uoHOwezEBJDU9TloLIZanMYu5kCfwAvp6t31RcfjQM3X4zQ1D72eY+LiOZgME+napAkEAz/v9Zk9rqqxVLDHDyGKwPcH9FdPW3vBi+4zFUGwRuegyPYlg1J6iIcTlJfaA1uxXOmcOx4ZnKBeFOcHwXFhP9wJBAJTEr0NytWuT6LYRS0Ftw6RE0lwYIMkzp8wShnpj9lJeEn+ixBVuSDRu5+JMmoUU5L/Ww81Qbe+Djt8DrhMxgrkCQH2mZiNuLcSRkm89rWMdRA47aRPLxE8xeQsxs27vqxHeVqPYoBgzsg2mry1tK88sP1J4OnSOHToaY5FOOlD/xWcCQEOKUic/SNzTzU6lbxLNfu0Sr3gYUOJ/W1tIH6UpSnE3yExALJrlB+KN74dUdxZF/4Auy+aq5TQH6LrYmsUA9Gw=";
		
		/*Map map=genRsaKey();
		publicKeyString=(String)map.get("public_key");
		privateKeyString=(String)map.get("private_key");*/
		
		String origStr="zoneid=3601,server_id=1,userid=78846,roleid=4094,rolename=guest1,timestamp=1357868090";
		String str=encrypt(publicKeyString, origStr);
		System.out.println(str);
		System.out.println(decrypt(privateKeyString, str));
	}
}
