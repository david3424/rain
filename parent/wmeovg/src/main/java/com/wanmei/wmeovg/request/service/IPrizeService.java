// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IPrizeService.java

package com.david.web.pppppp.wmeovg.request.service;

import com.david.web.pppppp.wmeovg.request.entity.GoodsLog;
import com.david.web.pppppp.wmeovg.request.entity.PrizeLog;
import com.david.web.pppppp.wmeovg.request.util.Priority;

import java.util.Date;
import java.util.List;

public interface IPrizeService
{

	public abstract int send(GoodsLog goodslog);

	public abstract String genGid();

	public abstract int send(String s, String s1, Integer integer, String s2, String s3, Integer integer1, String s4);

	public abstract int send(String s, String s1, Integer integer, String s2, String s3, Integer integer1, Integer integer2,
                             Priority priority, String s4);

	public abstract int sendByRoleId(String s, String s1, Integer integer, String s2, Integer integer1, Integer integer2, String s3);

	public abstract int sendByMZRoleId(String s, String s1, Integer integer, String s2, Long long1, Integer integer1, String s3);
    public abstract int sendByRoleIdWithTitleAndContent(String appid, String gid, Integer zoneid, String account, Long roleid, Integer prizeid, String callback, String title, String content);

    public abstract List queryByAccount(String s);

	public abstract List queryByRequestTime(Date date, Date date1);

	public abstract PrizeLog queryByGid(String s);

	public abstract GoodsLog queryGoodsByGid(String s);

	public abstract int resend(String s)
		throws Exception;

	public abstract int resendGoods(String s)
		throws Exception;

	public abstract int verifyRoleExists(String s, Integer integer, String s1);

	public abstract long verifyRoleExists2(String s, Integer integer, String s1);

	public abstract long verifyEncodeRoleExists(String s, Integer integer, String s1)
		throws Exception;

	public abstract long verifyEncodeRoleExists2(String s, Integer integer, String s1)
		throws Exception;
}
