// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DateUtil.java

package com.david.web.pppppp.wmeovg.request.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil
{

	public static final String DATETIME = "yyyy-MM-dd HH:mm:ss";

	public DateUtil()
	{
	}

	public static String format(Date date, String format)
	{
		if (null == date)
		{
			return null;
		} else
		{
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		}
	}
}
