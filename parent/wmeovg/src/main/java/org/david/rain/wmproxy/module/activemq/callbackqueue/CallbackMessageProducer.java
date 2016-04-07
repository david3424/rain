package org.david.rain.wmproxy.module.activemq.callbackqueue;

import javax.jms.Queue;

import org.david.rain.wmeovg.request.entity.PrizeLog;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;

/**
 * 
 * @ClassName CallbackMessageProducer
 * @Description 客户端回调消息生产者
 * @version 1.0
 * @date 2010-8-5 下午05:58:18
 */
public class CallbackMessageProducer {

	private JmsTemplate template;

	private Queue destination;

	public JmsTemplate getTemplate() {
		return template;
	}

	public void setTemplate(JmsTemplate template) {
		this.template = template;
	}

	public Queue getDestination() {
		return destination;
	}

	public void setDestination(Queue destination) {
		this.destination = destination;
	}

	/**
	 * 兑换请求进入消息队列
	 * 
	 * @throws JmsException
	 */
	public void send(final PrizeLog prizeLog) throws JmsException {

		CallbackMessageCreator messageCreator = new CallbackMessageCreator(prizeLog);
		try{
			template.send(destination, messageCreator);
		}catch (JmsException e) {
			
			e.printStackTrace();
			throw e;
		}
	}
}
