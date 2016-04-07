package org.david.rain.wmproxy.module.service.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.david.rain.wmeovg.request.entity.PrizeLog;
import org.david.rain.wmproxy.common.base.hibernate3.Finder;
import org.david.rain.wmproxy.common.base.page.Pagination;
import org.david.rain.wmproxy.common.core.CoreDaoImpl;
import org.david.rain.wmproxy.module.service.action.PrizeLogSearchBean;
import org.david.rain.wmproxy.module.service.dao.PrizeLogDao;
import org.springframework.stereotype.Repository;


@Repository
public class PrizeLogDaoImpl extends CoreDaoImpl<PrizeLog> implements
        PrizeLogDao {

	public int updateSendStatusByGID(String gid, int status, int processCount,
			Date sendTime) {

		String hql = "update PrizeLog bean set sendStatus = :status, sendTime = :sendTime, processCount=:processCount where bean.gid = :gid";

		return getSession().createQuery(hql).setInteger("status", status)
				.setTimestamp("sendTime", sendTime).setInteger("processCount",
						processCount).setString("gid", gid).executeUpdate();
	}

	public int insertMessageIdByGID(String gid, String messageId) throws Exception {
		String hql = "update PrizeLog bean set messageId = :messageId where bean.gid = :gid";

		int rtn = -1;
		try {
			rtn = getSession().createQuery(hql).setString("messageId",
					messageId).setString("gid", gid).executeUpdate();
		} catch (Exception e) {
			
			throw e;
		}
		return rtn;
	}

	public int accumulatePrizeResendCountByGID(String gid) {
		String hql = "update PrizeLog bean set prizeResendCount = prizeResendCount + 1 where bean.gid = :gid";
		return getSession().createQuery(hql).setString("gid", gid)
				.executeUpdate();
	}

	public byte getPrizeResendCountByGID(String gid) {
		String hql = "select prizeResendCount from PrizeLog bean where bean.gid = :gid";
		Object o = getSession().createQuery(hql).setString("gid", gid)
				.uniqueResult();
		if (o == null)
			return (byte) 0;
		return (Byte) o;
	}

	public int accumulateCallbackCountByGID(String gid) {
		String hql = "update PrizeLog bean set callbackCount = callbackCount + 1 where bean.gid = :gid";
		return getSession().createQuery(hql).setString("gid", gid)
				.executeUpdate();
	}

	public byte getCallbackCountByGID(String gid) {
		String hql = "select callbackCount from PrizeLog bean where bean.gid = :gid";
		Object o = getSession().createQuery(hql).setString("gid", gid)
				.uniqueResult();
		if (o == null)
			return (byte) 0;
		return (Byte) o;
	}

	public int updateCallbackStatusByGID(String gid, int callbackHttpStatus,
			Date callbackTime) {
		String hql = "update PrizeLog bean set callbackHttpStatus = :callbackHttpStatus, callbackTime = :callbackTime where bean.gid = :gid";

		return getSession().createQuery(hql).setInteger("callbackHttpStatus",
				callbackHttpStatus).setTimestamp("callbackTime", callbackTime)
				.setString("gid", gid).executeUpdate();
	}

	public int getTotalCount(Finder finder) {

		return countQueryResult(finder);
	}

	public Pagination pageBySearchBean(PrizeLogSearchBean searchBean,
			Integer start, Integer limit, String sort, String dir) {

		Finder finder = PrizeLogSearchBean.getFinder(searchBean, sort, dir);

		if (start == null)
			start = 0;

		if (limit == 0) // 响应 前台combox重新加载
			limit = countQueryResult(finder);

		return find(finder, start, limit);
	}

	public PrizeLog queryByGid(String gid) {
		
		if(gid == null){
			return null;
		}
		
		String hql = "select bean from PrizeLog bean where bean.gid = :gid";
		Object o = getSession().createQuery(hql).setString("gid", gid)
				.uniqueResult();

		return (PrizeLog) o;
	}

	@SuppressWarnings("unchecked")
	public List<PrizeLog> queryByAccount(String account) {

		if(account==null || account.trim().length() == 0){
			return new ArrayList<PrizeLog>();
		}
		
		String hql = "select bean from PrizeLog bean where bean.account = :account";

		return getSession().createQuery(hql).setString("account", account)
				.list();
	}

	@SuppressWarnings("unchecked")
	public List<PrizeLog> queryByRequestTime(Date start, Date end) {

		if(start == null || end == null){
			return new ArrayList<PrizeLog>();
		}
		
		String hql = "select bean from PrizeLog bean where bean.requestTime >= :start and bean.requestTime <= :end";

		return getSession().createQuery(hql).setTimestamp("start", start)
				.setTimestamp("end", end).list();
	}
}
