package org.david.rain.wmproxy.module.sys.dao.impl;

import org.david.rain.wmproxy.common.base.hibernate3.Finder;
import org.david.rain.wmproxy.common.base.page.Pagination;
import org.david.rain.wmproxy.common.core.CoreDaoImpl;
import org.david.rain.wmproxy.module.sys.dao.RoleDao;
import org.david.rain.wmproxy.module.sys.entity.Role;
import org.david.rain.wmproxy.module.util.SqlUtils;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDaoImpl extends CoreDaoImpl<Role> implements RoleDao {

	public Pagination findByName(String query, int start, int limit,
			String sort, String dir) {

		String hql = "select bean from Role bean";
		Finder finder = Finder.create(hql);
		query = SqlUtils.escape(query);
		
		if (query != null)
			finder.append(" where bean.name like :name").setParam("name",
					"%" + query + "%");
		if (sort != null && dir != null)
			finder.append(" order by bean" + "." + sort + " " + dir);

		return find(finder, start, limit);

	}

	public Pagination findUsers(int roleid, boolean isBelong, String query,
			int start, int limit, String sort, String dir) {

			String hql = "select bean from User bean left join bean.roles role";
			
			if(!isBelong)
				hql = "select bean2 from User bean2 where bean2 not in ( " + hql; 
			
			Finder finder = Finder.create(hql);
			finder.append(" where 1 = 1 ");

			if (query != null)
				finder.append(" and bean.name like :name").setParam("name",
						"%" + query + "%");
			finder.append(" and role.id = :roleid").setParam("roleid", roleid);

			if(!isBelong)
				finder.append(" ) ");
			
			if (sort != null && dir != null){
				if(!isBelong)
					finder.append(" order by bean2" + "." + sort + " " + dir);
				else
					finder.append(" order by bean" + "." + sort + " " + dir);
			}

			return find(finder, start, limit);
		}
	
	public Pagination findFucs(int roleid, boolean isBelong, String query,
			int start, int limit, String sort, String dir) {

			String hql = "select bean from Function bean left join bean.roles role";
			
			if(!isBelong)
				hql = "select bean2 from Function bean2 where bean2 not in ( " + hql; 
			
			Finder finder = Finder.create(hql);
			finder.append(" where 1 = 1 ");

			if (query != null)
				finder.append(" and bean.name like :name").setParam("name",
						"%" + query + "%");
			finder.append(" and role.id = :roleid").setParam("roleid", roleid);

			if(!isBelong)
				finder.append(" ) ");
			
			if (sort != null && dir != null){
				if(!isBelong)
					finder.append(" order by bean2" + "." + sort + " " + dir);
				else
					finder.append(" order by bean" + "." + sort + " " + dir);
			}

			return find(finder, start, limit);
		}
}
