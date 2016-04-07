package org.david.rain.wmproxy.module.sys.manager;


import org.david.rain.wmproxy.common.base.page.Pagination;
import org.david.rain.wmproxy.common.core.CoreManager;
import org.david.rain.wmproxy.module.sys.entity.Role;

public interface RoleMng extends CoreManager<Role> {
	/**
	 * 分页查询
	 * @param name
	 * @param start
	 * @param limit
	 * @param sort
	 * @param dir
	 * @return
	 */
	public Pagination findByName(String query, int start, int limit, String sort, String dir);
	
	/**
	 * 查找角色下的所有用户
	 * @param roleid
	 * @param isBelong
	 * @param query
	 * @param start
	 * @param limit
	 * @param sort
	 * @param dir
	 * @return
	 */
	public Pagination findUsers(int roleid, boolean isBelong, String query, int start, int limit,
                                String sort, String dir);
	
	/**
	 * 查找角色下的所有功能
	 * @param roleid
	 * @param isBelong
	 * @param query
	 * @param start
	 * @param limit
	 * @param sort
	 * @param dir
	 * @return
	 */
	public Pagination findFucs(int roleid, boolean isBelong, String query, int start, int limit,
                               String sort, String dir);

}