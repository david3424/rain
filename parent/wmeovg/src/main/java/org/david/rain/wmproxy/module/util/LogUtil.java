package org.david.rain.wmproxy.module.util;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.david.rain.wmeovg.request.util.DateUtil;
import org.david.rain.wmproxy.common.util.IpUtils;
import org.david.rain.wmproxy.module.sys.entity.Log;
import org.david.rain.wmproxy.module.sys.entity.User;
import org.david.rain.wmproxy.module.sys.manager.LogMng;
import org.david.rain.wmproxy.module.sys.manager.UserMng;
import org.slf4j.Logger;

/**
 * @ClassName LogUtil
 * @version 1.0
 * @date 2010-8-18 上午10:33:23
 */
public class LogUtil {

	public static void saveToLog(HttpServletRequest request, UserMng userMng, LogMng logMng, String operatorContent, Logger logger){
		String loginName = (String) request.getSession().getAttribute(
				User.LOGIN_NAME_KEY);
		User user = userMng.getByLoginName(loginName);
		Log log = new Log();
		log.setUser(user);
		log.setContent(operatorContent);
		log.setIp(IpUtils.gerRealIp(request));
		log.setCreatetime(DateUtil.format(new Date(), DateUtil.DATETIME));
		logMng.save(log);
		
		logger.info("操作日志：用户({})" + operatorContent, loginName);
	}
}
