package org.david.rain.wmproxy.module.service.dao;

import org.david.rain.wmeovg.request.entity.PrizeLog;
import org.david.rain.wmproxy.common.base.hibernate3.Finder;
import org.david.rain.wmproxy.common.base.page.Pagination;
import org.david.rain.wmproxy.common.core.CoreDao;
import org.david.rain.wmproxy.module.service.action.PrizeLogSearchBean;

import java.util.Date;
import java.util.List;


public interface PrizeLogDao  extends CoreDao<PrizeLog> {

	/**
	 * 更新兑奖记录发送状态
	 * @param gid 兑奖记录主键
	 * @param status 状态值
	 * @return
	 */
	public int updateSendStatusByGID(String gid, int status, int processCount, Date sendTime);
	
	/**
	 * 记录兑奖记录对应的JMS消息ID
	 * @param gid 兑奖记录主键
	 * @param messageId JMS消息ID
	 * @return
	 */
	public int insertMessageIdByGID(String gid, String messageId) throws Exception;
	
	/**
	 * 更新兑奖记录重新发送次数：累加1
	 * @param gid
	 * @return
	 */
	public int accumulatePrizeResendCountByGID(String gid);
	
	/**
	 * 获取重新发送次数
	 * @param gid
	 * @return
	 */
	public byte getPrizeResendCountByGID(String gid);
	
	/**
	 * 更新请求客户端接口重新发送次数：累加1
	 * @param gid
	 * @return
	 */
	public int accumulateCallbackCountByGID(String gid);
	
	/**
	 * 获取重新请求客户端接口次数
	 * @param gid
	 * @return
	 */
	public byte getCallbackCountByGID(String gid);
	
	/**
	 * 更新客户端接口请求状态
	 * @param gid 兑奖记录主键
	 * @param status 状态值
	 * @return
	 */
	public int updateCallbackStatusByGID(String gid, int httpStatus, Date callbackTime);
	
	/**
	 * 模糊查询
	 * @param name
	 * @param start
	 * @param limit
	 * @param sort
	 * @param dir
	 * @return
	 */
	public Pagination pageBySearchBean(PrizeLogSearchBean searchBean, Integer start, Integer limit, String sort, String dir);
	
	/**
	 * 
	 * @Description 总数
	 * @param finder
	 * @return
	 */
	public int getTotalCount(Finder finder);
	
	/**
	 * 
	 * @Description 根据流水号查询兑换记录
	 * @param gid
	 * @return
	 */
	public PrizeLog queryByGid(String gid);
	
	/**
	 * 
	 * @Description 根据账号查询兑换记录
	 * @param account
	 * @return
	 */
	public List<PrizeLog> queryByAccount(String account);
	
	/**
	 * 
	 * @Description 根据兑换请求时间查询兑换记录
	 * @param start 
	 * @param end
	 * @return
	 */
	public List<PrizeLog> queryByRequestTime(Date start, Date end);
}
