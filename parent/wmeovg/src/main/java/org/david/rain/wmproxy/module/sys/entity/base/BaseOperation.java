package org.david.rain.wmproxy.module.sys.entity.base;

import org.david.rain.wmproxy.module.sys.entity.Function;
import org.david.rain.wmproxy.module.sys.entity.Role;
import org.david.rain.wmproxy.module.sys.entity.User;

import java.io.Serializable;
import java.util.Set;


public abstract class BaseOperation  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//fields
	private Integer id;
	private String url;
	private String note;
	private Byte status;
	
	//many-to-one
	private Function function;

	//collections
	private Set<Role> roles;
	private Set<User> users;
	
	public BaseOperation() {
		initialize();
	}

	public BaseOperation(int id) {
		this.setId(id);
		initialize();
	}

	abstract protected void initialize ();
	
	public BaseOperation(Integer id, String url, String note, Byte status, Function function) {
		this.id = id;
		this.url = url;
		this.setNote(note);
		this.status = status;
		this.function = function;
		
		initialize();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Function getFunction() {
		return function;
	}

	public void setFunction(Function function) {
		this.function = function;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Set<User> getUsers() {
		return users;
	}
}
