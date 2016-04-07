package org.david.rain.wmproxy.module.sys.dao.impl;

import org.david.rain.wmproxy.common.base.hibernate3.Finder;
import org.david.rain.wmproxy.common.base.page.Pagination;
import org.david.rain.wmproxy.common.core.CoreDaoImpl;
import org.david.rain.wmproxy.module.sys.dao.FunctionDao;
import org.david.rain.wmproxy.module.sys.entity.Function;
import org.david.rain.wmproxy.module.util.SqlUtils;
import org.springframework.stereotype.Repository;


@Repository
public class FunctionDaoImpl extends CoreDaoImpl<Function> implements FunctionDao {

	public Pagination findByName(String query, int start, int limit,
			String sort, String dir) {

		String hql = "select bean from Function bean";
		Finder finder = Finder.create(hql);
		
		query = SqlUtils.escape(query);
		
		if (query != null)
			finder.append(" where bean.name like :name").setParam("name", "%" +query + "%" );
		if (sort != null && dir != null)
			
			finder.append(" order by bean" + "." + sort + " " + dir);
		
		return find(finder, start, limit);

	}
}
