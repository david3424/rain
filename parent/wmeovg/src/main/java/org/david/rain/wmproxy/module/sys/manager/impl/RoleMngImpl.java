package org.david.rain.wmproxy.module.sys.manager.impl;

import org.david.rain.wmproxy.common.base.page.Pagination;
import org.david.rain.wmproxy.common.core.CoreManagerImpl;
import org.david.rain.wmproxy.module.sys.dao.RoleDao;
import org.david.rain.wmproxy.module.sys.entity.Role;
import org.david.rain.wmproxy.module.sys.manager.RoleMng;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class RoleMngImpl extends CoreManagerImpl<Role> implements RoleMng {
	
	@Autowired
	public void setDao(RoleDao dao){
		super.setDao(dao);
	}
	
	public RoleDao getDao(){
		return (RoleDao) super.getDao();
	}
	@Transactional(readOnly = true)
	public Pagination findByName(String query, int start, int limit, String sort, String dir){
		return getDao().findByName(query, start, limit, sort, dir);
	}
	@Transactional(readOnly = true)
	public Pagination findUsers(int roleid,boolean isBelong, String query, int start, int limit,
			String sort, String dir){
		
		return getDao().findUsers(roleid, isBelong, query, start, limit, sort, dir);
	}
	@Transactional(readOnly = true)
	public Pagination findFucs(int roleid,boolean isBelong, String query, int start, int limit,
			String sort, String dir){
		
		return getDao().findFucs(roleid, isBelong, query, start, limit, sort, dir);
	}
}
