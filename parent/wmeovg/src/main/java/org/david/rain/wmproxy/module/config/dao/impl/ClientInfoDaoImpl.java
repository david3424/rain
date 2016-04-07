package org.david.rain.wmproxy.module.config.dao.impl;

import java.text.DecimalFormat;

import org.david.rain.wmproxy.common.base.hibernate3.Finder;
import org.david.rain.wmproxy.common.base.page.Pagination;
import org.david.rain.wmproxy.common.core.CoreDaoImpl;
import org.david.rain.wmproxy.module.config.dao.ClientInfoDao;
import org.david.rain.wmproxy.module.config.entity.ClientInfo;
import org.david.rain.wmproxy.module.util.SqlUtils;
import org.springframework.stereotype.Repository;


@Repository
public class ClientInfoDaoImpl extends CoreDaoImpl<ClientInfo> implements
        ClientInfoDao {
	
	public Pagination findByName(ClientInfo entity, int start, int limit,
			String sort, String dir) {

		String hql = "select bean from ClientInfo bean where 1=1 ";
		Finder finder = Finder.create(hql);
		
		
		if(entity != null && entity.getId() != null)
			 finder.append(" and bean.id = :id").setParam("id", entity.getId() );
		
		if(entity != null && entity.getUser().getId() != null)
			 finder.append(" and bean.user.id = :userid").setParam("userid", entity.getUser().getId() );
		
		if (sort != null && dir != null)
			finder.append(" order by bean" + "." + sort + " " + dir);
		
		if(limit == 0) //响应 前台combox重新加载
			limit = countQueryResult(finder);
		
		return find(finder, start, limit);
	}
	
	public Pagination findOwnListByName(String loginName, String query, int start, int limit,
			String sort, String dir) {

		/* String hql = "select bean from ClientInfo bean where bean.user.loginName = :loginName";
		Finder finder = Finder.create(hql).setParam("loginName", loginName);
		*/
		String hql = "select bean from ClientInfo bean where 1 = 1";
		Finder finder = Finder.create(hql);
		
		query = SqlUtils.escape(query);
		
		if (query != null)
			finder.append(" and bean.name like :name").setParam("name", "%" +query + "%" );
		if (sort != null && dir != null)
			finder.append(" order by bean" + "." + sort + " " + dir);
		
		if(limit == 0) //响应 前台combox重新加载
			limit = countQueryResult(finder);
		
		return find(finder, start, limit);
	}
	
	public String genCid(){
		
		String hql = "select max(cid) from ClientInfo bean";
		Object o = getSession().createQuery(hql).uniqueResult();
		if (o == null)
			return "01";
		int cid = Integer.valueOf(o.toString());
		
		DecimalFormat df = new DecimalFormat("00");

		return df.format(cid+1);
	}
	
	public ClientInfo getClientInfoByCid(String cid){
		
		String hql = "select bean from ClientInfo bean where cid=:cid";
		
		return (ClientInfo)getSession().createQuery(hql).setString("cid", cid).uniqueResult();
	}
}
