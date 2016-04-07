package org.david.rain.wmproxy.module.sys.dao.impl;

import org.david.rain.wmproxy.common.base.hibernate3.Finder;
import org.david.rain.wmproxy.common.base.page.Pagination;
import org.david.rain.wmproxy.common.core.CoreDaoImpl;
import org.david.rain.wmproxy.module.sys.dao.UserDao;
import org.david.rain.wmproxy.module.sys.entity.User;
import org.david.rain.wmproxy.module.util.SqlUtils;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl extends CoreDaoImpl<User> implements UserDao {

	public User getUserByLoginName(String loginName) {

		String hql = "from User u where u.loginName=?";
		return (User) findUnique(hql, loginName);
	}

	public Pagination findByName(String query, int start, int limit,
			String sort, String dir) {

		String hql = "select bean from User bean";
		Finder finder = Finder.create(hql);
		query = SqlUtils.escape(query);
		
		if (query != null)
			finder.append(" where bean.name like :name").setParam("name", "%" +query + "%" );
		if (sort != null && dir != null)
			
			finder.append(" order by bean" + "." + sort + " " + dir);
		
		return find(finder, start, limit);

	}
}
