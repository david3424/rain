package org.david.rain.wmproxy.module.sys.entity;


import org.david.rain.wmproxy.module.sys.entity.base.BaseModel;

public class Model extends BaseModel {
	private static final long serialVersionUID = 1L;
	
	public Model() {
		super();
		initialize();
	}

	public Model(int id) {
		super(id);
		initialize();
	}

	public Model(Integer id, String name, Integer sort,
			String loginName, String note, Byte status, Model parent) {
		super(id, name, sort, loginName, note, status, parent);
		initialize();
	}
	
	@Override
	protected void initialize () {}

}
