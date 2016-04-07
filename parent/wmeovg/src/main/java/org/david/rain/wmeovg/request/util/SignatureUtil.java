package org.david.rain.wmeovg.request.util;

import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;
import org.david.rain.wmeovg.request.alg.Signature;
import org.david.rain.wmeovg.request.bean.PrizeBean;


/**
 * 
 * @ClassName SignatureUtil
 * @Description 客户端数字签名工具类
 * @version 1.0
 * @date 2010-8-5 下午06:01:06
 */
public class SignatureUtil {
	/**
	 * 计算请求签名
	 * @param prizeBean
	 * @param privateKey
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String sign(PrizeBean prizeBean, String privateKey) throws NoSuchAlgorithmException{
		StringBuffer sb = new StringBuffer();
		sb.append(prizeBean.getCid().trim());
		sb.append(prizeBean.getGid().trim());
		sb.append(prizeBean.getAccount().trim());
		sb.append(prizeBean.getRolename());
		sb.append(prizeBean.getZoneid());
		sb.append(prizeBean.getPrizeid());
		sb.append(prizeBean.getCount());
		sb.append(prizeBean.getPriority().ordinal());
		sb.append(prizeBean.getCallback().trim());
		
		sb.append(privateKey.trim());
		//System.out.println("callback server**"+sb.toString()+"**" + privateKey.trim() + "**");
		
		return sign(sb.toString());
	}
	
	/**
	 * @Description 字符串SHA加密
	 * @param raw
	 * @return String
	 * @throws NoSuchAlgorithmException
	 */
	public static String sign(String raw) throws NoSuchAlgorithmException{
		
		//BigInteger sha = new BigInteger(Signature.encryptSHA(raw.getBytes()));
		//System.out.println("callback server+++++++++++++++++++++   " + sha.toString(32));
		//return sha.toString(32);
		return new String( Hex.encodeHex(Signature.encryptSHA(raw.getBytes())));
	}
}
