// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BasePrizeLog.java

package com.david.web.pppppp.wmeovg.request.entity.base;

import com.david.web.pppppp.wmeovg.request.util.DateUtil;
import java.io.Serializable;
import java.util.Date;

public abstract class BasePrizeLog
	implements Serializable
{

	private static final long serialVersionUID = 1L;
	private String gid;
	private String cid;
	private String appid;
	private Integer zoneid;
	private String account;
	private String rolename;
	private Long roleid;
	private Integer prizeid;
	private Integer count;
	private Byte priority;
	private String callback;
	private Byte sendStatus;
	private Integer processCount;
	private Byte callbackStatus;
	private Date requestTime;
	private Date sendTime;
	private String messageId;
	private Byte prizeResendCount;
	private Date callbackTime;
	private Integer callbackHttpStatus;
	private Byte callbackCount;
	private String ip;
	private Byte prizeSendType;

	public BasePrizeLog()
	{
		initialize();
	}

	public BasePrizeLog(String gid)
	{
		setGid(gid);
		initialize();
	}

	protected abstract void initialize();

	public BasePrizeLog(String gid, String cid, String appid, Integer zoneid, String account, String rolename, Long roleid, 
			Integer prizeid, Integer count, Byte priority, String callback, Byte sendStatus, Integer processCount, Byte callbackStatus, 
			Date requestTime, Date sendTime, String messageId, Byte prizeResendCount, Date callbackTime, Integer callbackHttpStatus, Byte callbackCount, 
			String ip, Byte prizeSendType)
	{
		this.gid = gid;
		this.cid = cid;
		this.appid = appid;
		this.zoneid = zoneid;
		this.account = account;
		this.rolename = rolename;
		this.roleid = roleid;
		this.prizeid = prizeid;
		this.count = count;
		this.priority = priority;
		this.callback = callback;
		this.sendStatus = sendStatus;
		this.processCount = processCount;
		this.callbackStatus = callbackStatus;
		this.requestTime = requestTime;
		this.sendTime = sendTime;
		this.messageId = messageId;
		this.prizeResendCount = prizeResendCount;
		this.callbackTime = callbackTime;
		this.callbackHttpStatus = callbackHttpStatus;
		this.callbackCount = callbackCount;
		this.ip = ip;
		this.prizeSendType = prizeSendType;
	}

	public String toString()
	{
		String prize = (new StringBuilder()).append(" GID: ").append(gid).toString();
		prize = (new StringBuilder()).append(prize).append(" CID: ").append(cid).toString();
		prize = (new StringBuilder()).append(prize).append(" appid: ").append(appid).toString();
		prize = (new StringBuilder()).append(prize).append(" account: ").append(account).toString();
		prize = (new StringBuilder()).append(prize).append(" rolename: ").append(rolename).toString();
		prize = (new StringBuilder()).append(prize).append(" roleid: ").append(roleid).toString();
		prize = (new StringBuilder()).append(prize).append(" zoneid: ").append(zoneid).toString();
		prize = (new StringBuilder()).append(prize).append(" prizeid: ").append(prizeid).toString();
		prize = (new StringBuilder()).append(prize).append(" priority: ").append(priority).toString();
		prize = (new StringBuilder()).append(prize).append(" callback: ").append(callback).toString();
		prize = (new StringBuilder()).append(prize).append(" sendStatus: ").append(sendStatus).toString();
		prize = (new StringBuilder()).append(prize).append(" processCount: ").append(processCount).toString();
		prize = (new StringBuilder()).append(prize).append(" callbackStatus: ").append(callbackStatus).toString();
		prize = (new StringBuilder()).append(prize).append(" requestTime: ").append(DateUtil.format(requestTime, "yyyy-MM-dd HH:mm:ss")).toString();
		prize = (new StringBuilder()).append(prize).append(" sendtime: ").append(DateUtil.format(sendTime, "yyyy-MM-dd HH:mm:ss")).toString();
		prize = (new StringBuilder()).append(prize).append(" messageId：").append(messageId).toString();
		prize = (new StringBuilder()).append(prize).append(" prizeResendCount：").append(prizeResendCount).toString();
		prize = (new StringBuilder()).append(prize).append(" callbackTime: ").append(DateUtil.format(callbackTime, "yyyy-MM-dd HH:mm:ss")).toString();
		prize = (new StringBuilder()).append(prize).append(" callbackHttpStatus：").append(callbackHttpStatus).toString();
		prize = (new StringBuilder()).append(prize).append(" callbackCount：").append(callbackCount).toString();
		prize = (new StringBuilder()).append(prize).append(" ip: ").append(ip).toString();
		prize = (new StringBuilder()).append(prize).append(" prizeSendType: ").append(prizeSendType).toString();
		return prize;
	}

	public String getGid()
	{
		return gid;
	}

	public void setGid(String gid)
	{
		this.gid = gid;
	}

	public String getCid()
	{
		return cid;
	}

	public void setCid(String cid)
	{
		this.cid = cid;
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

	public Long getRoleid()
	{
		return roleid;
	}

	public void setRoleid(Long roleid)
	{
		this.roleid = roleid;
	}

	public Integer getPrizeid()
	{
		return prizeid;
	}

	public void setPrizeid(Integer prizeid)
	{
		this.prizeid = prizeid;
	}

	public Integer getCount()
	{
		return count;
	}

	public void setCount(Integer count)
	{
		this.count = count;
	}

	public Byte getPriority()
	{
		return priority;
	}

	public void setPriority(Byte priority)
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

	public Byte getSendStatus()
	{
		return sendStatus;
	}

	public void setSendStatus(Byte sendStatus)
	{
		this.sendStatus = sendStatus;
	}

	public Byte getCallbackStatus()
	{
		return callbackStatus;
	}

	public void setCallbackStatus(Byte callbackStatus)
	{
		this.callbackStatus = callbackStatus;
	}

	public Date getRequestTime()
	{
		return requestTime;
	}

	public void setRequestTime(Date requestTime)
	{
		this.requestTime = requestTime;
	}

	public Date getSendTime()
	{
		return sendTime;
	}

	public void setSendTime(Date sendTime)
	{
		this.sendTime = sendTime;
	}

	public String getMessageId()
	{
		return messageId;
	}

	public void setMessageId(String messageId)
	{
		this.messageId = messageId;
	}

	public Byte getPrizeResendCount()
	{
		return prizeResendCount;
	}

	public void setPrizeResendCount(Byte prizeResendCount)
	{
		this.prizeResendCount = prizeResendCount;
	}

	public Date getCallbackTime()
	{
		return callbackTime;
	}

	public void setCallbackTime(Date callbackTime)
	{
		this.callbackTime = callbackTime;
	}

	public Integer getCallbackHttpStatus()
	{
		return callbackHttpStatus;
	}

	public void setCallbackHttpStatus(Integer callbackHttpStatus)
	{
		this.callbackHttpStatus = callbackHttpStatus;
	}

	public Byte getCallbackCount()
	{
		return callbackCount;
	}

	public void setCallbackCount(Byte callbackCount)
	{
		this.callbackCount = callbackCount;
	}

	public String getIp()
	{
		return ip;
	}

	public void setIp(String ip)
	{
		this.ip = ip;
	}

	public Byte getPrizeSendType()
	{
		return prizeSendType;
	}

	public void setPrizeSendType(Byte prizeSendType)
	{
		this.prizeSendType = prizeSendType;
	}

	public void setProcessCount(Integer processCount)
	{
		this.processCount = processCount;
	}

	public Integer getProcessCount()
	{
		return processCount;
	}

	public void setAppid(String appid)
	{
		this.appid = appid;
	}

	public String getAppid()
	{
		return appid;
	}
}
