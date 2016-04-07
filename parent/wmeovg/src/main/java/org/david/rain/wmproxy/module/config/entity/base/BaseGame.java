package org.david.rain.wmproxy.module.config.entity.base;

import org.david.rain.wmproxy.module.config.entity.WhiteList;

import java.io.Serializable;
import java.util.Set;



public abstract class BaseGame  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//fields
	private Integer id;
	private String name;
	private String serverName;
	private Integer aid;
	
	//collections
	private Set<WhiteList> whiteLists;
	
	public BaseGame() {
		initialize();
	}

	public BaseGame(Integer id) {
		this.setId(id);
		initialize();
	}

	abstract protected void initialize ();

	public BaseGame(Integer id, String name, String serverName,
			Integer aid) {
		super();
		this.id = id;
		this.name = name;
		this.serverName = serverName;
		this.aid = aid;
	}

	@Override
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("\nid=" + id);
		sb.append("\nname=" + name);
		sb.append("\nserverName=" + serverName);
		sb.append("\naid=" + aid);
		
		return sb.toString();
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public Integer getAid() {
		return aid;
	}

	public void setAid(Integer aid) {
		this.aid = aid;
	}

	public void setWhiteLists(Set<WhiteList> whiteLists) {
		this.whiteLists = whiteLists;
	}

	public Set<WhiteList> getWhiteLists() {
		return whiteLists;
	}
}
