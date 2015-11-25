package com.david.web.pppppp.service.webservice.textmessage.server.webservice;

/**
 * 
 * @author lihongyu
 * 2010-08-23
 *
 */
public interface WSTextMessageService {
	
	/**
	 * 发送文本信息方法，调用前请先调用WSTextMessageBean.validate()进行验证
	 * @param textMessageBean
	 * @return 100操作不成功，200操作成功，150参数验证失败，-100服务器端内部错误
	 */
	Integer send(WSTextMessageBean textMessageBean);
	
	/**
	 * 发送短信，只要用户的通行帐号绑定了手机号码就可以发送短信，不管用户服务是否过期均能发送，调用前请先调用WSTextMessageBean.validate()进行验证
	 * @param textMessageBean
	 * @return 100操作不成功，200操作成功，150参数验证失败，-100服务器端内部错误
	 */
	Integer sendDirectly(WSTextMessageBean textMessageBean);
	
	/**
	 * 发送短信息至指定的号码，此方法一般用于发送“直送”短信息，调用前请先调用WSTextMessageBean.validate()进行验证
	 * @param textMessageBean bean中的passports list请直接填入目标手机号码
	 * @return 100操作不成功，200操作成功，150参数验证失败，-100服务器端内部错误
	 */
	Integer sendSimple(WSTextMessageBean textMessageBean);
}
