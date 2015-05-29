// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IPrizeWebService.java

package com.david.web.wanmei.wmeovg.request.hessian;

import com.david.web.wanmei.wmeovg.request.entity.GoodsLog;
import com.david.web.wanmei.wmeovg.request.entity.PrizeLog;
import java.util.Date;
import java.util.List;

public interface IPrizeWebService
{

	public abstract List queryByAccount(String s);

	public abstract int sendGoods(GoodsLog goodslog, String s);

	public abstract List queryByRequestTime(Date date, Date date1);

	public abstract PrizeLog queryByGid(String s);

	public abstract GoodsLog queryGoodsByGid(String s);

	public abstract int send(String s, String s1, String s2, Integer integer, String s3, String s4, Integer integer1,
                             Integer integer2, Byte byte1, String s5, String s6, String s7);

	public abstract int sendByMZRoleId(String s, String s1, String s2, Integer integer, String s3, Long long1, Integer integer1,
                                       Integer integer2, Byte byte1, String s4, String s5, String s6);

	public abstract int sendByRoleId(String s, String s1, String s2, Integer integer, String s3, Integer integer1, Integer integer2,
                                     Integer integer3, Byte byte1, String s4, String s5, String s6);

    public abstract int sendByRoleIdWithTitleAndContent(String cid, String appid, String gid, Integer zoneid, String account,
                                               Long roleid, Integer prizeid, Integer count, Byte priority,
                                               String callback, String sign , String ip, String title, String content);

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
