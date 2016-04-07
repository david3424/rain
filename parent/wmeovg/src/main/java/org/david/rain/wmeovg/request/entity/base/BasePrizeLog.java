package org.david.rain.wmeovg.request.entity.base;

import org.david.rain.wmeovg.request.util.DateUtil;

import java.io.Serializable;
import java.util.Date;


public abstract class BasePrizeLog  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//fields
	private String gid;
	private String cid;
	private String appid;
	private Integer zoneid;
	private String account;
	private String rolename;
	private Long roleid;
	private Integer prizeid;
	private Integer count;
	private Byte priority;
	private String callback;
	private Byte sendStatus;
	private Integer processCount;
	private Byte callbackStatus;
	private Date requestTime;
	private Date sendTime;
	private String messageId;
	private Byte prizeResendCount;
	private Date callbackTime;
	private Integer callbackHttpStatus;
	private Byte callbackCount;
	private String ip;
	
	private Byte prizeSendType;
	
	private String title;
	private String content;
	
	public BasePrizeLog() {
		initialize();
	}

	public BasePrizeLog(String gid) {
		this.setGid(gid);
		initialize();
	}

	abstract protected void initialize ();

	public BasePrizeLog(String gid, String cid, String appid, Integer zoneid,
			String account, String rolename, Long roleid, Integer prizeid, Integer count,
			Byte priority, String callback, Byte sendStatus, Integer processCount,
			Byte callbackStatus, Date requestTime, Date sendTime, String messageId, Byte prizeResendCount,
			Date callbackTime, Integer callbackHttpStatus, Byte callbackCount, String ip, Byte prizeSendType) {
		super();
		this.gid = gid;
		this.cid = cid;
		this.appid = appid;
		this.zoneid = zoneid;
		this.account = account;
		this.rolename = rolename;
		this.roleid = roleid;
		this.prizeid = prizeid;
		this.count = count;
		this.priority = priority;
		this.callback = callback;
		this.sendStatus = sendStatus;
		this.processCount = processCount;
		this.callbackStatus = callbackStatus;
		this.requestTime = requestTime;
		this.sendTime = sendTime;
		this.messageId = messageId;
		this.prizeResendCount = prizeResendCount;
		this.callbackTime = callbackTime;
		this.callbackHttpStatus = callbackHttpStatus;
		this.callbackCount = callbackCount;
		this.ip = ip;
		this.prizeSendType = prizeSendType;
	}

	@Override
	public String toString() {
		
		String prize = " GID: " + this.gid;
		prize += " CID: " + this.cid;
		prize += " appid: " + this.appid;
		prize += " account: " + this.account;
		prize += " rolename: " + this.rolename;
		prize += " roleid: " + this.roleid;
		prize += " zoneid: " + this.zoneid;
		prize += " prizeid: " + this.prizeid;
		prize += " priority: " + this.priority;
		prize += " callback: " + this.callback;
		prize += " sendStatus: " + this.sendStatus;
		prize += " processCount: " + this.processCount;
		prize += " callbackStatus: " + this.callbackStatus;
		prize += " requestTime: " + DateUtil.format(this.requestTime, DateUtil.DATETIME);
		prize += " sendtime: " + DateUtil.format(this.sendTime, DateUtil.DATETIME);
		prize += " messageId：" + this.messageId;
		prize += " prizeResendCount："  + this.prizeResendCount;
		prize += " callbackTime: " + DateUtil.format(this.callbackTime, DateUtil.DATETIME);
		prize += " callbackHttpStatus：" + this.callbackHttpStatus;
		prize += " callbackCount："  + this.callbackCount;
		prize += " ip: " + this.ip;
		prize += " prizeSendType: " + this.prizeSendType;
		
		return prize;
	}
	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public Integer getZoneid() {
		return zoneid;
	}

	public void setZoneid(Integer zoneid) {
		this.zoneid = zoneid;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public Long getRoleid() {
		return roleid;
	}

	public void setRoleid(Long roleid) {
		this.roleid = roleid;
	}

	public Integer getPrizeid() {
		return prizeid;
	}

	public void setPrizeid(Integer prizeid) {
		this.prizeid = prizeid;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Byte getPriority() {
		return priority;
	}

	public void setPriority(Byte priority) {
		this.priority = priority;
	}

	public String getCallback() {
		return callback;
	}

	public void setCallback(String callback) {
		this.callback = callback;
	}

	public Byte getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(Byte sendStatus) {
		this.sendStatus = sendStatus;
	}

	public Byte getCallbackStatus() {
		return callbackStatus;
	}

	public void setCallbackStatus(Byte callbackStatus) {
		this.callbackStatus = callbackStatus;
	}

	public Date getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public Byte getPrizeResendCount() {
		return prizeResendCount;
	}

	public void setPrizeResendCount(Byte prizeResendCount) {
		this.prizeResendCount = prizeResendCount;
	}

	public Date getCallbackTime() {
		return callbackTime;
	}

	public void setCallbackTime(Date callbackTime) {
		this.callbackTime = callbackTime;
	}

	public Integer getCallbackHttpStatus() {
		return callbackHttpStatus;
	}

	public void setCallbackHttpStatus(Integer callbackHttpStatus) {
		this.callbackHttpStatus = callbackHttpStatus;
	}

	public Byte getCallbackCount() {
		return callbackCount;
	}

	public void setCallbackCount(Byte callbackCount) {
		this.callbackCount = callbackCount;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Byte getPrizeSendType() {
		return prizeSendType;
	}

	public void setPrizeSendType(Byte prizeSendType) {
		this.prizeSendType = prizeSendType;
	}

	public void setProcessCount(Integer processCount) {
		this.processCount = processCount;
	}

	public Integer getProcessCount() {
		return processCount;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getAppid() {
		return appid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	
}
