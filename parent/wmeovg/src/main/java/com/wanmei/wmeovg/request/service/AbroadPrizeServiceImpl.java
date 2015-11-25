// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AbroadPrizeServiceImpl.java

package com.david.web.pppppp.wmeovg.request.service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.david.web.pppppp.wmeovg.request.bean.PrizeBean;
import com.david.web.pppppp.wmeovg.request.entity.GoodsLog;
import com.david.web.pppppp.wmeovg.request.entity.PrizeLog;
import com.david.web.pppppp.wmeovg.request.hessian.IPrizeWebService;
import com.david.web.pppppp.wmeovg.request.util.Priority;
import com.david.web.pppppp.wmeovg.request.util.SignatureUtil;
import com.david.web.pppppp.wmeovg.request.util.WmeovgProperties;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

// Referenced classes of package com.david.web.pppppp.wmeovg.request.service:
//			IPrizeService

public class AbroadPrizeServiceImpl
	implements IPrizeService
{

	private static String cid = WmeovgProperties.getAbroadCid();
	private static String serverUrl;
	private static String keystorePath;
	private static String keystorePwd;
	private static String privateKey = WmeovgProperties.getAbroadPrivateKey();
	private SimpleDateFormat sdf;
	private static IPrizeWebService prizeWebService;

	public AbroadPrizeServiceImpl()
	{
		sdf = new SimpleDateFormat("yyyyMMdd");
	}

	public String genGid()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(cid);
		sb.append("-");
		sb.append(sdf.format(new Date()));
		sb.append("-");
		sb.append(UUID.randomUUID());
		return sb.toString();
	}

	public int send(GoodsLog goodsLog)
	{
		return commonSend(goodsLog);
	}

	public List queryByAccount(String account)
	{
		return prizeWebService.queryByAccount(account);
	}

	public PrizeLog queryByGid(String gid)
	{
		return prizeWebService.queryByGid(gid);
	}

	public GoodsLog queryGoodsByGid(String gid)
	{
		return prizeWebService.queryGoodsByGid(gid);
	}

	public List queryByRequestTime(Date start, Date end)
	{
		return prizeWebService.queryByRequestTime(start, end);
	}

	public int send(String appid, String gid, Integer zoneid, String account, String rolename, Integer prizeid, String callback)
	{
		int count = 1;
		Priority priority = Priority.NORMAL;
		return send(appid, gid, zoneid, account, rolename, prizeid, Integer.valueOf(count), priority, callback);
	}

	public int send(String appid, String gid, Integer zoneid, String account, String rolename, Integer prizeid, Integer count, 
			Priority priority, String callback)
	{
		return commonSend(appid, gid, zoneid, account, rolename, null, prizeid, count, priority, callback, Byte.valueOf((byte)0));
	}

	public int sendByRoleId(String appid, String gid, Integer zoneid, String account, Integer roleid, Integer prizeid, String callback)
	{
		int count = 1;
		Priority priority = Priority.NORMAL;
		return commonSend(appid, gid, zoneid, account, null, Long.valueOf(roleid.intValue()), prizeid, Integer.valueOf(count), priority, callback, Byte.valueOf((byte)1));
	}

	public int sendByMZRoleId(String appid, String gid, Integer zoneid, String account, Long roleid, Integer prizeid, String callback)
	{
		int count = 1;
		Priority priority = Priority.NORMAL;
		return commonSend(appid, gid, zoneid, account, null, roleid, prizeid, Integer.valueOf(count), priority, callback, Byte.valueOf((byte)2));
	}

    @Override
    public int sendByRoleIdWithTitleAndContent(String appid, String gid, Integer zoneid, String account, Long roleid, Integer prizeid, String callback, String title, String content) {
        return 0;
    }

    private int commonSend(String appid, String gid, Integer zoneid, String account, String rolename, Long roleid, Integer prizeid,
			Integer count, Priority priority, String callback, Byte prizeSendType)
	{
		PrizeBean prizeBean = null;
		String sign;
		String ip;
		try
		{
			prizeBean = new PrizeBean(cid, appid, gid, zoneid, account, rolename, roleid, prizeid, count, priority, callback, prizeSendType);
			sign = SignatureUtil.sign(prizeBean, privateKey);
			InetAddress localhost = InetAddress.getLocalHost();
			ip = localhost.getHostAddress();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return -1;
		}
		int rtn = -2;
		try
		{
			switch (prizeSendType.byteValue())
			{
			case 0: // '\0'
				rtn = prizeWebService.send(cid, appid, gid, zoneid, account, prizeBean.getRolename(), prizeid, count, Byte.valueOf((byte)prizeBean.getPriority().ordinal()), prizeBean.getCallback(), sign, ip);
				break;

			case 1: // '\001'
				rtn = prizeWebService.sendByRoleId(cid, appid, gid, zoneid, account, Integer.valueOf(prizeBean.getRoleid().intValue()), prizeid, count, Byte.valueOf((byte)prizeBean.getPriority().ordinal()), prizeBean.getCallback(), sign, ip);
				break;

			case 2: // '\002'
				rtn = prizeWebService.sendByMZRoleId(cid, appid, gid, zoneid, account, prizeBean.getRoleid(), prizeid, count, Byte.valueOf((byte)prizeBean.getPriority().ordinal()), prizeBean.getCallback(), sign, ip);
				break;

			default:
				rtn = -2;
				break;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return rtn;
	}

	private int commonSend(GoodsLog goodsLog)
	{
		goodsLog.setCid(cid);
		PrizeBean prizeBean = null;
		String sign;
		String ip;
		try
		{
			prizeBean = new PrizeBean();
			prizeBean.setGid(goodsLog.getGid());
			prizeBean.setCid(cid);
			prizeBean.setAppid(goodsLog.getAppid());
			sign = SignatureUtil.signGoods(prizeBean, privateKey);
			InetAddress localhost = InetAddress.getLocalHost();
			ip = localhost.getHostAddress();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return -1;
		}
		int rtn = -2;
		try
		{
			goodsLog.setIp(ip);
			rtn = prizeWebService.sendGoods(goodsLog, sign);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return rtn;
	}

	public int resend(String gid)
		throws Exception
	{
		return prizeWebService.resend(gid);
	}

	public int resendGoods(String gid)
		throws Exception
	{
		return prizeWebService.resendGoods(gid);
	}

	public int verifyRoleExists(String username, Integer zoneid, String rolename)
	{
		return prizeWebService.verifyRoleExists(username, zoneid, rolename);
	}

	public long verifyRoleExists2(String username, Integer zoneid, String rolename)
	{
		return prizeWebService.verifyRoleExists2(username, zoneid, rolename);
	}

	public long verifyEncodeRoleExists(String username, Integer zoneid, String encodeRolename)
		throws Exception
	{
		return prizeWebService.verifyEncodeRoleExists(username, zoneid, encodeRolename);
	}

	public long verifyEncodeRoleExists2(String username, Integer zoneid, String encodeRolename)
		throws Exception
	{
		return prizeWebService.verifyEncodeRoleExists2(username, zoneid, encodeRolename);
	}

	static 
	{
		serverUrl = WmeovgProperties.getAbroadServerUrl();
		keystorePath = WmeovgProperties.getKeystorePath();
		keystorePwd = WmeovgProperties.getKeystorePwd();
		System.setProperty("java.protocol.handler.pkgs", "javax.net.ssl");
		System.setProperty("javax.net.ssl.keyStoreType", "PKCS12");
		System.setProperty("javax.net.ssl.keyStore", keystorePath);
		System.setProperty("javax.net.ssl.keyStorePassword", keystorePwd);
		HostnameVerifier hv = new HostnameVerifier() {

			public boolean verify(String urlHostName, SSLSession sslSession)
			{
				return urlHostName.equals(sslSession.getPeerHost());
			}

		};
		HttpsURLConnection.setDefaultHostnameVerifier(hv);
		try
		{
			String url = (new StringBuilder()).append(serverUrl).append("/hessian/prizeService").toString();
			HessianProxyFactory factory = new HessianProxyFactory();
			prizeWebService = (IPrizeWebService)factory.create(IPrizeWebService.class, url);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
