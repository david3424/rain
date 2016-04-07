package org.david.rain.wmproxy.module.sys.dao.impl;

import org.david.rain.wmproxy.common.base.hibernate3.Finder;
import org.david.rain.wmproxy.common.base.page.Pagination;
import org.david.rain.wmproxy.common.core.CoreDaoImpl;
import org.david.rain.wmproxy.module.sys.dao.ModelDao;
import org.david.rain.wmproxy.module.sys.entity.Model;
import org.david.rain.wmproxy.module.util.SqlUtils;
import org.springframework.stereotype.Repository;

@Repository
public class ModelDaoImpl extends CoreDaoImpl<Model>  implements ModelDao {
	public Pagination findByName(String query, int start, int limit,
			String sort, String dir) {

		String hql = "select bean from Model bean";
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
