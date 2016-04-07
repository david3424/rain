package org.david.rain.wmproxy.module.config.entity;

import org.david.rain.wmproxy.module.config.entity.base.BaseClientInfo;
import org.david.rain.wmproxy.module.sys.entity.User;

import java.util.Date;


public class ClientInfo extends BaseClientInfo {
	private static final long serialVersionUID = 1L;

	public ClientInfo() {
		super();
		initialize();
	}

	public ClientInfo(Integer id) {
		super(id);
		initialize();
	}

	public ClientInfo(Integer id, String cid, String name, String privateKey, String clientIp,
			Byte priority, String rootUrl, Date createtime, Byte status,User user) {
		super(id, cid, name, privateKey, clientIp, priority, rootUrl, createtime, status, user);
		initialize();
	}

	@Override
	protected void initialize() {
	}
}
