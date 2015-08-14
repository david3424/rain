package org.david.rain.web.service.hdinterface;


import org.david.rain.common.lang.CommonList;
import org.david.rain.common.search.Search;
import org.david.rain.web.service.hdinterface.entity.HdIscoreLog;
import org.david.rain.web.service.hdinterface.entity.TopBean;
import org.david.rain.web.service.hdinterface.entity.UserBean;

import java.util.List;

/**
 * Date: 12-7-13
 * Time: 下午5:21
 * To change this template use File | Settings | File Templates.
 */
public interface HdIscoreServiceInterface {


    /**
     * 创建流水号
     *
     * @param typeId
     * @return
     */
    public Long createOrderId(Integer typeId);


    /**
     * 积分变更
     *
     * @param username
     * @param score
     * @param tableName
     * @param hdname
     * @return
     * @throws Exception
     */
    public Integer consumeScore(String username, Integer type, Long orderId, Integer score, String tableName, String hdname) throws Exception;


    /***********************************************Z商城********************************************************/
    /**
     * Z点积分变更
     *
     * @param username  账号
     * @param type      加还是减
     * @param orderId   流水号 唯一
     * @param score     正或者负
     * @param tableName 活动表名
     * @return 返回状态
     * @throws Exception
     */
    public Integer consumeZScore(String username, Integer type, Long orderId, Integer score, String tableName, String ip) throws Exception;


    /**
     *
     * @param username 账号
     * @return >0
     */
    public Integer getUserZScore(String username);

    /**
     * 查询玩家消费记录
     *
     * @return
     */
    public CommonList getTotalZLogByUser(Integer pageNo, Integer pageSize, String username);


    /**
     * 分页总数量
     *
     * @return
     */
    public Integer getRecNumOfPage(Search search);

    /**
     * 查询玩家积分榜信息  hd_z_iscore_user
     * @param search
     * @return
     */
    public CommonList getTotalZscoreByUser(Search search);

    /**
        * 分页总数量
        *
        * @return
        */
       public Integer getRecZscoreNumOfPage(Search search);
    /**
     * 更新用户个人信息
     *
     * @param bean
     * @return
     */
    public int  updateZUser(UserBean bean);


    /**
     * 检查用户积分是否正常
     *
     * @param username
     * @return
     */
    public boolean CheckZData(String username);

/***********************************************Z商城********************************************************/
    /**
     * 获取玩家剩余积分数
     *
     * @param username
     * @return
     */
    public Integer getUserScore(String username, String hdName);


    /**
     * 获取玩家bean
     *
     * @param username
     * @return
     */
    public UserBean getUserBean(String username);

    /**
     * 获取玩家总增加积分数
     *
     * @param username
     * @param tableName
     * @return
     */
    public Integer getTotalIncomeScore(String username, String tableName);

    /**
     * 获取玩家总消费积分数
     *
     * @param username
     * @param tableName
     * @return
     */
    public Integer getTotalConsumeScore(String username, String tableName);

    /**
     * 查询玩家消费记录
     *
     * @param starttime
     * @param endtime
     * @return
     */
    public List<HdIscoreLog> getTotalLogByUser(String username, String starttime, String endtime, String tableName);


    /**
     * 查询玩家消费记录
     *
     * @param orderId
     * @return
     */
    public HdIscoreLog getLogByOrderId(Long orderId);


    /**
     * 检测是否数据正常
     *
     * @param username
     * @param score
     * @param tableName
     * @return
     */
    public boolean CheckData(String username, Integer score, String tableName);

    public List<TopBean> gainTopListByType(String username, Integer type, String day);

    public boolean saveScoreEveryDay() throws Exception;
}
