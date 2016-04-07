package org.david.rain.wmproxy.module.service.manager.impl;

import java.util.Date;
import java.util.List;

import org.david.rain.wmeovg.request.entity.PrizeLog;
import org.david.rain.wmproxy.common.base.hibernate3.Finder;
import org.david.rain.wmproxy.common.base.page.Pagination;
import org.david.rain.wmproxy.common.core.CoreManagerImpl;
import org.david.rain.wmproxy.module.service.action.PrizeLogSearchBean;
import org.david.rain.wmproxy.module.service.dao.PrizeLogDao;
import org.david.rain.wmproxy.module.service.manager.PrizeLogMng;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class PrizeLogMngImpl extends CoreManagerImpl<PrizeLog> implements PrizeLogMng {
	@Autowired
	public void setDao(PrizeLogDao dao) {
		super.setDao(dao);
	}

	public PrizeLogDao getDao() {
		return (PrizeLogDao) super.getDao();
	}
	
	public int updateSendStatusByGID(String gid, int status, int processCount, Date sendTime){
		
		return getDao().updateSendStatusByGID(gid, status, processCount, sendTime);
	}
	
	public int insertMessageIdByGID(String gid, String messageId) throws Exception{
		return getDao().insertMessageIdByGID(gid, messageId);
	}
	
	public int accumulatePrizeResendCountByGID(String gid){
		return getDao().accumulatePrizeResendCountByGID(gid);
	}
	
	@Transactional(readOnly=true)
	public byte getPrizeResendCountByGID(String gid){
		return getDao().getPrizeResendCountByGID(gid);
	}

	@Override
	public int accumulateCallbackCountByGID(String gid) {
		return getDao().accumulateCallbackCountByGID(gid);
	}

	@Transactional(readOnly=true)
	public byte getCallbackCountByGID(String gid) {
		return getDao().getCallbackCountByGID(gid);
	}

	@Override
	public int updateCallbackStatusByGID(String gid, int httpStatus,
			Date callbackTime) {
		return getDao().updateCallbackStatusByGID(gid, httpStatus, callbackTime);
	}

	@Transactional(readOnly=true)
	public Pagination pageBySearchBean(PrizeLogSearchBean searchBean, Integer start, Integer limit, String sort, String dir){
		return getDao().pageBySearchBean(searchBean, start, limit, sort, dir);
	}
	
	@Transactional(readOnly=true)
	public int getTotalCount(Finder finder){
		return getDao().getTotalCount(finder);
	}
	
	@Transactional(readOnly=true)
	public PrizeLog queryByGid(String gid){
		return getDao().queryByGid(gid);
	}

	@Transactional(readOnly=true)
	public List<PrizeLog> queryByAccount(String account){
		return getDao().queryByAccount(account);
	}

	@Transactional(readOnly=true)
	public List<PrizeLog> queryByRequestTime(Date start, Date end){
		return getDao().queryByRequestTime(start, end);
	}
}
