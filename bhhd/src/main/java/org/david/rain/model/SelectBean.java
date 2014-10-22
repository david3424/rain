package org.david.rain.model;

import java.io.Serializable;

public class SelectBean implements Serializable  {

	private static final long serialVersionUID = 2166129397398408435L;

	private Integer id;
	private String code;
	private String name;

	public SelectBean() {
	};

	public SelectBean(Integer id, String name) {
		setId(id);
		setName(name);
	}

	public SelectBean(String code, String name) {
		setCode(code);
		setName(name);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
