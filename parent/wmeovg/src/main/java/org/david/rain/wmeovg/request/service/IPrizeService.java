package org.david.rain.wmeovg.request.service;

import org.david.rain.wmeovg.request.entity.PrizeLog;
import org.david.rain.wmeovg.request.util.Priority;

import java.util.Date;
import java.util.List;


/**
 * @ClassName IPrizeService
 * @Description 虚拟物品兑换客户端接口
 * @version 1.0
 * @date 2010-8-13 下午02:50:10
 */
public interface IPrizeService {

	/**
	 * 
	 * @Description 流水号生成
	 * @return
	 */
	public String genGid();
	
	/**
	 * 带部分默认值的兑换方法（发送数目默认为1个，发送优先级为普通优先级）
	 * @param gid  流水号
	 * @param zoneid 游戏服务器ID
	 * @param account 通行证账号
	 * @param rolename 游戏角色名
	 * @param roleid 角色ID
	 * @param prizeid 奖品AU编号
	 * @param callback 客户端请求附加信息
	 * @throws Exception 
	 */
	int send(String gid, Integer zoneid, String account, String rolename, Long roleid,
             Integer prizeid, String callback);
	
	/**
	 * 兑换方法
	 * @param gid 流水号
	 * @param zoneid 游戏服务器ID
	 * @param account 通行证账号
	 * @param rolename 游戏角色名
	 * @param roleid 角色ID
	 * @param prizeid 奖品AU编号
	 * @param count 发送数目
	 * @param priority 发送优先级
	 * @param callback 客户端请求附加信息
	 * @throws Exception
	 */
	int send(String gid, Integer zoneid, String account, String rolename, Long roleid,
             Integer prizeid, Integer count, Priority priority, String callback);
	
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
