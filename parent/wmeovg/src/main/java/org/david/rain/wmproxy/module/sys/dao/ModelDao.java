package org.david.rain.wmproxy.module.sys.dao;


import org.david.rain.wmproxy.common.base.page.Pagination;
import org.david.rain.wmproxy.common.core.CoreDao;
import org.david.rain.wmproxy.module.sys.entity.Model;

public interface ModelDao  extends CoreDao<Model> {
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
