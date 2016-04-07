package org.david.rain.wmproxy.module.sys.entity;


import org.david.rain.wmproxy.module.sys.entity.base.BaseFunction;

public class Function extends BaseFunction {
	private static final long serialVersionUID = 1L;
	
	public Function() {
		super();
		initialize();
	}

	public Function(int id) {
		super(id);
		initialize();
	}

	public Function(Integer id, String name, String src,
			String target,String rootPath, String note, Byte status,Model model) {
		super(id, name, src, target, rootPath, note, status, model);
		initialize();
	}
	
	@Override
	protected void initialize () {}
}
