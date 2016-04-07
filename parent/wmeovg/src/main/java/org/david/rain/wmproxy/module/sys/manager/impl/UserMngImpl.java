package org.david.rain.wmproxy.module.sys.manager.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.david.rain.wmproxy.common.base.page.Pagination;
import org.david.rain.wmproxy.common.base.struts2.ContextPvd;
import org.david.rain.wmproxy.common.core.CoreManagerImpl;
import org.david.rain.wmproxy.module.sys.dao.UserDao;
import org.david.rain.wmproxy.module.sys.entity.Function;
import org.david.rain.wmproxy.module.sys.entity.Role;
import org.david.rain.wmproxy.module.sys.entity.User;
import org.david.rain.wmproxy.module.sys.manager.UserMng;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class UserMngImpl extends CoreManagerImpl<User> implements UserMng {
	
	public User login(String loginName, String password) {
		User user = getByLoginName(loginName);
		contextPvd.setSessionAttr(User.LOGIN_NAME_KEY, user);
		return user;
	}
	
	@Transactional(readOnly = true)
	public User getByLoginName(String loginName) {
		return getDao().getUserByLoginName(loginName);
	}

	@Autowired
	private ContextPvd contextPvd;

	@Autowired
	public void setDao(UserDao dao) {
		super.setDao(dao);
	}

	public UserDao getDao() {
		return (UserDao) super.getDao();
	}
	
	@Transactional(readOnly = true)
	public  Set<Function> getUserFunctions(String loginName){
		
		User user = getByLoginName(loginName);
		
		// 用户所有功能
		Set<Function> functions = new HashSet<Function>();
		// 获取用户所属角色下的所有功能
		Set<Role> roles = user.getRoles();
		Iterator<Role> itRole = roles.iterator();
		while (itRole.hasNext()) {
			Set<Function> roleFunctions = itRole.next().getFunctions();
			functions.addAll(roleFunctions);
		}
		// 用户拥有的其他功能
		Set<Function> userFunctions = user.getFunctions();
		functions.addAll(userFunctions);
		
		return functions;
	}
	@Transactional(readOnly = true)
	public Pagination findByName(String query, int start, int limit, String sort, String dir){
		return getDao().findByName(query, start, limit, sort, dir);
	}
}
