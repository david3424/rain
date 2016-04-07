package org.david.rain.wmproxy.module.activemq.callbackqueue;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.david.rain.wmeovg.request.entity.PrizeLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.MessageCreator;




/**
 * 
 * @ClassName CallbackMessageCreator
 * @Description 客户端回调消息处理辅助类
 * @version 1.0
 * @date 2010-8-5 下午05:57:31
 */
public class CallbackMessageCreator implements MessageCreator{

	protected Logger log = LoggerFactory.getLogger(getClass());
	
	private ObjectMessage msg;
	private PrizeLog prizeLog;
	
	public CallbackMessageCreator(PrizeLog prizeLog) {
		super();
		this.prizeLog = prizeLog;
	}

	@Override
	public Message createMessage(Session session){
		try{
			ObjectMessage msg = session.createObjectMessage();
			msg.setJMSPriority(prizeLog.getPriority());
			msg.setStringProperty("type", "callback");
			msg.setStringProperty("gid", prizeLog.getGid());
			msg.setStringProperty("cid", prizeLog.getCid());
			msg.setStringProperty("appid", prizeLog.getAppid());
			msg.setIntProperty("prizeid", prizeLog.getPrizeid());
			msg.setObject(prizeLog);	
			this.setMsg(msg);
		}catch (Exception e) {
			e.printStackTrace();
			log.error("流水号[{}]：CallBackQueue消息创建失败", prizeLog.getGid());
		}
		return this.getMsg();
	}


	public String getMessageId(){
		
		String messageId = null;
		try {
			messageId = msg.getJMSMessageID();
		} catch (JMSException e) {
			e.printStackTrace();
			log.error("流水号[{}]：CallBackQueue获取消息ID失败", prizeLog.getGid());
		}
		
		return messageId;
	}
	
	public ObjectMessage getMsg() {
		return msg;
	}


	public void setMsg(ObjectMessage msg) {
		this.msg = msg;
	}


	public PrizeLog getPrizeLog() {
		return prizeLog;
	}


	public void setPrizeLog(PrizeLog prizeLog) {
		this.prizeLog = prizeLog;
	}
}
