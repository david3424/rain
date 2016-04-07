package org.david.rain.wmproxy.module.activemq.requestqueue;

import java.util.Enumeration;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;

import org.david.rain.wmproxy.module.service.manager.PrizeLogMng;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.BrowserCallback;
import org.springframework.jms.core.JmsTemplate;


/**
 * @ClassName RequestMessageBrowser
 * @Description 请求消息队列查看器
 * @version 1.0
 * @date 2010-8-11 上午11:17:30
 */
public class RequestMessageBrowser {

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
	 * @Description 按客户端标识查询当前队列的兑换记录数目
	 * @return
	 */
	public int getCount(String cid) {
		
		return count("cid='" + cid + "'");
	}

	/**
	 * 
	 * @Description 按客户端标识和物品ID查询当前队列的兑换记录数目
	 * @return
	 */
	public int getCount(String cid, String appid, Integer prizeid) {
		
		return count("cid='" + cid + "' and appid='" + appid + "' and prizeid=" + prizeid);
	}
	
	public boolean isInQueue(String gid){
		
		String searchStr = "gid='" + gid + "'";
		
		return count(searchStr) > 0;
	}
	
	@SuppressWarnings("unchecked")
	public int count(String searchStr){
		
		count = 0;
		try{
			
			template.browseSelected(destination, searchStr, new BrowserCallback() {
	
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
	
	private Integer count;
	
	@Autowired
    PrizeLogMng prizeLogMng;
}
