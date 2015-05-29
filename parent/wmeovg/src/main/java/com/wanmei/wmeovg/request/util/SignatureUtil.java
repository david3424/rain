// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SignatureUtil.java

package com.david.web.wanmei.wmeovg.request.util;

import com.david.web.wanmei.wmeovg.request.alg.Signature;
import com.david.web.wanmei.wmeovg.request.bean.PrizeBean;
import org.apache.commons.codec.binary.Hex;

import java.security.NoSuchAlgorithmException;

// Referenced classes of package com.david.web.wanmei.wmeovg.request.util:
//			Priority

public class SignatureUtil
{

	public SignatureUtil()
	{
	}

	public static String signGoods(PrizeBean prizeBean, String privateKey)
		throws NoSuchAlgorithmException
	{
		StringBuffer sb = new StringBuffer();
		sb.append(prizeBean.getGid().trim());
		sb.append(prizeBean.getCid().trim());
		sb.append(prizeBean.getAppid());
		sb.append(privateKey.trim());
		System.out.println((new StringBuilder()).append("server(").append(sb.toString()).append(")").append(privateKey.trim()).append("**client").toString());
		return sign(sb.toString());
	}

	public static String sign(PrizeBean prizeBean, String privateKey)
		throws NoSuchAlgorithmException
	{
		StringBuffer sb = new StringBuffer();
		sb.append(prizeBean.getGid().trim());
		sb.append(prizeBean.getCid().trim());
		sb.append(prizeBean.getAppid());
		sb.append(prizeBean.getAccount().trim());
		if (prizeBean.getPrizeSendType() == null || prizeBean.getPrizeSendType().byteValue() == 0)
			sb.append(prizeBean.getRolename());
		else
			sb.append((new StringBuilder()).append("_").append(prizeBean.getRoleid()).append("_").toString());
		sb.append(prizeBean.getZoneid());
		sb.append(prizeBean.getPrizeid());
		sb.append(prizeBean.getCount());
		sb.append(prizeBean.getPriority().ordinal());
		sb.append(prizeBean.getCallback().trim());
		sb.append(privateKey.trim());
		return sign(sb.toString());
	}

	public static String sign(String raw)
		throws NoSuchAlgorithmException
	{
		return new String(Hex.encodeHex(Signature.encryptSHA(raw.getBytes())));
	}

	public static void main(String args[])
		throws Exception
	{
		String raw = "0123456789我们都是中国人";
		String encrypt1 = sign(raw);
		System.out.println(encrypt1);
		String encrypt2 = Signature.encryptBASE64(raw.getBytes());
		System.out.println(encrypt2);
		raw = "Na〞vi妮特丽\016 Na〞vi妮特丽\016 ";
		String en = Signature.encryptBASE64(raw.getBytes("UTF-8"));
		System.out.println(en);
		String de = new String(Signature.decryptBASE64(en));
		System.out.print(de);
	}
}
