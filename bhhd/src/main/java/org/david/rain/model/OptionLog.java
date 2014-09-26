package org.david.rain.model;

import java.io.Serializable;
import java.util.Date;

public class OptionLog implements Serializable  {

	private static final long serialVersionUID = -1216173007745297301L;
	private Integer logId;
	private String account;
	private String loginIp;
	private Date optionTime;
	private String optionTimeStr;
	private String message;

	public Integer getLogId() {
		return logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public Date getOptionTime() {
		return optionTime;
	}

	public void setOptionTime(Date optionTime) {
		this.optionTime = optionTime;
	}

	public String getOptionTimeStr() {
		return optionTimeStr;
	}

	public void setOptionTimeStr(String optionTimeStr) {
		this.optionTimeStr = optionTimeStr;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
