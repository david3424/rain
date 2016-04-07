package org.david.rain.wmproxy.module.config.dao;

import org.david.rain.wmeovg.request.entity.PrizeLog;
import org.david.rain.wmproxy.common.base.page.Pagination;
import org.david.rain.wmproxy.common.core.CoreDao;
import org.david.rain.wmproxy.module.config.entity.WhiteList;
import org.hibernate.HibernateException;


public interface WhiteListDao  extends CoreDao<WhiteList> {
	/**
	 * 模糊查询
	 * @param start
	 * @param limit
	 * @param sort
	 * @param dir
	 * @return
	 */
	public Pagination findByName(WhiteList entity, int start, int limit, String sort, String dir);
	
	/**
	 * 模糊查询，当前用户
	 * @param loginName
	 * @param start
	 * @param limit
	 * @param sort
	 * @param dir
	 * @return
	 */
	public Pagination findOwnListByName(String loginName, String query, int start, int limit, String sort, String dir);
	
	/**
	 * 
	 * @Description 发送总数加count，并保存发奖记录
	 * @param id
	 * @return
	 */
	public int accumulateSendCount(Integer id, PrizeLog prizeLog) throws HibernateException;

	/**
	 * 
	 * @Description 更新发送失败总数并更新兑换记录发送状态
	 * @param prizeLog (AU调用之后的兑换记录更新值)
	 * @return
	 * @throws Exception
	 */
	public int accumulateFailCount(PrizeLog prizeLog) throws HibernateException;
	
	/**
	 * 
	 * @Description 根据兑换请求查询白名单记录id
	 * @param clientInfoId
	 * @param appid
	 * @param prizeid
	 * @return
	 */
	public int getWhiteListIdByPrizeLog(Integer clientInfoId, String appid, Integer prizeid);
	
	/**
	 * 
	 * @Description 获取物品白名单请求状态  
	 * @param cid
	 * @param appid
	 * @param prizeid
	 * @return -1：数据库读取错误 1：客户端兑换请求处于拒绝状态  2：当前应用下的物品兑换请求处于拒绝状态  0: 允许兑换请求
	 */
	public byte	getWhiteListStatus(String cid, String appid, Integer prizeid);
	
	/**
	 * 兑换记录重发时重置发送失败数目
	 * 两种情况： 1、发送数目为一条（更新发送失败白名单数量 ）
	 * 			 2、发送数目为多条 (更新发送失败白名单数量 )
	 */
	public int resetFailCount(PrizeLog prizeLog);


    /**
     *
     * @Description 获取物品白名单请求状态
     * @param cid
     * @param appid
     * @param prizeid
     * @return   Object数组 o{0] 白名单装袋 o[1] 客户端状态 o[2] 邮件标题 o[3] 邮件正文
     */
    public Object[]	getWhiteListStatusAndEmailInfo(String cid, String appid, Integer prizeid);
}
