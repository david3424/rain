// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CallbackServlet.java

package com.david.web.pppppp.wmeovg.callback.servlet;

import com.david.web.pppppp.wmeovg.request.util.SignatureUtil;
import com.david.web.pppppp.wmeovg.request.util.WmeovgProperties;
import sun.misc.BASE64Decoder;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.NoSuchAlgorithmException;

// Referenced classes of package com.david.web.pppppp.wmeovg.callback.servlet:
//			ICallbackService

public class CallbackServlet extends HttpServlet
{

	private static final long serialVersionUID = 1L;
	public static ICallbackService callbackService;

	public CallbackServlet()
	{
	}

	public void init(ServletConfig config)
		throws ServletException
	{
		String callback = config.getInitParameter("callback");
		Object callbackObject = null;
		if (null == callbackService)
		{
			try
			{
				Class callbackClass = Class.forName(callback);
				callbackObject = callbackClass.newInstance();
			}
			catch (Exception e)
			{
				System.err.println((new StringBuilder()).append("虚拟物品兑换系统回调接口实例化失败：类").append(callback).append("不存在或不能被实例化").toString());
				throw new ServletException((new StringBuilder()).append("虚拟物品兑换系统回调接口实例化失败：类").append(callback).append("不存在或不能被实例化").toString());
			}
			if (!(callbackObject instanceof ICallbackService))
			{
				System.err.println("虚拟物品兑换回调接口实例化失败：没有继承com.pppppp.wmeovg.callback.servlet.ICallbackService接口");
				throw new ServletException("虚拟物品兑换回调接口没有继承com.pppppp.wmeovg.callback.servlet.ICallbackService接口");
			}
			callbackService = (ICallbackService)callbackObject;
			callbackService.init(config);
		}
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	{
		String gid = req.getParameter("gid");
		String cid = req.getParameter("cid");
		String appid = req.getParameter("appid");
		int status = -1;
		try
		{
			status = Integer.parseInt(req.getParameter("status"));
		}
		catch (Exception e)
		{
			resp.setStatus(1001);
			return;
		}
		int count = Integer.parseInt(req.getParameter("count"));
		String callback;
		try
		{
			callback = new String((new BASE64Decoder()).decodeBuffer(req.getParameter("callback")), "UTF-8");
		}
		catch (Exception e1)
		{
			System.err.println("虚拟物品兑换回调接口：回调参数解码错误");
			resp.setStatus(1002);
			return;
		}
		String sign = req.getParameter("sign");
		String privateKey = null;
		if (cid.equals("07"))
			privateKey = WmeovgProperties.getPrivateKey().trim();
		else
			privateKey = WmeovgProperties.getPrivateKey().trim();
		try
		{
			if (null != sign && !sign.equals(SignatureUtil.sign((new StringBuilder()).append(gid.trim()).append(status).append(callback.trim()).append(privateKey).toString())))
			{
				System.err.println("虚拟物品兑换回调接口：客户端回调参数不合法");
				resp.setStatus(1001);
				return;
			}
		}
		catch (NoSuchAlgorithmException e)
		{
			System.err.println("虚拟物品兑换回调接口：数据签名失败");
			resp.setStatus(1003);
			return;
		}
		try
		{
			callbackService.receive(gid, cid, appid, status, count, callback);
		}
		catch (Exception e)
		{
			System.err.println("虚拟物品兑换回调接口：数据接收处理异常");
			e.printStackTrace();
			resp.setStatus(1004);
			return;
		}
	}
}
