// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PrizeServiceManager.java

package com.david.web.pppppp.wmeovg.request.service;


// Referenced classes of package com.david.web.pppppp.wmeovg.request.service:
//			PrizeServiceImpl, AbroadPrizeServiceImpl, IPrizeService

public class PrizeServiceManager
{

	public static IPrizeService prizeService;
	public static IPrizeService abroadPrizeService;

	public PrizeServiceManager()
	{
	}

	static 
	{
		if (null == prizeService)
			prizeService = new PrizeServiceImpl();
		if (null == abroadPrizeService)
			abroadPrizeService = new AbroadPrizeServiceImpl();
	}
}
