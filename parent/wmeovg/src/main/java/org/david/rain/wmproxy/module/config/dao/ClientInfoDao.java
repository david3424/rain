package org.david.rain.wmproxy.module.config.dao;


import org.david.rain.wmproxy.common.base.page.Pagination;
import org.david.rain.wmproxy.common.core.CoreDao;
import org.david.rain.wmproxy.module.config.entity.ClientInfo;

public interface ClientInfoDao  extends CoreDao<ClientInfo> {
	/**
	 * 模糊查询
	 * @param start
	 * @param limit
	 * @param sort
	 * @param dir
	 * @return
	 */
	public Pagination findByName(ClientInfo entity, int start, int limit, String sort, String dir);
	
	/**
	 * 模糊查询，当前用户
	 * @param loginName
	 * @param start
	 * @param limit
	 * @param sort
	 * @param dir
	 * @return
	 */
	public Pagination findOwnListByName(String loginName, String query, int start, int limit, String sort, String dir);
	
	/**
	 * 
	 * @Description 生成客户端标识
	 * @return
	 */
	public String genCid();
	
	/**
	 * 
	 * @Description 根据cid查询客户端信息
	 * @param cid
	 * @return
	 */
	public ClientInfo getClientInfoByCid(String cid);
}
