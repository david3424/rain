package org.david.rain.wmproxy.module.config.manager;


import org.david.rain.wmproxy.common.base.page.Pagination;
import org.david.rain.wmproxy.common.core.CoreManager;
import org.david.rain.wmproxy.module.config.entity.Game;

public interface GameMng extends CoreManager<Game> {
	
	
	/**
	 * 分页查询Game
	 * @param start
	 * @param limit
	 * @param sort
	 * @param dir
	 * @return
	 */
	public Pagination findByName(String query, int start, int limit, String sort, String dir);
}