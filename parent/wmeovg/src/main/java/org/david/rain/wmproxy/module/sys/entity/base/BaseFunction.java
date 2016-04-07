package org.david.rain.wmproxy.module.sys.entity.base;

import org.david.rain.wmproxy.module.sys.entity.*;

import java.io.Serializable;
import java.util.Set;


public abstract class BaseFunction  implements Serializable, Comparable<Function> {
	private static final long serialVersionUID = 1L;
	
	//fields
	private Integer id;
	private String name;
	private String src;
	private String target;
	private Integer sort;
	private String rootPath;
	private String note;
	private Byte status;
	
	//many-to-one
	private Model model;

	//collections
	private Set<Operation> operations;
	private Set<Role> roles;
	private Set<User> users;
	
	public BaseFunction() {
		initialize();
	}

	public BaseFunction(int id) {
		this.setId(id);
		initialize();
	}

	abstract protected void initialize ();
	
	public BaseFunction(Integer id, String name, String src,
			String target,String rootPath, String note, Byte status,Model model) {
		this.id = id;
		this.name = name;
		this.src = src;
		this.target = target;
		this.rootPath = rootPath;
		this.setNote(note);
		this.status = status;
		this.model = model;
		initialize();
	}

	public int compareTo(Function fuc) {
		return this.getId() - fuc.getId();
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

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getRootPath() {
		return rootPath;
	}

	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
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

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public void setOperations(Set<Operation> operations) {
		this.operations = operations;
	}

	public Set<Operation> getOperations() {
		return operations;
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
