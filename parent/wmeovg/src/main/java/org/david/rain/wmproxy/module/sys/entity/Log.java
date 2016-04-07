package org.david.rain.wmproxy.module.sys.entity;


import org.david.rain.wmproxy.module.sys.entity.base.BaseLog;

public class Log extends BaseLog {
	private static final long serialVersionUID = 1L;
	
	public Log() {
		super();
		initialize();
	}

	public Log(int id) {
		super(id);
		initialize();
	}

	public Log(Integer id, String content, String createtime, String ip, Byte status, User user) {
		super(id, content, createtime, ip, status, user);
		initialize();
	}
	
	@Override
	protected void initialize () {}
}
