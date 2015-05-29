package com.david.web.wanmei.service.webservice.textmessage.server.webservice;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author lihongyu
 * 2010-08-23
 */
public final class WSTextMessageBean implements Serializable {

	private static final long serialVersionUID = -2504363503767367700L;

	/**
	 * 验证WSTextMessageBean实例是否有效
	 * @param textMessageBean
	 * @return
	 */
	public static boolean validate(WSTextMessageBean textMessageBean) {
		if (textMessageBean == null) return false;
		if (checkStringFailed(textMessageBean.getClientId(), 64)) return false;
		if (checkStringFailed(textMessageBean.getMessageContent(), 255)) return false;
		if (textMessageBean.getServiceId() == null || textMessageBean.getServiceId() < 1) return false;
		if (checkStringFailed(textMessageBean.getMessageType(), 32)) return false;
		if (textMessageBean.getPriority() < 1 || textMessageBean.getPriority() > 5) return false;
		if (!checkAccounts(textMessageBean.getPassports())) return false;
		return true;
	}
	
	private String clientId; // 客户端ID，不能为空，长度不能大于64
	private List<String> passports; // 完美通行证帐号，List不能为null，size()不能小于1且不能大于255
	private String messageContent;  // 短信息内容， 不能为空，长度不能大于255的字符
	private Integer serviceId; // 短信息服务ID，不能为null，且大于0
	private Integer priority;  // 优先级1 - 5，1 最低，5 最高，默认为1
	private String messageType; // 短信息类型，不能为空，长度不能大于32
	
	public WSTextMessageBean() {
		super();
		priority = 1;
	}
	
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getMessageContent() {
		return messageContent;
	}
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public List<String> getPassports() {
		return passports;
	}
	public void setPassports(List<String> passports) {
		this.passports = passports;
	}

	public Integer getServiceId() {
		return serviceId;
	}
	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}

	@Override
	public String toString() {
		return new StringBuilder(127)
			.append("WSTextMessageBean detail --> ").append("client id = '").append(clientId)
			.append("', passport amount = [").append(passports.size())
			.append("], message content = '").append(messageContent)
			.append("', service id = [").append(serviceId)
			.append("], message type = '").append(messageType)
			.append("', priority = ").append(priority)
			.toString();
	}
	private static boolean checkStringFailed(String thisString, int maxLength) {
		return thisString == null || thisString.trim().length() == 0 || thisString.length() > maxLength;
	}
	private static boolean checkAccounts(List<String> accounts) {
		final int maxAccountNumber = 255;
		if (accounts == null || accounts.isEmpty() || accounts.size() > maxAccountNumber) {
			return false;
		}
		final int accountMaxLength = 16;
		for (String account : accounts) {
			if (checkStringFailed(account, accountMaxLength))
				return false;
		}
		return true;
	}
}
