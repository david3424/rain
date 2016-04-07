package org.david.rain.wmproxy.module.config.dao.impl;

import org.david.rain.wmproxy.common.base.hibernate3.Finder;
import org.david.rain.wmproxy.common.base.page.Pagination;
import org.david.rain.wmproxy.common.core.CoreDaoImpl;
import org.david.rain.wmproxy.module.config.dao.GameDao;
import org.david.rain.wmproxy.module.config.entity.Game;
import org.david.rain.wmproxy.module.util.SqlUtils;
import org.springframework.stereotype.Repository;


@Repository
public class GameDaoImpl extends CoreDaoImpl<Game> implements
        GameDao {
	
	public Pagination findByName(String query, int start, int limit,
			String sort, String dir) {

		String hql = "select bean from Game bean";
		Finder finder = Finder.create(hql);
		query = SqlUtils.escape(query);
		
		if (query != null)
			finder.append(" where bean.name like :name").setParam("name", "%" +query + "%" );
		if (sort != null && dir != null)
			finder.append(" order by bean" + "." + sort + " " + dir);
		
		if(limit == 0) //响应 前台combox重新加载
			limit = countQueryResult(finder);
		
		return find(finder, start, limit);
	}
}
