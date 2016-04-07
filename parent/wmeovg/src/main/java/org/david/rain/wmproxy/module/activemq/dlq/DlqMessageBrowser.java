package org.david.rain.wmproxy.module.activemq.dlq;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.BrowserCallback;
import org.springframework.jms.core.JmsTemplate;

import org.david.rain.wmproxy.module.activemq.callbackqueue.CallbackMessageCreator;
import org.david.rain.wmproxy.module.activemq.callbackqueue.CallbackMessageProducer;
import org.david.rain.wmproxy.module.activemq.requestqueue.RequestMessageCreator;
import org.david.rain.wmproxy.module.activemq.requestqueue.RequestMessageProducer;
import org.david.rain.wmeovg.request.entity.PrizeLog;

/**
 * @ClassName RequestMessageBrowser
 * @Description 系统异常消息队列查看器
 * @version 1.0
 * @date 2010-8-11 上午11:17:30
 */
public class DlqMessageBrowser {

	protected Logger log = LoggerFactory.getLogger(getClass());
	
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
	 * 
	 * @Description 查询当前队列所有兑换记录数目
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getCount() {
		count = 0;
		try{
			template.browse(destination, new BrowserCallback() {
	
				@Override
				public Object doInJms(Session session, QueueBrowser queueBrowser)
						throws JMSException {
					
					Enumeration messages = queueBrowser.getEnumeration();
					
					while(messages.hasMoreElements()){
						count++;
						messages.nextElement();
					}
					
					return null;
				}
				
			});
		}catch(JmsException ex){
			
			log.error("ActiveMq未响应，请重启服务器。", ex);
			return -1;
		}
		return count;
	}

	/**
	 * 
	 * @Description 查询发送失败的兑换记录
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PrizeLog> getSendFailedPrizeLogList(String gid) {
		
		final List<PrizeLog> list = new ArrayList<PrizeLog>();
		
		String messageSelector = "type='request'";
		if(gid != null && gid.trim().length() > 0){
			messageSelector += " and gid='" + gid.trim() + "'";
		}
		
		try{
			template.browseSelected(destination, messageSelector, new BrowserCallback() {
	
				@Override
				public Object doInJms(Session session, QueueBrowser queueBrowser)
						throws JMSException {
					
					Enumeration messages = queueBrowser.getEnumeration();
					
					while(messages.hasMoreElements()){
						list.add((PrizeLog)((ObjectMessage) messages.nextElement()).getObject());
					}
					
					return null;
				}
				
			});
		}catch(JmsException ex){
			
			log.error("ActiveMq未响应，请重启服务器。", ex);
		}
		return list;
	}
	
	/**
	 * 
	 * @Description 查询回调失败的兑换记录
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PrizeLog> getCallbackFailedPrizeLogList(String gid) {
		
		final List<PrizeLog> list = new ArrayList<PrizeLog>();
		String messageSelector = "type='callback'";
		if(gid != null && gid.trim().length() > 0){
			messageSelector += " and gid='" + gid.trim() + "'";
		}
		
		try{
			template.browseSelected(destination,messageSelector, new BrowserCallback() {
	
				@Override
				public Object doInJms(Session session, QueueBrowser queueBrowser)
						throws JMSException {
					
					Enumeration messages = queueBrowser.getEnumeration();
					
					while(messages.hasMoreElements()){
						list.add((PrizeLog)((ObjectMessage) messages.nextElement()).getObject());
					}
					
					return null;
				}
				
			});
		}catch(JmsException ex){
			
			log.error("ActiveMq未响应，请重启服务器。", ex);
		}
		return list;
	}
	
	/**
	 * 重新发送消息
	 * 
	 * @throws JmsException
	 * @throws JMSException 
	 * @throws JMSException 
	 */
	public void resend(String gid) throws JmsException, JMSException{

		ObjectMessage message = (ObjectMessage)template.receiveSelected(destination, "gid='" + gid + "'");
		try{
			String type = message.getStringProperty("type");
			if(type.equals("request")){
				
				RequestMessageCreator messageCreator = new RequestMessageCreator((PrizeLog) message.getObject());
				requestMessageProducer.getTemplate().send(requestMessageProducer.getDestination(),messageCreator);
				
			}else if(type.equals("callback")){
				
				CallbackMessageCreator messageCreator = new CallbackMessageCreator((PrizeLog) message.getObject());
				callbackMessageProducer.getTemplate().send(callbackMessageProducer.getDestination(), messageCreator);
			}else{
				throw new JMSException("=======异常消息========");
			}
		}catch (JmsException e) {
			
			e.printStackTrace();
			throw e;
		}catch (JMSException e) {
			
			e.printStackTrace();
			throw e;
		}
	}
	
	private Integer count;
	
	@Autowired
	private RequestMessageProducer requestMessageProducer;
	
	@Autowired
	private CallbackMessageProducer callbackMessageProducer;
}
