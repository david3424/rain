// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GoodsLog.java

package com.wanmei.wmeovg.request.entity;

import com.wanmei.wmeovg.request.util.DateUtil;
import java.io.Serializable;
import java.util.Date;

public class GoodsLog
	implements Serializable
{

	private static final long serialVersionUID = 1L;
	private long id;
	private String gid;
	private String cid;
	private String appid;
	private Long couponOrderid;
	private Integer zoneid;
	private Integer userid;
	private Long roleid;
	private String rolename;
	private String mailTitle;
	private String mailContext;
	private Integer goodsFlag;
	private Integer goodsPrice;
	private Integer goodsPriceBeforeDiscount;
	private Integer prizeid;
	private Integer attachMoney;
	private Integer reserved1;
	private Integer reserved2;
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

	public GoodsLog()
	{
	}

	public GoodsLog(String gid)
	{
		setGid(gid);
	}

	public GoodsLog(long id, String gid, String cid, String appid, Long couponOrderid, Integer zoneid, 
			Integer userid, Long roleid, String rolename, String mailTitle, String mailContext, Integer goodsFlag, Integer goodsPrice, 
			Integer goodsPriceBeforeDiscount, Integer prizeid, Integer attachMoney, Integer reserved1, Integer reserved2, Integer count, Byte priority, 
			String callback, Byte sendStatus, Integer processCount, Byte callbackStatus, Date requestTime, Date sendTime, String messageId, 
			Byte prizeResendCount, Date callbackTime, Integer callbackHttpStatus, Byte callbackCount, String ip, Byte prizeSendType)
	{
		this.id = id;
		this.gid = gid;
		this.cid = cid;
		this.appid = appid;
		this.couponOrderid = couponOrderid;
		this.zoneid = zoneid;
		this.userid = userid;
		this.roleid = roleid;
		this.rolename = rolename;
		this.mailTitle = mailTitle;
		this.mailContext = mailContext;
		this.goodsFlag = goodsFlag;
		this.goodsPrice = goodsPrice;
		this.goodsPriceBeforeDiscount = goodsPriceBeforeDiscount;
		this.prizeid = prizeid;
		this.attachMoney = attachMoney;
		this.reserved1 = reserved1;
		this.reserved2 = reserved2;
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
		prize = (new StringBuilder()).append(prize).append(" userid: ").append(userid).toString();
		prize = (new StringBuilder()).append(prize).append(" rolename: ").append(rolename).toString();
		prize = (new StringBuilder()).append(prize).append(" roleid: ").append(roleid).toString();
		prize = (new StringBuilder()).append(prize).append(" couponOrderid: ").append(couponOrderid).toString();
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

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getGid()
	{
		return gid;
	}

	public void setGid(String gid)
	{
		this.gid = gid != null ? gid.trim() : null;
	}

	public String getCid()
	{
		return cid;
	}

	public void setCid(String cid)
	{
		this.cid = cid != null ? cid.trim() : null;
	}

	public String getAppid()
	{
		return appid;
	}

	public void setAppid(String appid)
	{
		this.appid = appid != null ? appid.trim() : null;
	}

	public Integer getZoneid()
	{
		return zoneid;
	}

	public void setZoneid(Integer zoneid)
	{
		this.zoneid = zoneid;
	}

	public Integer getUserid()
	{
		return userid;
	}

	public void setUserid(Integer userid)
	{
		this.userid = userid;
	}

	public Long getRoleid()
	{
		return roleid;
	}

	public void setRoleid(Long roleid)
	{
		this.roleid = roleid;
	}

	public String getRolename()
	{
		return rolename;
	}

	public void setRolename(String rolename)
	{
		this.rolename = rolename != null ? rolename.trim() : null;
	}

	public String getMailTitle()
	{
		return mailTitle;
	}

	public void setMailTitle(String mailTitle)
	{
		this.mailTitle = mailTitle != null ? mailTitle.trim() : null;
	}

	public String getMailContext()
	{
		return mailContext;
	}

	public void setMailContext(String mailContext)
	{
		this.mailContext = mailContext != null ? mailContext.trim() : null;
	}

	public Integer getGoodsFlag()
	{
		return goodsFlag;
	}

	public void setGoodsFlag(Integer goodsFlag)
	{
		this.goodsFlag = goodsFlag;
	}

	public Integer getGoodsPrice()
	{
		return goodsPrice;
	}

	public void setGoodsPrice(Integer goodsPrice)
	{
		this.goodsPrice = goodsPrice;
	}

	public Integer getGoodsPriceBeforeDiscount()
	{
		return goodsPriceBeforeDiscount;
	}

	public void setGoodsPriceBeforeDiscount(Integer goodsPriceBeforeDiscount)
	{
		this.goodsPriceBeforeDiscount = goodsPriceBeforeDiscount;
	}

	public Integer getPrizeid()
	{
		return prizeid;
	}

	public void setPrizeid(Integer prizeid)
	{
		this.prizeid = prizeid;
	}

	public Integer getAttachMoney()
	{
		return attachMoney;
	}

	public void setAttachMoney(Integer attachMoney)
	{
		this.attachMoney = attachMoney;
	}

	public Integer getReserved1()
	{
		return reserved1;
	}

	public void setReserved1(Integer reserved1)
	{
		this.reserved1 = reserved1;
	}

	public Integer getReserved2()
	{
		return reserved2;
	}

	public void setReserved2(Integer reserved2)
	{
		this.reserved2 = reserved2;
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
		this.callback = callback != null ? callback.trim() : null;
	}

	public Byte getSendStatus()
	{
		return sendStatus;
	}

	public void setSendStatus(Byte sendStatus)
	{
		this.sendStatus = sendStatus;
	}

	public Integer getProcessCount()
	{
		return processCount;
	}

	public void setProcessCount(Integer processCount)
	{
		this.processCount = processCount;
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
		this.messageId = messageId != null ? messageId.trim() : null;
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
		this.ip = ip != null ? ip.trim() : null;
	}

	public Byte getPrizeSendType()
	{
		return prizeSendType;
	}

	public void setPrizeSendType(Byte prizeSendType)
	{
		this.prizeSendType = prizeSendType;
	}

	public void setCouponOrderid(Long couponOrderid)
	{
		this.couponOrderid = couponOrderid;
	}

	public Long getCouponOrderid()
	{
		return couponOrderid;
	}
}
