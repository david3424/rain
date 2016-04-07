package org.david.rain.wmproxy.module.sys.entity;


import org.david.rain.wmproxy.module.sys.entity.base.BaseOperation;

public class Operation extends BaseOperation {
	private static final long serialVersionUID = 1L;
	
	public Operation() {
		super();
		initialize();
	}

	public Operation(int id) {
		super(id);
		initialize();
	}

	public Operation(Integer id, String url, String note, Byte status, Function function) {
		super(id, url, note, status, function);
		
		initialize();
	}
	
	@Override
	protected void initialize () {}
}
