package org.david.rain.wmproxy.module.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @ClassName FailToSendPrizeException
 * @date 2010-8-5 下午06:00:19
 */
public class FailToSendPrizeException extends Exception {

	protected Logger log = LoggerFactory.getLogger(getClass());
	
	public FailToSendPrizeException() {
		//log.error("FailToSendPrizeException");
	}

	private static final long serialVersionUID = 1L;
}
