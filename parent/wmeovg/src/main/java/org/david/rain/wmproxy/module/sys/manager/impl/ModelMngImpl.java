package org.david.rain.wmproxy.module.sys.manager.impl;

import org.david.rain.wmproxy.common.base.page.Pagination;
import org.david.rain.wmproxy.common.core.CoreManagerImpl;
import org.david.rain.wmproxy.module.sys.dao.ModelDao;
import org.david.rain.wmproxy.module.sys.entity.Model;
import org.david.rain.wmproxy.module.sys.manager.ModelMng;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ModelMngImpl extends CoreManagerImpl<Model> implements ModelMng {

	
	@Autowired
	public void setDao(ModelDao dao){
		super.setDao(dao);
	}
	
	public ModelDao getDao(){
		return (ModelDao) super.getDao();
	}
	@Transactional(readOnly = true)
	public Pagination findByName(String query, int start, int limit, String sort, String dir){
		return getDao().findByName(query, start, limit, sort, dir);
	}
}
