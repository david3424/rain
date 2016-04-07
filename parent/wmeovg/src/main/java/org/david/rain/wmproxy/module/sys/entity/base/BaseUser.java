package org.david.rain.wmproxy.module.sys.entity.base;

import java.io.Serializable;
import java.util.Set;

import org.david.rain.wmproxy.module.config.entity.ClientInfo;
import org.david.rain.wmproxy.module.config.entity.WhiteList;
import org.david.rain.wmproxy.module.sys.entity.Function;
import org.david.rain.wmproxy.module.sys.entity.Log;
import org.david.rain.wmproxy.module.sys.entity.Operation;
import org.david.rain.wmproxy.module.sys.entity.Role;

public abstract class BaseUser  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//fields
	private Integer id;
	private String name;
	private String password;
	private String loginName;
	private String note;
	private Integer loginCount;
	private Byte status;
	
	//collections
	private Set<Log> logs;
	private Set<ClientInfo> clientInfos;
	private Set<WhiteList> whiteLists;
	private Set<Role> roles;
	private Set<Function> functions;
	private Set<Operation> operations;
	
	public BaseUser() {
		initialize();
	}

	public BaseUser(int id) {
		this.setId(id);
		initialize();
	}

	abstract protected void initialize ();
	
	public BaseUser(Integer id, String name, String password,
			String loginName, String note, Integer loginCount, Byte status) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.loginName = loginName;
		this.setNote(note);
		this.setLoginCount(loginCount);
		this.status = status;
		initialize();
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getNote() {
		return note;
	}

	public void setLogs(Set<Log> logs) {
		this.logs = logs;
	}

	public Set<Log> getLogs() {
		return logs;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setFunctions(Set<Function> functions) {
		this.functions = functions;
	}

	public Set<Function> getFunctions() {
		return functions;
	}

	public void setOperations(Set<Operation> operations) {
		this.operations = operations;
	}

	public Set<Operation> getOperations() {
		return operations;
	}
	public void setLoginCount(Integer loginCount) {
		this.loginCount = loginCount;
	}

	public Integer getLoginCount() {
		return loginCount;
	}

	public void setClientInfos(Set<ClientInfo> clientInfos) {
		this.clientInfos = clientInfos;
	}

	public Set<ClientInfo> getClientInfos() {
		return clientInfos;
	}

	public void setWhiteLists(Set<WhiteList> whiteLists) {
		this.whiteLists = whiteLists;
	}

	public Set<WhiteList> getWhiteLists() {
		return whiteLists;
	}	
}
