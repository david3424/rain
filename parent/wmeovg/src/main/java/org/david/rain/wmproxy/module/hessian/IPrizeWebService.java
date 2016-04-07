package org.david.rain.wmproxy.module.hessian;

import org.david.rain.wmeovg.request.entity.PrizeLog;

import java.util.Date;
import java.util.List;


/**
 * @version 1.0
 * @ClassName IPrizeService
 * @date 2010-8-13 下午03:47:31
 */
public interface IPrizeWebService {

    /**
     * @param account
     * @return
     * @Description 查询本客户端下的账号的兑换记录
     */
    List<PrizeLog> queryByAccount(String account);

    /**
     * @param start
     * @param end
     * @return
     * @Description 查询本客户端下指定时间段内的的兑换记录
     */
    List<PrizeLog> queryByRequestTime(Date start, Date end);

    /**
     * @param gid
     * @return
     * @Description 根据流水号查询兑换记录
     */
    PrizeLog queryByGid(String gid);

    /**
     * 兑换方法
     *
     * @param appid    所属应用标识
     * @param gid      流水号
     * @param zoneid   游戏服务器ID
     * @param account  通行证账号
     * @param rolename 游戏角色名
     * @param count    发送数目
     * @param priority 发送优先级
     * @param callback 客户端请求附加信息
     * @param sign     签名
     * @throws Exception
     */
    public int send(String cid, String appid, String gid, Integer zoneid, String account,
                    String rolename, Integer prizeid, Integer count, Byte priority,
                    String callback, String sign, String ip);


    public int sendByRoleId(String cid, String appid, String gid, Integer zoneid, String account,
                            Integer roleid, Integer prizeid, Integer count, Byte priority,
                            String callback, String sign, String ip);

    public int sendByMZRoleId(String cid, String appid, String gid, Integer zoneid, String account,
                              Long roleid, Integer prizeid, Integer count, Byte priority,
                              String callback, String sign, String ip);

    // 角色ID + 邮件标题、内容
    public int sendByRoleIdWithTitleAndContent(String cid, String appid, String gid, Integer zoneid, String account,
                                               Long roleid, Integer prizeid, Integer count, Byte priority,
                                               String callback, String sign, String ip, String title, String content);

    /**
     * @param gid
     * @return
     * @Description 物品重发
     * 两种情况：1、发送数目为一条的兑换记录重发  2、发送数目为多条的兑换记录重发
     */
    int resend(String gid) throws Exception;

    public int resendByRoleId(String gid, int roleId) throws Exception;

}
