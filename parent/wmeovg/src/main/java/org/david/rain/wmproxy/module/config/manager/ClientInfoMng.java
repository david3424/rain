package org.david.rain.wmproxy.module.config.manager;


import org.david.rain.wmproxy.common.base.page.Pagination;
import org.david.rain.wmproxy.common.core.CoreManager;
import org.david.rain.wmproxy.module.config.entity.ClientInfo;

public interface ClientInfoMng extends CoreManager<ClientInfo> {
	
	
	/**
	 * 分页查询ClientInfo
	 * @param name
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
	 * @param name
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