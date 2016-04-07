package org.david.rain.wmproxy.module.sys.entity.base;

import org.david.rain.wmproxy.module.sys.entity.Function;
import org.david.rain.wmproxy.module.sys.entity.Operation;
import org.david.rain.wmproxy.module.sys.entity.User;

import java.io.Serializable;
import java.util.Set;


public abstract class BaseRole  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String name;
	private String note;
	private Byte status;

	// collections
	private Set<User> users;
	private Set<Function> functions;
	private Set<Operation> operations;
	
	public BaseRole() {
		initialize ();
	}

	public BaseRole(int id) {
		this.setId(id);
		initialize ();
	}
	
	abstract protected void initialize ();

	public BaseRole(Integer id, String name, String note, Byte status) {
		this.id = id;
		this.name = name;
		this.setNote(note);
		this.status = status;
		initialize ();
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

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Set<User> getUsers() {
		return users;
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
	
}
