package org.david.rain.wmproxy.module.config.entity.base;

import org.david.rain.wmproxy.module.config.entity.WhiteList;
import org.david.rain.wmproxy.module.sys.entity.User;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;


public abstract class BaseClientInfo  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//fields
	private Integer id;
	private String cid;
	private String name;
	private String privateKey;
	private String clientIp;
	private Byte priority;
	private String rootUrl;
	private Date createtime;
	private Byte status;
	
	// many to one
	private User user;
	
	//collections
	private Set<WhiteList> whiteLists;
	
	public BaseClientInfo() {
		initialize();
	}

	public BaseClientInfo(Integer id) {
		this.setId(id);
		initialize();
	}

	abstract protected void initialize ();

	public BaseClientInfo(Integer id, String cid, String name, String privateKey, String clientIp,
			Byte priority, String rootUrl, Date createtime, Byte status,User user) {
		super();
		this.id = id;
		this.cid = cid;
		this.name = name;
		this.privateKey = privateKey;
		this.clientIp = clientIp;
		this.priority = priority;
		this.rootUrl = rootUrl;
		this.createtime = createtime;
		this.status = status;
		this.user = user;
	}
	@Override
	public String toString(){
		StringBuffer sb = new StringBuffer();
		
		sb.append("\nid=" + id);
		//sb.append("\ncid=" + cid);
		sb.append("\nname=" + name);
		//sb.append("\nprivateKey=" + privateKey);
		sb.append("\npriority=" + priority);
		sb.append("\nclientIp=" + clientIp);
		sb.append("\nrootUrl=" + rootUrl);
		
		return sb.toString();
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getClientIp() {
		return clientIp;
	}

	public Byte getPriority() {
		return priority;
	}

	public void setPriority(Byte priority) {
		this.priority = priority;
	}

	public String getRootUrl() {
		return rootUrl;
	}

	public void setRootUrl(String rootUrl) {
		this.rootUrl = rootUrl;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setWhiteLists(Set<WhiteList> whiteLists) {
		this.whiteLists = whiteLists;
	}

	public Set<WhiteList> getWhiteLists() {
		return whiteLists;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}
}
