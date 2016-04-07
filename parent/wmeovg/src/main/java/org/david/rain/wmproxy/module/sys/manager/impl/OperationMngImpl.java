package org.david.rain.wmproxy.module.sys.manager.impl;


import org.david.rain.wmproxy.common.core.CoreManagerImpl;
import org.david.rain.wmproxy.module.sys.dao.OperationDao;
import org.david.rain.wmproxy.module.sys.entity.Operation;
import org.david.rain.wmproxy.module.sys.manager.OperationMng;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OperationMngImpl extends CoreManagerImpl<Operation> implements OperationMng {
	
	@Autowired
	public void setDao(OperationDao dao){
		super.setDao(dao);
	}
	
	public OperationDao getDao(){
		return (OperationDao) super.getDao();
	}
}
