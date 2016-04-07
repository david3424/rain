package org.david.rain.wmproxy.module.util;

import javax.jms.ExceptionListener;
import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @ClassName SpringJmsExceptionListener
 * @Description SpringJmsExceptionListener
 */
public class SpringJmsExceptionListener implements ExceptionListener {
	protected Logger log = LoggerFactory.getLogger(getClass());
	
	@Override
	public void onException(JMSException ex) {
		
		log.info("===SpringJmsExceptionListener===正常抛出");
	}

}
