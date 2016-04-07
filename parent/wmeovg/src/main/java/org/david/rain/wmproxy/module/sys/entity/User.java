package org.david.rain.wmproxy.module.sys.entity;


import org.david.rain.wmproxy.module.sys.entity.base.BaseUser;

public class User extends BaseUser {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 在session的保存的key。
	 */
	public static final String LOGIN_NAME_KEY = "_login_name_key";
	
	public User() {
		super();
		initialize();
	}

	public User(int id) {
		super(id);
		initialize();
	}

	public User(Integer id, String name, String password,
			String loginName, String note, Integer loginCount,Byte status) {
		super(id,name,password, loginName, note, loginCount, status);
		initialize();
	}
	
	@Override
	protected void initialize () {}
}
