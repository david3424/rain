package org.david.rain.wmeovg.request.hessian;

import org.david.rain.wmeovg.request.entity.PrizeLog;

import java.util.Date;
import java.util.List;


/**
 * @ClassName IPrizeService
 * @Description ws查询接口
 * @version 1.0
 * @date 2010-8-13 下午03:47:31
 */
public interface IPrizeWebService {

	/**
	 * 
	 * @Description 查询本客户端下的账号的兑换记录
	 * @param account
	 * @return
	 */
	List<PrizeLog> queryByAccount(String account);
	
	/**
	 * 
	 * @Description 查询本客户端下指定时间段内的的兑换记录
	 * @param start
	 * @param end
	 * @return
	 */
	List<PrizeLog> queryByRequestTime(Date start, Date end);
	
	/**
	 * 
	 * @Description 根据流水号查询兑换记录
	 * @param gid
	 * @return
	 */
	PrizeLog queryByGid(String gid);
}
