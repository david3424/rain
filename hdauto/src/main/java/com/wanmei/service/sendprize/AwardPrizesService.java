package com.wanmei.service.sendprize;

import com.wanmei.common.Constant;
import com.wanmei.common.DateUtils;
import com.wanmei.entity.PrizeLogBean;
import com.wanmei.entity.PrizeTableBean;
import com.wanmei.entity.SendProperty;
import com.wanmei.logservice.OperationLog;
import com.wanmei.logservice.OperationLogger;
import com.wanmei.logservice.SendPrizeBean;
import com.wanmei.repository.DaoManager;
import com.wanmei.repository.Idao;
import com.wanmei.wmeovg.request.entity.GoodsLog;
import com.wanmei.wmeovg.request.service.IPrizeService;
import com.wanmei.wmeovg.request.service.PrizeServiceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class AwardPrizesService implements IAwardPrizes {

    OperationLogger operationLogger;

    static final String SEND_CHECK_TABLE = "hd_interface_log";

    /**
     * 包周包月活动发奖的时候需要检查一下该奖品是否是包周产生的，那个数据是在coupon上面，这里直接访问数据库，检查是否有
     */
//    static final Idao couponDao = DaoManager.getDao("coupon");

    private static Logger logger = LoggerFactory.getLogger(AwardPrizesService.class);
    //    idao 需要是单例的吗
    private Idao idao;

    public AwardPrizesService(String dataSource, OperationLogger logger) {
        this.dataSource = dataSource;
        this.idao = DaoManager.getDao(dataSource);
        operationLogger = logger;
    }

    public AwardPrizesService() {

    }

    /**
     * 定时器触发发奖过程
     * status = 0：开始 1:结束  2:锁定  3:错误  -10: 奖品ID异常(停止发放) 5:重复 8：申请成功，等待回调 4：申请失败
     * log存在两条相同记录，停止发放
     *
     * @throws Exception
     */
    @Override
    public void execute() throws Exception {

        if (this.paramsIsLegal() && jobIsOpened(tableName)) {
            logger.info("begin send tablename :" + tableName);
            List<Hashtable<String, String>> list = this.getRecordsByCount();
            logger.info(" new send prize-------------" + tableName + "===new----------list.size=" + list.size());
            for (Hashtable<String, String> hashtable : list) {
                Thread.sleep(100);

                String gid = hashtable.get("gid");
                String _UserName = hashtable.get("username");      //用户名
                String _Server = hashtable.get("server");        //服务器
                String _RoleId = hashtable.get("roleid");      //角色名
                String _PrizeID = hashtable.get("prize");       //奖品号
                String _ID = hashtable.get("id");               //主键id
                int id = Integer.parseInt(_ID);
                int server = Integer.parseInt(_Server);
                int prizeid = Integer.parseInt(_PrizeID);
                Long roleid = Long.parseLong(_RoleId);


                Long useridForLog = 0L;

                logger.info(hashtable.toString());

                int re = updateGIdAndStatus(id, 2, gid);
                if (re == 0) {
                    continue;
                }
                String callback = tableName + "&" + _ID + "&ROLEID&" + this.dataSource;
                int flag = -10;
                if (!prizeCanSend(tableName, id)) {
                    logger.warn(" prize can not be sent because we sent id before!");
                    continue;
                }

                try {
                    IPrizeService prizeService = null;
                    if (sendInterface.equals(SendProperty.SendInterface.MAIN_LAND.value)) {
                        prizeService = PrizeServiceManager.prizeService;
                    } else {
                        prizeService = PrizeServiceManager.abroadPrizeService;
                    }
                    if (!this.ifCoupon) {
                        callback = callback + "&NOCOUPON";
                        if (!this.ifMz) {//非梦诛
                            flag = prizeService.sendByRoleId(tableName, gid, server, _UserName, roleid.intValue(), prizeid, callback);
                        } else {
                            flag = 1;
                            flag = prizeService.sendByMZRoleId(tableName, gid, server, _UserName, roleid, prizeid, callback);
                        }

                    } else {

                        GoodsLog goodsLog = new GoodsLog();


                        Long orderid = Long.valueOf(hashtable.get("orderid"));
                        Integer userid = Integer.valueOf(hashtable.get("userid"));
                        Integer score = Integer.valueOf(hashtable.get("score"));
                        useridForLog = new Long(userid);

                        goodsLog.setAppid(tableName);
                        goodsLog.setPriority(new Byte((byte) 1));
                        goodsLog.setCount(1);
                        goodsLog.setZoneid(server);
                        goodsLog.setUserid(userid);
                        goodsLog.setRoleid(roleid);
                        goodsLog.setPrizeid(prizeid);
                        goodsLog.setAttachMoney(0);
                        goodsLog.setGoodsFlag(1);
                        goodsLog.setGoodsPrice(score);
                        goodsLog.setGoodsPriceBeforeDiscount(0);
                        goodsLog.setReserved2(0);
                        goodsLog.setGid(gid);
                        goodsLog.setCouponOrderid(orderid);

                        flag = 3;


                        goodsLog.setCallback(callback);


                        logger.info(goodsLog.toString());
                        flag = prizeService.send(goodsLog);
                    }
                    if (0 == flag) {
                        // 申请成功
                        updateStatus(id, 8);
                    } else {
                        // 申请失败
                        updateStatus(id, 4);
                    }
                    addLog(_UserName, server, roleid, prizeid, flag, id, 0, gid);
                } catch (Exception e) {
                    e.printStackTrace();
                    addLog(_UserName, server, roleid, prizeid, flag, id, 0, gid);
                }

                sendLog(_UserName, useridForLog, server, roleid, prizeid, flag, gid);
            }
        } else {
            logger.info("-----------参数不正确或者活动已关闭--------");
        }
    }

    private void sendLog(String username, Long userid, Integer server, Long roleid, Integer prize, int result, String gid) {
        SendPrizeBean prizeBean = new SendPrizeBean();
        prizeBean.setUsername(username);
        prizeBean.setUserid(userid);
        prizeBean.setServer(server);
        prizeBean.setRoleid(roleid);
        prizeBean.setPrize(prize);
        prizeBean.setGid(gid);
        prizeBean.setStatus(0);//我们是查到0才发的，所以这里写0，这里没有绝对的事务和锁，哎
        Integer resultStatus = 0;
        if (result == 0) {
            resultStatus = 1;
        }
        operationLogger.log(OperationLog.HandleType.SEND_PRIZE.value, sendProperty, prizeBean, resultStatus, result);
    }


    /**
     * 验证初始化参数是否合法
     * 参数：tablename date
     *
     * @return boolean
     */
    private boolean paramsIsLegal() {
        // 如果表名没有设置
        if (null == this.getTableName() || 0 == this.getTableName().trim().length()) {
            logger.error("-------------------------------------------发奖 没有设置表名.");
            return false;
        }
        // 如果存在时间限制但时间限制不合法
        if (this.isDateLimitExisted() && 0 != validateTime(this.getBeginDate(), this.getEndDate())) {
            logger.error("----------------表" + tableName + "------------------------发奖 时间限制.");
            return false;
        }
        return true;
    }

    /**
     * 验证发奖起止时间是否合法 一般不用
     * 返回： 0 :合法
     */
    private static int validateTime(String starttime, String endtime) {
        if (null == starttime || 0 == starttime.trim().length() || null != endtime && 0 != endtime.trim().length())
            return -2; // 时间设定错误
        java.text.DateFormat sf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowtime = DateUtils.getCurrentFormatDateTime();
        try {
            java.util.Date star = sf.parse(starttime);
            java.util.Date end = sf.parse(endtime);
            java.util.Date now = sf.parse(nowtime);
            if (now.getTime() < star.getTime()) {
                return -1;//没开始
            } else if (now.getTime() > end.getTime()) {
                return 1; //领奖时间已过
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 判断是否开启发奖程序( 1: 开启  0:关闭)
     *
     * @param tbname
     * @return boolean
     */
    private boolean jobIsOpened(String tbname) throws Exception {
        /*PrizeOpenBean prizeOpenBean = idao.queryObject(PrizeOpenBean.class, "select open  from " + Constant.TABLE_SEND_PRIZE_OPEN + " where  tbname=?", tbname);
        if (prizeOpenBean == null) {
//            logger.error("---------表名不存在----------");
            throw new ServiceException("---------------" + tbname + "表名不存在！---------------");
        }
        return prizeOpenBean.getOpen() == 1;*/
        /*暂时不处理开关事宜*/
        return true;
    }

    /**
     * 验证奖品是否可以发放
     * 如果奖品序号不存在于指定的序号内，则将发奖记录置状态 -10，将发奖活动的开启状态置为0，并返回false
     * 如果log中已有成功发送的记录，状态标记为5，返回false
     *
     * @param id
     * @return boolean   false：不可发放 true：可以发放
     */
    private boolean prizeCanSend(String tablename, int id) throws Exception {

        String sql;
        boolean flag = false;
        sql = " select count(1) from " + Constant.TABLE_GAME_PRIZE_LOGS_NEW + " where flag = 1 and pid = ? and tablename= ? and status = 0";
        if (idao.queryCount(sql, id, tablename) > 0) {
            updateStatus(id, 5);
            return false;
        } else {
            flag = idao.queryCount("select count(1) from " + tablename + " where status=2 and id = ? ", id) > 0;
        }
        return flag;
    }

   /* private boolean sendCheck(String tablename, int id) throws Exception {
        String sql = "select runningnumber from " + tablename + " where id = ?";
        String runningNum = idao.queryScalar(sql, id);
        if (runningNum == null) {
            return false;
        }

        String sql2 = "select count(1) from " + SEND_CHECK_TABLE + " where runningnumber = ?";
        Long count = couponDao.queryScalar(sql2, runningNum);
        return count > 0;
    }*/

    /**
     * 获取未发奖的count条数据并将状态改为2（锁定）
     *
     * @return
     * @throws Exception
     */
    private List<Hashtable<String, String>> getRecordsByCount() throws Exception {

        logger.info("tablename:[" + this.tableName + "] begin get to send");
        IPrizeService prizeService = null;
        if (sendInterface.equals(SendProperty.SendInterface.ABROAD_HK.value)) {
            prizeService = PrizeServiceManager.abroadPrizeService;
        } else {
            prizeService = PrizeServiceManager.prizeService;
        }
        String sql = "select * from " + this.getTableName().trim() + " where status = 0 and (gid is null or gid = '')  limit " + this.getCounts();

        List<Hashtable<String, String>> list = new ArrayList<Hashtable<String, String>>();
        Hashtable<String, String> hashtable;
        List<PrizeTableBean> p_list = new ArrayList<PrizeTableBean>();
        try {
            p_list = idao.queryObjects(PrizeTableBean.class, sql);
        } catch (Exception e) {
            logger.error("dataSource:[" + this.dataSource + "] " + e.getMessage());
            e.getMessage();
        }
        for (PrizeTableBean prizeTableBean : p_list) {
            hashtable = new Hashtable<String, String>();
            hashtable.put("username", prizeTableBean.getUsername());
            hashtable.put("roleid", prizeTableBean.getRoleid());
            hashtable.put("prize", prizeTableBean.getPrize() + "");

            hashtable.put("id", prizeTableBean.getId() + "");
            hashtable.put("server", prizeTableBean.getServer() + "");
            String gid = prizeService.genGid();
            hashtable.put("gid", gid);
            if (isIfCoupon()) {
                hashtable.put("orderid", String.valueOf(prizeTableBean.getOrderid()));
                hashtable.put("userid", String.valueOf(prizeTableBean.getUserid()));
                hashtable.put("score", String.valueOf(prizeTableBean.getScore()));
                hashtable.put("prizename", prizeTableBean.getPrizename() == null ? "" : prizeTableBean.getPrizename()); //新多个奖品的团购要求有奖品名
                hashtable.put("num", prizeTableBean.getNum() == null ? "1" : prizeTableBean.getNum().toString()); //新团购有奖品个数
                Integer oldprice = prizeTableBean.getOldprice();

                if (oldprice == null && sendProperty != null && sendProperty.getSend_type() == SendProperty.SendType.COUPON_MULTI.value) {
                    logger.error("table: " + this.getTableName() + " old price can not be null");
                    throw new IllegalArgumentException("old price can not be null");

                }
                hashtable.put("oldprice", oldprice == null ? "0" : oldprice.toString());
            }
            list.add(hashtable);
        }
        return list;
    }

    /**
     * 修改数据库数据
     *
     * @param id     当前发奖记录id
     * @param status 当前发奖记录状态
     * @throws Exception
     */
    @Override
    public void updateStatus(int id, int status) throws Exception {

        String sql = "update  " + tableName + " set status = ? where id=? ";
        idao.update(sql, status, id);
    }


    public void updateStatus(int id, int status, String time) throws Exception {
        String sql = "update " + tableName + " set status = ?, sendtime = ? where id = ?";
        idao.update(sql, status, time, id);
    }

    /**
     * 修改失败数据与状态
     *
     * @param id
     * @param status
     * @param rid
     * @throws Exception
     */
    public void updateRidAndStatus(int id, int status, int rid) throws Exception {

        String sql = "update  " + tableName + " set status = ? ,rid = ? where id=? ";
        idao.update(sql, status, rid, id);
    }

    /**
     * 修改数据库数据
     *
     * @param id     当前发奖记录id
     * @param status 当前发奖记录状态
     * @throws Exception
     */
    @Override
    public int updateGIdAndStatus(int id, int status, String gid) throws Exception {

        String sql = "update  " + tableName + " set status = ? , gid= ? where id = ? and status = 0";
        return idao.update(sql, status, gid, id);
    }

    /**
     * 插入发奖日志
     *
     * @param username
     * @param server
     * @param roleid
     * @param prize
     * @param status
     * @param flag     区分是请求时插入（0）还是回调后插入（1）
     */
    @Override
    public void addLog(String username, int server, Long roleid, int prize, int status, int pid, int flag, String gid) throws SQLException {
//        String sql = "insert into " + Constant.TABLE_GAME_PRIZE_LOGS_NEW + "(username,server,roleid,prize,status,createdate,tablename,pid,flag,gid) values(?,?,?,?,?,now(),?,?,?,?)";
        PrizeLogBean prizeLogBean = new PrizeLogBean();
        prizeLogBean.setUsername(username);
        prizeLogBean.setServer(server);
        prizeLogBean.setRoleid(roleid);
        prizeLogBean.setPrize(prize);
        prizeLogBean.setStatus(status);
        prizeLogBean.setPid(pid);
        prizeLogBean.setFlag(flag);
        prizeLogBean.setCreatedate(DateUtils.getCurrentDate());
        prizeLogBean.setGid(gid);
        prizeLogBean.setTablename(tableName);
        idao.insert(prizeLogBean);
    }

    /* private void executeUpdateSql(String sql, String dblink) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBManager.getHuodongConnection(dblink);
            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            DBManager.close(rs, pstmt, conn);
        }
    }*/

    @Override
    public String getTableName() {
        return tableName;
    }

    @Override
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public int getCounts() {
        return counts;
    }

    @Override
    public void setCounts(int counts) {
        this.counts = counts;
    }

    @Override
    public String getBeginDate() {
        return beginDate;
    }

    @Override
    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    @Override
    public String getEndDate() {
        return endDate;
    }

    @Override
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean isDateLimitExisted() {
        return dateLimitExisted;
    }

    @Override
    public void setDateLimitExisted(boolean dateLimitExisted) {
        this.dateLimitExisted = dateLimitExisted;
    }

    @Override
    public boolean isIfMz() {
        return ifMz;
    }

    @Override
    public void setIfMz(boolean ifMz) {
        this.ifMz = ifMz;
    }

    @Override
    public boolean isIfCoupon() {
        return ifCoupon;
    }

    @Override
    public void setIfCoupon(boolean ifCoupon) {
        this.ifCoupon = ifCoupon;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    private String tableName;           //表名
    private String dataSource;           //表名
    private int counts = 150;           //执行数据量
    private boolean ifMz = false;           //是否是梦诛或者是神魔
    private boolean ifCoupon = false;           //是否是梦诛或者是神魔
    private String beginDate;             //开始时间
    private String endDate;               //结束时间
    private boolean dateLimitExisted;       //是否存在日期限制

    private Integer sendInterface;


    public Integer getSendInterface() {
        return sendInterface;
    }

    public void setSendInterface(Integer sendInterface) {
        this.sendInterface = sendInterface;
    }


    private SendProperty sendProperty;

    //    public final static String Constant.TABLE_SEND_PRIZE_OPEN = "hd_sendprize_open";
    public final static String ADD_AWARD_PASSWD = "12!@";

    @Override
    public PrizeTableBean gainPrizeBean(String tbName, int id) throws SQLException {

        return idao.queryObject(PrizeTableBean.class, "select * from " + tbName + " where id = ?", id);
    }

    public SendProperty getSendProperty() {
        return sendProperty;
    }

    public void setSendProperty(SendProperty sendProperty) {
        this.sendProperty = sendProperty;
    }
}