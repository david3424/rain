// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Signature.java

package com.david.web.wanmei.wmeovg.request.alg;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Signature
{

	public static final String KEY_SHA = "SHA";

	public Signature()
	{
	}

	public static byte[] decryptBASE64(String key)
		throws Exception
	{
		return (new BASE64Decoder()).decodeBuffer(key);
	}

	public static String encryptBASE64(byte key[])
		throws Exception
	{
		return (new BASE64Encoder()).encodeBuffer(key);
	}

	public static byte[] encryptSHA(byte data[])
		throws NoSuchAlgorithmException
	{
		MessageDigest sha = MessageDigest.getInstance("SHA");
		sha.update(data);
		return sha.digest();
	}
}
