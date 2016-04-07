package org.david.rain.wmproxy.module.sys.manager;


import org.david.rain.wmproxy.common.base.page.Pagination;
import org.david.rain.wmproxy.common.core.CoreManager;
import org.david.rain.wmproxy.module.sys.entity.Log;

public interface LogMng extends CoreManager<Log> {

	/**
	 * 模糊查询
	 * @param start
	 * @param limit
	 * @param sort
	 * @param dir
	 * @return
	 */
	public Pagination findByName(String query, int start, int limit, String sort, String dir);
}