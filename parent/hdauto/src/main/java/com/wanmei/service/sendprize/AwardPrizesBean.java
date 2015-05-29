package com.david.web.wanmei.service.sendprize;

import com.david.web.wanmei.common.Constant;
import com.david.web.wanmei.common.DateUtils;
import com.david.web.wanmei.entity.PrizeLogBean;
import com.david.web.wanmei.entity.PrizeOpenBean;
import com.david.web.wanmei.entity.PrizeTableBean;
import com.david.web.wanmei.repository.Idao;
//import com.david.web.wanmei.wmeovg.request.service.IPrizeService;
//import com.david.web.wanmei.wmeovg.request.service.PrizeServiceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

@Service
public class AwardPrizesBean implements IAwardPrizes {

    private static Logger logger = LoggerFactory.getLogger(AwardPrizesBean.class);
    private Idao idao;

    public Idao getIdao() {
        return idao;
    }

    @Autowired
    public void setIdao(@Qualifier("prizeDaoImp") Idao idao) {
        this.idao = idao;
    }

    /**
     * 定时器触发发奖过程
     * status = 0：开始 1:结束  2:锁定  3:错误  -10: 奖品ID异常(停止发放) 5:重复 8：申请成功，等待回调 4：申请失败
     * log存在两条相同记录，停止发放
     *
     * @throws Exception
     */
    public void execute() throws Exception {

        // 参数合法且发奖程序开启
        if (this.paramsIsLegal() && jobIsOpened(tableName)) {
            //获取待发数据
            List<Hashtable<String, String>> list = this.getRecordsByCount();

            //  System.out.println(" new send prize---------------------------------" + tableName + "===new---------------------------------list.size=" + list.size() );

            for (Hashtable<String, String> hashtable : list) {
                String _UserName = hashtable.get("username");      //用户名
                String _Server = hashtable.get("server");        //服务器
                String _RoleId = hashtable.get("roleid");      //角色名
                String _PrizeID = hashtable.get("prize");       //奖品号
                String _ID = hashtable.get("id");
                int id = Integer.parseInt(_ID);
                int server = Integer.parseInt(_Server);
                int prizeid = Integer.parseInt(_PrizeID);
                Long roleid = Long.parseLong(_RoleId);
                String callback = tableName + "&" + _ID + "&ROLEID&活动开发项目";
                //System.out.println("表" + tableName + " callback is -------------------" + callback);
                //System.out.println("表" + tableName + " 当前发奖id==" + id);
                int flag = -10;
                // 如果奖品可以发放
                if (prizeCanSend(tableName, id)) {
//					System.out.println("表" + tableName + "  准备发奖id---------------------------------" + id);
                    String gid = hashtable.get("gid");
                    try {
                      /*  IPrizeService prizeService = PrizeServiceManager.prizeService;
                        if (!this.ifCoupon) {
                            if (!this.ifMz) {//非梦诛
                                flag = prizeService.sendByRoleId(tableName, gid, server, _UserName, roleid.intValue(), prizeid, callback);
                            } else {
                                flag = prizeService.sendByMZRoleId(tableName, gid, server, _UserName, roleid, prizeid, callback);
                            }
                        } else {
                        }
                        // System.out.println("申请结果：---------------------------------" + flag);*/
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
                }
            }
        }
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
            System.out.println("-------------------------------------------发奖 没有设置表名.");
            logger.error("-------------------------------------------发奖 没有设置表名.");
            return false;
        }
        // 如果存在时间限制但时间限制不合法
        if (this.isDateLimitExisted() && 0 != validateTime(this.getBeginDate(), this.getEndDate())) {
            System.out.println("----------------表" + tableName + "------------------------发奖 时间限制.");
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
    private boolean jobIsOpened(String tbname) throws SQLException {
        PrizeOpenBean prizeOpenBean = idao.queryObject(PrizeOpenBean.class, "select open  from " + Constant.TABLE_SEND_PRIZE_OPEN + " where  tbname=?", tbname);
        return prizeOpenBean != null && prizeOpenBean.getOpen() == 1;
        /*try {
            sql = " select open  from " + Constant.TABLE_SEND_PRIZE_OPEN + " where  tbname=?";
            conn = DBManager.getHuodongConnection("huodong218");
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, tbname);
            rs = pstmt.executeQuery();
            flag = (rs != null && rs.next() && 1 == rs.getInt(1));
            //System.out.println("表" + tableName + " 打开情况：" + flag);

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            DBManager.close(rs, pstmt, conn);
        }*/
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

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql;
        boolean flag = false;

        String prizelist = null;
        PrizeOpenBean prizeOpenBean = idao.queryObject(PrizeOpenBean.class, "select open  from " + Constant.TABLE_SEND_PRIZE_OPEN + " where  tbname=?", tablename);
        if (prizeOpenBean != null && prizeOpenBean.getOpen() == 1) {
            prizelist = prizeOpenBean.getPrizelist();
            sql = " select count(1) from " + tablename + " where prize in (?) and id= ? ";
            Long result = idao.queryCount(sql, prizelist, id);
            if (result <= 0) {
                sql = "update " + tablename + " set status =-10 where id= ? ";
                idao.update(sql, id);
            } else {
                sql = " select count(1) from " + Constant.TABLE_GAME_PRIZE_LOGS_NEW + " where flag = 1 and pid = ? and tablename= ? and status = 0";
                if (idao.queryCount(sql, id, tablename) > 0) {
                    updateStatus(id, 5);
                } else {
                    flag = idao.queryCount("select count(1) from " + tablename + "where status=2 and id = ? ", id) > 0;
                }
            }
        }
        return flag;
    }

    /**
     * 获取未发奖的count条数据并将状态改为2（锁定）
     *
     * @return
     * @throws Exception
     */
    private List<Hashtable<String, String>> getRecordsByCount() throws Exception {
//        IPrizeService prizeService = PrizeServiceManager.prizeService;
        String sql = "select * from " + this.getTableName().trim() + " where status = 0   limit " + this.getCounts();
        //  System.out.println("-------------------------sql demo s-----------------------------------"+sql);
        List<Hashtable<String, String>> list = new ArrayList<Hashtable<String, String>>();
        Hashtable<String, String> hashtable;
        List<PrizeTableBean> p_list = idao.queryObjects(PrizeTableBean.class, sql);
        for (PrizeTableBean prizeTableBean : p_list) {
            hashtable = new Hashtable<String, String>();
            hashtable.put("username", prizeTableBean.getUsername());
            hashtable.put("roleid", prizeTableBean.getRoleid());
            hashtable.put("prize", prizeTableBean.getPrize() + "");
            hashtable.put("id", prizeTableBean.getId() + "");
            hashtable.put("server", prizeTableBean.getServer() + "");
//            String gid = prizeService.genGid();
//            hashtable.put("gid", gid);
//            updateGIdAndStatus(prizeTableBean.getId(), 2, gid);
            list.add(hashtable);
        }
        /*try {
            conn = DBManager.getHuodongConnection("huodong218");
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs != null && rs.next()) {
                hashtable = new Hashtable<String, String>();
                hashtable.put("id", rs.getInt("id") + "");
                hashtable.put("username", rs.getString("username"));
                hashtable.put("roleid", rs.getString("roleid"));//用角色id来发奖
                hashtable.put("prize", rs.getInt("prize") + "");
                hashtable.put("server", rs.getInt("server") + "");
                String gid = prizeService.genGid();
                hashtable.put("gid", gid);
                /*//*******************************//*添加coupon限制
                if (ifCoupon) {
                    hashtable.put("userid", rs.getInt("userid") + "");
                    hashtable.put("score", rs.getInt("score") + "");
                    hashtable.put("orderid", rs.getLong("orderid") + "");
                    hashtable.put("rolename", rs.getString("rolename"));
                }
                // 客户端生成流水号
                updateGIdAndStatus(rs.getInt("id"), 2, gid);
                list.add(hashtable);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            DBManager.close(rs, pstmt, conn);
        }*/
        return list;
    }

    /**
     * 修改数据库数据
     *
     * @param id     当前发奖记录id
     * @param status 当前发奖记录状态
     * @throws Exception
     */
    public void updateStatus(int id, int status) throws Exception {

        String sql = "update  " + tableName + " set status = ? where id=? ";
        idao.update(sql, status, id);
    }

    /**
     * 修改数据库数据
     *
     * @param id     当前发奖记录id
     * @param status 当前发奖记录状态
     * @throws Exception
     */
    public int updateGIdAndStatus(int id, int status, String gid) throws Exception {

        String sql = "update  " + tableName + " set status = ? , gid= ? where id = ? and status = 0";
        return idao.update(sql, status, gid, id);
    }

    /**
     * 修改失败数据与状态
     * @param id
     * @param status
     * @param rid
     * @throws Exception
     */
    public void updateRidAndStatus(int id, int status,int rid) throws Exception {

        String sql = "update  " + tableName + " set status = ? ,rid = ? where id=? ";
        idao.update(sql, status,rid, id);
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

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public int getCounts() {
        return counts;
    }

    public void setCounts(int counts) {
        this.counts = counts;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public boolean isDateLimitExisted() {
        return dateLimitExisted;
    }

    public void setDateLimitExisted(boolean dateLimitExisted) {
        this.dateLimitExisted = dateLimitExisted;
    }

    public boolean isIfMz() {
        return ifMz;
    }

    public void setIfMz(boolean ifMz) {
        this.ifMz = ifMz;
    }

    public boolean isIfCoupon() {
        return ifCoupon;
    }

    public void setIfCoupon(boolean ifCoupon) {
        this.ifCoupon = ifCoupon;
    }

    private String tableName;           //表名
    private int counts = 100;           //执行数据量
    private boolean ifMz = false;           //是否是梦诛或者是神魔
    private boolean ifCoupon = false;           //是否是梦诛或者是神魔
    private String beginDate;             //开始时间
    private String endDate;               //结束时间
    private boolean dateLimitExisted;       //是否存在日期限制

    //    public final static String Constant.TABLE_SEND_PRIZE_OPEN = "hd_sendprize_open";
    public final static String ADD_AWARD_PASSWD = "12!@";

    public PrizeTableBean gainPrizeBean(String tbName, int id) throws SQLException {

        return idao.queryObject(PrizeTableBean.class, "select * from " + tbName + " where id = ?", id);
    }
}