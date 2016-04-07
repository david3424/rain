package org.david.rain.wmproxy.module.config.manager.impl;

import org.david.rain.wmproxy.common.base.page.Pagination;
import org.david.rain.wmproxy.common.core.CoreManagerImpl;
import org.david.rain.wmproxy.module.config.dao.GameDao;
import org.david.rain.wmproxy.module.config.entity.Game;
import org.david.rain.wmproxy.module.config.manager.GameMng;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GameMngImpl extends CoreManagerImpl<Game> implements GameMng {

	@Autowired
	public void setDao(GameDao dao) {
		super.setDao(dao);
	}

	public GameDao getDao() {
		return (GameDao) super.getDao();
	}
	
	@Transactional(readOnly = true)
	public Pagination findByName(String query, int start, int limit, String sort, String dir){
		return getDao().findByName(query, start, limit, sort, dir);
	}
}
