package org.david.rain.wmproxy.module.sys.manager.impl;

import org.david.rain.wmproxy.common.base.page.Pagination;
import org.david.rain.wmproxy.common.core.CoreManagerImpl;
import org.david.rain.wmproxy.module.sys.dao.LogDao;
import org.david.rain.wmproxy.module.sys.entity.Log;
import org.david.rain.wmproxy.module.sys.manager.LogMng;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LogMngImpl extends CoreManagerImpl<Log> implements LogMng {

	@Autowired
	public void setDao(LogDao dao){
		super.setDao(dao);
	}
	
	public LogDao getDao(){
		return (LogDao) super.getDao();
	}
	@Transactional(readOnly = true)
	public Pagination findByName(String query, int start, int limit, String sort, String dir){
		return getDao().findByName(query, start, limit, sort, dir);
	}
}
