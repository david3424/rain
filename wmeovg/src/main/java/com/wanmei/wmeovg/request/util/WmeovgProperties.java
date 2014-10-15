// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   WmeovgProperties.java

package com.wanmei.wmeovg.request.util;

import com.wanmei.wmeovg.request.service.PrizeServiceImpl;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class WmeovgProperties
{

	private static String cid;
	private static String serverUrl;
	private static String keystorePath;
	private static String keystorePwd;
	private static String privateKey;
	private static String abroadCid;
	private static String abroadServerUrl;
	private static String abroadPrivateKey;

	public WmeovgProperties()
	{
	}

	public static String getCid()
	{
		return cid;
	}

	public static void setCid(String cid)
	{
		cid = cid;
	}

	public static void setServerUrl(String serverUrl)
	{
        WmeovgProperties.serverUrl = serverUrl;
	}

	public static String getServerUrl()
	{
		return serverUrl;
	}

	public static String getKeystorePath()
	{
		return keystorePath;
	}

	public static void setKeystorePath(String keystorePath)
	{
        WmeovgProperties.keystorePath = keystorePath;
	}

	public static String getKeystorePwd()
	{
		return keystorePwd;
	}

	public static void setKeystorePwd(String keystorePwd)
	{
        WmeovgProperties.keystorePwd = keystorePwd;
	}

	public static void setPrivateKey(String privateKey)
	{
        WmeovgProperties.privateKey = privateKey;
	}

	public static String getPrivateKey()
	{
		return privateKey;
	}

	public static String getAbroadCid()
	{
		return abroadCid;
	}

	public static void setAbroadCid(String abroadCid)
	{
        WmeovgProperties.abroadCid = abroadCid;
	}

	public static String getAbroadServerUrl()
	{
		return abroadServerUrl;
	}

	public static void setAbroadServerUrl(String abroadServerUrl)
	{
        WmeovgProperties.abroadServerUrl = abroadServerUrl;
	}

	public static String getAbroadPrivateKey()
	{
		return abroadPrivateKey;
	}

	public static void setAbroadPrivateKey(String abroadPrivateKey)
	{
        WmeovgProperties.abroadPrivateKey = abroadPrivateKey;
	}

	static 
	{
		String propertiesFileName = "/wmeovg.properties";
		InputStream is = PrizeServiceImpl.class.getResourceAsStream(propertiesFileName);
		Properties properties = new Properties();
		try
		{
			properties.load(is);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		cid = properties.getProperty("client_id").trim();
		setServerUrl(properties.getProperty("server_url").trim());
		setAbroadCid(properties.getProperty("hk_client_id").trim());
		setAbroadServerUrl(properties.getProperty("hk_server_url").trim());
		setAbroadPrivateKey(properties.getProperty("hk_private_key").trim());
		setKeystorePath(properties.getProperty("ssl_keystore_path").trim());
		setKeystorePwd(properties.getProperty("ssl_keystore_pwd").trim());
		setPrivateKey(properties.getProperty("private_key").trim());
	}
}
