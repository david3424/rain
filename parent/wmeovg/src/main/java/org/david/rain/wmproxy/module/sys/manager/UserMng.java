package org.david.rain.wmproxy.module.sys.manager;

import org.david.rain.wmproxy.common.base.page.Pagination;
import org.david.rain.wmproxy.common.core.CoreManager;
import org.david.rain.wmproxy.module.sys.entity.Function;
import org.david.rain.wmproxy.module.sys.entity.User;

import java.util.Set;


public interface UserMng extends CoreManager<User> {
	/**
	 * 通过登录名查找用户，并使用缓存。
	 * 
	 * @param loginName
	 * @return 返回用户。用户不存在返回null。
	 */
	public User getByLoginName(String loginName);
	
	/**
	 * 登录。登录成功后保存至session。
	 * 
	 * @param loginName
	 * @param password
	 * @return
	 */
	public User login(String loginName, String password);
	
	/**
	 * 获取用户所有的功能
	 * @return
	 */
	public  Set<Function> getUserFunctions(String loginName);
	
	/**
	 * 分页查询
	 * @param start
	 * @param limit
	 * @param sort
	 * @param dir
	 * @return
	 */
	public Pagination findByName(String query, int start, int limit, String sort, String dir);

}