package org.david.rain.wmproxy.module.activemq.requestqueue;

import javax.jms.Queue;

import org.david.rain.wmeovg.request.entity.PrizeLog;
import org.david.rain.wmproxy.module.config.manager.WhiteListMng;
import org.david.rain.wmproxy.module.util.ExceedTotalCountException;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 
 * @ClassName RequestMessageProducer
 * @Description 兑换请求消息生产者
 * @version 1.0
 * @date 2010-8-5 下午05:58:43
 */
@Service
@Transactional
public class RequestMessageProducer {

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
	public void send(final PrizeLog prizeLog, int wid) throws HibernateException,JmsException {
		
		// 更新已发送总数，并将兑奖记录保存至数据库
		try {
			whiteListMng.accumulateSendCount(wid, prizeLog);
			
		}catch (ExceedTotalCountException e) {
			
			throw e;
		} catch (HibernateException e) {
			
			e.printStackTrace();
			throw e;
		}
		
		RequestMessageCreator messageCreator = new RequestMessageCreator(prizeLog);
		try{
			template.send(destination, messageCreator);
			
		}catch (JmsException e) {
			
			e.printStackTrace();
			throw e;
		}
		// 更新兑换记录消息ID
		//prizeLogMng.insertMessageIdByGID(prizeLog.getGid(), messageCreator.getMessageId());
	}
	
	/**
	 * 发送数目为多个的兑换请求重新发送
	 * 
	 * @throws JmsException
	 */
	public void resend(final PrizeLog prizeLog) throws HibernateException,JmsException {
	
		RequestMessageCreator messageCreator = new RequestMessageCreator(prizeLog);
		try{
			template.send(destination, messageCreator);
			
		}catch (JmsException e) {
			
			e.printStackTrace();
			throw e;
		}
	}
	@Autowired
	private WhiteListMng whiteListMng;
}
