package org.david.rain.wmproxy.module.util;

import javax.servlet.http.HttpServletRequest;

import org.david.rain.wmproxy.common.hessian.HessianContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HessianUtils {

	protected static Logger log = LoggerFactory.getLogger(HessianUtils.class);
	
	public static String getClientIp(){
		
		String serverIp = null;
		try{
			HttpServletRequest request = (HttpServletRequest) HessianContext.getRequest();
			//serverIp = IpUtils.gerRealIp(request);
			serverIp = request.getRemoteAddr();
			
		}catch (Exception e) {
			log.error("获取客户端IP失败:" + e.getMessage());
		}
		
		return serverIp;
	}
}
