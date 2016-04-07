package org.david.rain.wmproxy.module.config.entity;

import org.david.rain.wmproxy.module.config.entity.base.BaseWhiteList;
import org.david.rain.wmproxy.module.sys.entity.User;

import java.util.Date;


public class WhiteList extends BaseWhiteList {
	private static final long serialVersionUID = 1L;

	public WhiteList() {
		super();
		initialize();
	}

	public WhiteList(Integer id) {
		super(id);
		initialize();
	}

	public WhiteList(Integer id, String appid, Integer prizeid, String prizename, String title,String text,
			Integer count,Integer sendCount, Integer failCount, Date createtime, Byte status, ClientInfo clientInfo, Game game, User user) {
		super(id, appid, prizeid, prizename,title,text, count, sendCount, failCount, createtime, status, clientInfo, game,user);
		initialize();
	}

	@Override
	protected void initialize() {
	}
}
