// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PrizeBean.java

package com.david.web.wanmei.wmeovg.request.bean;

import com.david.web.wanmei.wmeovg.request.alg.Signature;
import com.david.web.wanmei.wmeovg.request.util.Priority;
import com.david.web.wanmei.wmeovg.request.util.WmeovgProperties;
import java.io.Serializable;

public class PrizeBean
	implements Serializable
{

	private String cid;
	private String appid;
	private String gid;
	private Integer zoneid;
	private String account;
	private String rolename;
	private Long roleid;
	private Integer prizeid;
	private Integer count;
	private Priority priority;
	private String callback;
	private Byte prizeSendType;
	private static final long serialVersionUID = 0x24200401f00b0fd6L;

	public PrizeBean()
	{
		count = Integer.valueOf(1);
	}

	public PrizeBean(String appid, String gid, Integer zoneid, String account, String rolename, Long roleid, Integer prizeid, 
			String callback, Byte prizeSendType)
		throws Exception
	{
		count = Integer.valueOf(1);
		this.zoneid = zoneid;
		this.account = account.trim();
		if (prizeSendType == null || prizeSendType.byteValue() == 0)
			this.rolename = Signature.encryptBASE64(rolename.getBytes("UTF-8"));
		this.roleid = roleid;
		this.prizeid = prizeid;
		this.callback = Signature.encryptBASE64(callback.trim().getBytes("UTF-8"));
		count = Integer.valueOf(1);
		priority = Priority.NORMAL;
		cid = WmeovgProperties.getCid();
		this.appid = appid;
		this.gid = gid;
		this.prizeSendType = prizeSendType;
	}

	public PrizeBean(String cid, String appid, String gid, Integer zoneid, String account, String rolename, Long roleid, 
			Integer prizeid, Integer count, Priority priority, String callback, Byte prizeSendType)
		throws Exception
	{
		this.count = Integer.valueOf(1);
		this.zoneid = zoneid;
		this.account = account.trim();
		if (prizeSendType == null || prizeSendType.byteValue() == 0)
			this.rolename = Signature.encryptBASE64(rolename.getBytes("UTF-8"));
		this.roleid = roleid;
		this.prizeid = prizeid;
		this.count = count;
		this.priority = priority;
		this.callback = Signature.encryptBASE64(callback.trim().getBytes("UTF-8"));
		this.cid = cid;
		this.appid = appid;
		this.gid = gid;
		this.prizeSendType = prizeSendType;
	}

	public String getCid()
	{
		return cid;
	}

	public void setCid(String cid)
	{
		this.cid = cid;
	}

	public String getGid()
	{
		return gid;
	}

	public void setGid(String gid)
	{
		this.gid = gid;
	}

	public Integer getZoneid()
	{
		return zoneid;
	}

	public void setZoneid(Integer zoneid)
	{
		this.zoneid = zoneid;
	}

	public String getAccount()
	{
		return account;
	}

	public void setAccount(String account)
	{
		this.account = account;
	}

	public String getRolename()
	{
		return rolename;
	}

	public void setRolename(String rolename)
	{
		this.rolename = rolename;
	}

	public Integer getCount()
	{
		return count;
	}

	public void setCount(Integer count)
	{
		this.count = count;
	}

	public Integer getPrizeid()
	{
		return prizeid;
	}

	public void setPrizeid(Integer prizeid)
	{
		this.prizeid = prizeid;
	}

	public Priority getPriority()
	{
		return priority;
	}

	public void setPriority(Priority priority)
	{
		this.priority = priority;
	}

	public String getCallback()
	{
		return callback;
	}

	public void setCallback(String callback)
	{
		this.callback = callback;
	}

	public void setAppid(String appid)
	{
		this.appid = appid;
	}

	public String getAppid()
	{
		return appid;
	}

	public void setRoleid(Long roleid)
	{
		this.roleid = roleid;
	}

	public Long getRoleid()
	{
		return roleid;
	}

	public void setPrizeSendType(Byte prizeSendType)
	{
		this.prizeSendType = prizeSendType;
	}

	public Byte getPrizeSendType()
	{
		return prizeSendType;
	}
}
