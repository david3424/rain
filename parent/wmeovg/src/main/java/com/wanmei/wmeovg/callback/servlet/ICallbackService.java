// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ICallbackService.java

package com.david.web.wanmei.wmeovg.callback.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

public interface ICallbackService
{

	public abstract void receive(String s, String s1, String s2, int i, int j, String s3)
		throws Exception;

	public abstract void init(ServletConfig servletconfig)
		throws ServletException;
}
