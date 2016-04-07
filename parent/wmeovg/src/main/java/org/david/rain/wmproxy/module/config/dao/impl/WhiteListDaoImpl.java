package org.david.rain.wmproxy.module.config.dao.impl;

import org.david.rain.wmeovg.request.entity.PrizeLog;
import org.david.rain.wmproxy.common.base.hibernate3.Finder;
import org.david.rain.wmproxy.common.base.page.Pagination;
import org.david.rain.wmproxy.common.core.CoreDaoImpl;
import org.david.rain.wmproxy.module.config.dao.WhiteListDao;
import org.david.rain.wmproxy.module.config.entity.WhiteList;
import org.david.rain.wmproxy.module.util.ExceedTotalCountException;
import org.david.rain.wmproxy.module.util.SqlUtils;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;


@Repository
public class WhiteListDaoImpl extends CoreDaoImpl<WhiteList> implements
        WhiteListDao {

	public Pagination findByName(WhiteList entity, int start, int limit,
			String sort, String dir) {

		String hql = "select bean from WhiteList bean where 1=1 ";
		Finder finder = Finder.create(hql);
		
		if(entity != null && entity.getAppid() != null && entity.getAppid().trim().length() > 0)
			 finder.append(" and bean.appid = :appid").setParam("appid",  entity.getAppid());
		
		if(entity != null && entity.getPrizeid() != null &&  entity.getPrizeid()!= 0)
			 finder.append(" and bean.prizeid = :prizeid").setParam("prizeid",  entity.getPrizeid() );
		
		if(entity != null && entity.getClientInfo().getId() != null)
			 finder.append(" and bean.clientInfo.id = :clientInfoId").setParam("clientInfoId", entity.getClientInfo().getId() );
		
		if(entity != null && entity.getUser().getId() != null)
			 finder.append(" and bean.user.id = :userid").setParam("userid", entity.getUser().getId() );
		
		if (sort != null && dir != null)
			finder.append(" order by bean" + "." + sort + " " + dir);

		return find(finder, start, limit);
	}
	
	public Pagination findOwnListByName(String loginName, String query, int start, int limit,
			String sort, String dir) {

		/*String hql = "select bean from WhiteList bean where bean.user.loginName = :loginName";
		Finder finder = Finder.create(hql).setParam("loginName", loginName);
		*/
		String hql = "select bean from WhiteList bean where 1 = 1";
		Finder finder = Finder.create(hql);
		
		query = SqlUtils.escape(query);
		
		if (query != null)
			finder.append(" and bean.prizename like :prizename").setParam(
					"prizename", "%" + query + "%");
		if (sort != null && dir != null)

			finder.append(" order by bean" + "." + sort + " " + dir);

		return find(finder, start, limit);
	}

	public int accumulateSendCount(Integer id, PrizeLog prizeLog) throws HibernateException {

		String hql = "update WhiteList bean  set bean.sendCount = bean.sendCount + :count where bean.id = :id and (bean.count=-1 or (bean.sendCount + :count) <= bean.count)";

		int count = -1;
		try{
			int c = getSession().createQuery(hql).setInteger("count",
					prizeLog.getCount()).setInteger("id", id).executeUpdate();
	
			if( c <= 0){
				
				throw new ExceedTotalCountException(prizeLog.getGid() + " 请求失败，超过发送总数(SQL)" );
			}
			
			getSession().save(prizeLog);
			count = c;
		}catch (HibernateException e) {

			throw e;
		}
		return count;
	}

	public int accumulateFailCount(PrizeLog prizeLog) throws HibernateException {

		int sendStatus = prizeLog.getSendStatus();
		int count = prizeLog.getCount();   // count >=1
		int processCount = prizeLog.getProcessCount(); // processCount >= 0 且processCount <= count
		
		if(count > 1) // 如果发送数目为1个，则可能更新的数目为1
			count = count - processCount; // 如果发送数目大于1，则可能更新的数目为 发送总数 - 已成功发送总数
		int c = -1;
		try{
			String hql = "update WhiteList bean set bean.failCount = bean.failCount + :count where bean.clientInfo = (select ci from ClientInfo ci where ci.cid=:cid) and bean.appid=:appid and bean.prizeid=:prizeid";
			if(sendStatus != 0 && count > 0){ // AU接口返回不为0则标记为发送失败记录, 此时count必然大于0
				
				c = getSession().createQuery(hql).setInteger("count", count)
						.setString("cid", prizeLog.getCid()).setString("appid", prizeLog.getAppid()).setInteger("prizeid", prizeLog.getPrizeid())
						.executeUpdate();
			}
			
			if(sendStatus == 0 || c > 0){ // 如果全部发送成功 或者  更新失败记录成功，则更新兑换记录的发送状态、发送时间、处理个数
				hql = "update PrizeLog bean set sendStatus = :sendStatus, sendTime = :sendTime, processCount=:processCount where bean.gid = :gid";
				c = getSession().createQuery(hql).setInteger("sendStatus", prizeLog.getSendStatus())
				.setTimestamp("sendTime", prizeLog.getSendTime()).setInteger("processCount",
						processCount).setString("gid", prizeLog.getGid()).executeUpdate();
			}	
		}catch (HibernateException e) {
			
			throw e;
		}
		
		return c;
	}
	


	/**
	 * 兑换记录重发时重置发送失败数目
	 * 两种情况： 1、发送数目为一条（更新发送失败白名单数量 ）
	 * 			 2、发送数目为多条 (更新发送失败白名单数量 )
	 */
	public int resetFailCount(PrizeLog prizeLog){
		
		String hql = "update WhiteList bean set bean.failCount = bean.failCount - :count where bean.clientInfo = (select ci from ClientInfo ci where ci.cid=:cid) and bean.appid=:appid and bean.prizeid=:prizeid";
		
		int failCount = 1;
		if(prizeLog.getCount() > 1)
			failCount = prizeLog.getCount() - prizeLog.getProcessCount();
		
		if(failCount <= 0 || prizeLog.getSendStatus() == 0){
			return -2;
		}
		
		return getSession().createQuery(hql).setInteger("count", failCount)
		.setString("cid", prizeLog.getCid()).setString("appid", prizeLog.getAppid()).setInteger("prizeid", prizeLog.getPrizeid())
		.executeUpdate();
	}

	public int getWhiteListIdByPrizeLog(Integer clientInfoId, String appid, Integer prizeid) {

		String hql = "select bean.id, bean.status, bean.clientInfo.status  from WhiteList bean where bean.clientInfo.id = :clientInfoId and bean.appid=:appid and bean.prizeid =:prizeid";

		Object[] o = (Object[])getSession().createQuery(hql).setInteger(
				"clientInfoId", clientInfoId).setString("appid", appid).setInteger("prizeid", prizeid)
				.uniqueResult();
		int id = 0;
		
		if(o != null){
			id = (Integer)o[0];
			byte whiteListStatus= (Byte)o[1];
			byte clientInfoStatus = (Byte)o[2];
			
			if(whiteListStatus == -1){
				id = -1;
			}else if(clientInfoStatus == -1){
				id = -2;
			}
		}
		return id;
	}
	
	public byte	getWhiteListStatus(String cid, String appid, Integer prizeid){
		
		byte status = -1;
		
		String hql = "select bean.status, bean.clientInfo.status from WhiteList bean where bean.clientInfo.cid = :cid and bean.appid=:appid and bean.prizeid =:prizeid";
		
		Object[] o = (Object[])getSession().createQuery(hql).setString(
				"cid", cid).setString("appid", appid).setInteger("prizeid", prizeid)
				.uniqueResult();
		
		if( o!= null){
			byte whiteListStatus= (Byte)o[0];
			byte clientInfoStatus = (Byte)o[1];
			
			if(whiteListStatus == -1){
				status = 1;
			}else if( clientInfoStatus == -1){
				status = 2;
			}else{
				status = 0;
			}
		}
		return status;
	}



    /***
     * 得到状态和邮件发送的信息
     * ***/
    public Object[]	getWhiteListStatusAndEmailInfo(String cid, String appid, Integer prizeid){

        byte status = -1;

        String hql = "select bean.status, bean.clientInfo.status, bean.title, bean.text from WhiteList bean where bean.clientInfo.cid = :cid and bean.appid=:appid and bean.prizeid =:prizeid";

        Object[] o = (Object[])getSession().createQuery(hql).setString(
                "cid", cid).setString("appid", appid).setInteger("prizeid", prizeid)
                .uniqueResult();

        if( o!= null){
            byte whiteListStatus= (Byte)o[0];
            byte clientInfoStatus = (Byte)o[1];

            if(whiteListStatus == -1){
                status = 1;
            }else if( clientInfoStatus == -1){
                status = 2;
            }else{
                status = 0;
            }
        }
        //通过白名单和客户端的状态判断是否开通
        o[0]=status;
        return o;
    }



}
