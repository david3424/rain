package org.david.rain.wmproxy.module.config.dao;


import org.david.rain.wmproxy.common.base.page.Pagination;
import org.david.rain.wmproxy.common.core.CoreDao;
import org.david.rain.wmproxy.module.config.entity.Game;

public interface GameDao  extends CoreDao<Game> {
	/**
	 * 模糊查询
	 * @param name
	 * @param start
	 * @param limit
	 * @param sort
	 * @param dir
	 * @return
	 */
	public Pagination findByName(String query, int start, int limit, String sort, String dir);
	
}
