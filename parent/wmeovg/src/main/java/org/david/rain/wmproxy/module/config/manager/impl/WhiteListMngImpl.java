package org.david.rain.wmproxy.module.config.manager.impl;

import org.david.rain.wmeovg.request.entity.PrizeLog;
import org.david.rain.wmproxy.common.base.page.Pagination;
import org.david.rain.wmproxy.common.core.CoreManagerImpl;
import org.david.rain.wmproxy.module.config.dao.WhiteListDao;
import org.david.rain.wmproxy.module.config.entity.WhiteList;
import org.david.rain.wmproxy.module.config.manager.WhiteListMng;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class WhiteListMngImpl extends CoreManagerImpl<WhiteList> implements WhiteListMng {

	@Autowired
	public void setDao(WhiteListDao dao) {
		super.setDao(dao);
	}

	public WhiteListDao getDao() {
		return (WhiteListDao) super.getDao();
	}
	
	@Transactional(readOnly = true)
	public Pagination findByName(WhiteList entity, int start, int limit, String sort, String dir){
		return getDao().findByName(entity, start, limit, sort, dir);
	}
	
	@Transactional(readOnly = true)
	public Pagination findOwnListByName(String loginName, String query, int start, int limit, String sort, String dir){
		return getDao().findOwnListByName(loginName, query, start, limit, sort, dir);
	}
	
	public int accumulateSendCount(Integer id, PrizeLog prizeLog) throws HibernateException{
		
		return getDao().accumulateSendCount(id,prizeLog);
	}

	public int accumulateFailCount(PrizeLog prizeLog) throws HibernateException{
		
		return getDao().accumulateFailCount(prizeLog);
	}
	

	@Transactional(readOnly = true)
	public int getWhiteListIdByPrizeLog(Integer clientInfoId, String appid, Integer prizeid){
		
		return getDao().getWhiteListIdByPrizeLog(clientInfoId, appid, prizeid);
	}
	@Transactional(readOnly = true)
	public byte	getWhiteListStatus(String cid, String appid, Integer prizeid){
		
		return getDao().getWhiteListStatus(cid, appid, prizeid);
	}

    @Transactional(readOnly = true)
    public Object[]	getWhiteListStatusAndEmailInfo(String cid, String appid, Integer prizeid){
        return getDao().getWhiteListStatusAndEmailInfo(cid,appid,prizeid);
    }
	/**
	 * 兑换记录重发时重置发送失败数目
	 * 两种情况： 1、发送数目为一条（更新发送失败白名单数量 ）
	 * 			 2、发送数目为多条 (更新发送失败白名单数量 )
	 */
	public int resetFailCount(PrizeLog prizeLog){
		
		return getDao().resetFailCount(prizeLog);
	}



}
