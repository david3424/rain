package org.david.rain.wmproxy.module.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @ClassName FailToSendPrizeException
 * @Description 客户端接口回调失败异常
 * @version 1.0
 * @date 2010-8-5 下午06:00:19
 */
public class FailToCallbackException extends Exception {

	protected Logger log = LoggerFactory.getLogger(getClass());
	
	public FailToCallbackException() {
		//log.error("FailToCallbackException");
	}

	private static final long serialVersionUID = 1L;
}
