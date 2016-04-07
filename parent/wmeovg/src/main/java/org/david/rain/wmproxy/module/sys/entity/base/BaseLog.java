package org.david.rain.wmproxy.module.sys.entity.base;

import org.david.rain.wmproxy.module.sys.entity.User;

import java.io.Serializable;


public abstract class BaseLog  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	// fields
	private Integer id;
	private String content;
	private String createtime;
	private String ip;
	private Byte status;
	
	// many to one
	private User user;

	public BaseLog() {
		initialize ();
	}

	public BaseLog(int id) {
		this.setId(id);
		initialize ();
	}
	abstract protected void initialize ();
	
	public BaseLog(Integer id, String content, String createtime, String ip, Byte status, User user) {
		this.setId(id);
		this.setContent(content);
		this.setCreatetime(createtime);
		this.setIp(ip);
		this.setStatus(status);
		this.setUser(user);
		initialize ();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

}
