package org.david.rain.wmproxy.module.sys.entity;


import org.david.rain.wmproxy.module.sys.entity.base.BaseRole;

public class Role extends BaseRole {
	private static final long serialVersionUID = 1L;
	
	public Role() {
		super();
		initialize();
	}

	public Role(int id) {
		super(id);
		initialize();
	}

	public Role(Integer id, String name, String note, Byte status) {
		super(id,name, note, status);
		initialize();
	}
	@Override
	protected void initialize () {}
}
