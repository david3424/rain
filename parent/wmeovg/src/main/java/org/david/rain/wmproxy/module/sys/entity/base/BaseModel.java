package org.david.rain.wmproxy.module.sys.entity.base;

import org.david.rain.wmproxy.module.sys.entity.Function;
import org.david.rain.wmproxy.module.sys.entity.Model;

import java.io.Serializable;
import java.util.Set;


public abstract class BaseModel  implements Serializable, Comparable<Model>{
	private static final long serialVersionUID = 1L;
	
	//fields
	private Integer id;
	private String name;
	private Integer sort;
	private String rootPath;
	private String note;
	private Byte status;
	
	//many-to-one
	private Model parent;
	
	//collections
	private Set<Model> child;
	private Set<Function> functions;

	public BaseModel() {
		initialize();
	}

	public BaseModel(int id) {
		this.setId(id);
		initialize();
	}

	abstract protected void initialize ();
	
	public BaseModel(Integer id, String name, Integer sort,
			String loginName, String note, Byte status, Model parent) {
		this.id = id;
		this.name = name;
		this.setSort(sort);
		this.setNote(note);
		this.status = status;
		this.setParent(parent);
		
		initialize();
	}
	public int compareTo(Model model) {
		return this.getId() - model.getId();
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

	public Set<Model> getChild() {
		return child;
	}

	public void setChild(Set<Model> child) {
		this.child = child;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getSort() {
		return sort;
	}

	public void setParent(Model parent) {
		this.parent = parent;
	}

	public Model getParent() {
		return parent;
	}

	public void setFunctions(Set<Function> functions) {
		this.functions = functions;
	}

	public Set<Function> getFunctions() {
		return functions;
	}

	
}
