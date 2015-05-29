// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PrizeLog.java

package com.david.web.wanmei.wmeovg.request.entity;

import com.david.web.wanmei.wmeovg.request.entity.base.BasePrizeLog;
import java.util.Date;

public class PrizeLog extends BasePrizeLog
{

	private static final long serialVersionUID = 1L;

	public PrizeLog()
	{
		initialize();
	}

	public PrizeLog(String gid)
	{
		super(gid);
		initialize();
	}

	public PrizeLog(String gid, String cid, String appid, Integer zoneid, String account, String rolename, Long roleid, 
			Integer prizeid, Integer count, Byte priority, String callback, Byte sendStatus, Integer processCount, Byte callbackStatus, 
			Date requestTime, Date sendTime, String messageId, Byte prizeResendCount, Date callbackTime, Integer callbackHttpStatus, Byte callbackCount, 
			String ip, Byte prizeSendType)
	{
		super(gid, cid, appid, zoneid, account, rolename, roleid, prizeid, count, priority, callback, sendStatus, processCount, callbackStatus, requestTime, sendTime, messageId, prizeResendCount, callbackTime, callbackHttpStatus, callbackCount, ip, prizeSendType);
		initialize();
	}

	protected void initialize()
	{
	}
}
