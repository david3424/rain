package org.david.rain.wmproxy.module.sys.manager.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.david.rain.wmproxy.common.base.page.Pagination;
import org.david.rain.wmproxy.common.core.CoreManagerImpl;
import org.david.rain.wmproxy.module.sys.dao.FunctionDao;
import org.david.rain.wmproxy.module.sys.entity.Function;
import org.david.rain.wmproxy.module.sys.entity.Model;
import org.david.rain.wmproxy.module.sys.manager.FunctionMng;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class FunctionMngImpl extends CoreManagerImpl<Function> implements FunctionMng {

	@Autowired
	public void setDao(FunctionDao dao){
		super.setDao(dao);
	}
	
	public FunctionDao getDao(){
		return (FunctionDao) super.getDao();
	}
	@Transactional(readOnly = true)
	public Set<Model> getFunctionSetModels(Set<Function> functions) {
		
		Set<Model> models = new HashSet<Model>();
		Iterator<Function> it = functions.iterator();
		
		while(it.hasNext()){
			Function fuc = it.next();
			Model model = fuc.getModel();
			if(models.contains(model)) continue;
			
			models.add(model);
			Model parent = model.getParent();
			while(parent != null){
				models.add(model);
				parent = parent.getParent();
			}
		}
		
		return models;
	}
	@Transactional(readOnly = true)
	public Pagination findByName(String query, int start, int limit, String sort, String dir){
		return getDao().findByName(query, start, limit, sort, dir);
	}
}
