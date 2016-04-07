package org.david.rain.wmproxy.module.config.manager.impl;

import org.david.rain.wmproxy.common.base.page.Pagination;
import org.david.rain.wmproxy.common.core.CoreManagerImpl;
import org.david.rain.wmproxy.module.config.dao.ClientInfoDao;
import org.david.rain.wmproxy.module.config.entity.ClientInfo;
import org.david.rain.wmproxy.module.config.manager.ClientInfoMng;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class ClientInfoMngImpl extends CoreManagerImpl<ClientInfo> implements ClientInfoMng {

	@Autowired
	public void setDao(ClientInfoDao dao) {
		super.setDao(dao);
	}

	public ClientInfoDao getDao() {
		return (ClientInfoDao) super.getDao();
	}
	
	@Transactional(readOnly = true)
	public Pagination findByName(ClientInfo entity, int start, int limit, String sort, String dir){
		return getDao().findByName(entity, start, limit, sort, dir);
	}
	
	@Transactional(readOnly = true)
	public Pagination findOwnListByName(String loginName, String query, int start, int limit, String sort, String dir){
		return getDao().findOwnListByName(loginName, query, start, limit, sort, dir);
	}
	
	@Transactional(readOnly = true)
	public String genCid(){
		return getDao().genCid();
	}
	@Transactional(readOnly = true)
	public ClientInfo getClientInfoByCid(String cid){
		
		return getDao().getClientInfoByCid(cid);
	}
}
