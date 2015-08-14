package org.david.rain.web.service.hdinterface.testimpl;

import org.apache.commons.lang.StringUtils;
import org.david.rain.common.components.util.ActionUtil;
import org.david.rain.common.lang.CommonList;
import org.david.rain.common.repository.Idao;
import org.david.rain.common.search.Search;
import org.david.rain.common.util.DateUtils;
import org.david.rain.web.service.hdinterface.HdIscoreServiceInterface;
import org.david.rain.web.service.hdinterface.entity.HdIscoreLog;
import org.david.rain.web.service.hdinterface.entity.ScoreInfoBean;
import org.david.rain.web.service.hdinterface.entity.TopBean;
import org.david.rain.web.service.hdinterface.entity.UserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Random;


/**
 */
@Component
public class HdIscoreServiceInterfaceImpl implements HdIscoreServiceInterface {

    private static SimpleDateFormat sdfId = new SimpleDateFormat("yyMMddHHmmssSSS");
    //    private static final String BSAEDIR = ConfigUtil.defaultHomePath() + File.separator + "upload";
    private static final String SCOREDIR = "score";
    private static final String TOTALSCOREDIR = "totalscore";
    private static final String COSUMESCOREDIR = "consumeScore";
    private static final Integer TOPNUMBER = 20;


    private static final String TABLE_WEBMALL_USER = "hd_webmall_interface_user";
    private static final String TABLE_WEBMALL_USERSCORE = "hd_webmall_interface_score";
    private static final String TABLE_WEBMALL_USERSCORE_LOG = "hd_webmall_interface_score_log";
    private static final String TABLE_WEBMALL_FAIL_LOG = "hd_webmall_interface_fail_log";

//    @Autowired
//    @Qualifier("logUtil")
//    private LogUtil logUtil;


    @Autowired
    @Qualifier("writeDaoImp")
    private Idao dao;

    @Autowired
    @Qualifier("daoImp")
    private Idao readDao;

    @Override
    public Long createOrderId(Integer typeId) {
        StringBuffer sb = new StringBuffer(typeId).append(sdfId.format(System.currentTimeMillis())).append(new Random().nextInt(8999) + 1000);
        return Long.parseLong(sb.toString());
    }

    @Override
    public Integer consumeScore(String username, Integer type, Long orderId, Integer score, String tableName, String hdname) throws Exception {
        return null;
    }

    @Override
    public Integer consumeZScore(String username, Integer type, Long orderId, Integer score, String tableName, String ip) throws Exception {
        if (type * score <= 0 || StringUtils.isEmpty(username) || orderId <= 0 || StringUtils.isEmpty(tableName)) {
            return -1; //参数异常
        }
        UserBean hdIscoreUser = getUserBean(username);
        if (hdIscoreUser == null) {
            return -6; //系统错误
        }

        if (hdIscoreUser.getTotalscore() + hdIscoreUser.getUsedscore() + score < 0) {
            return -7;//数据异常 积分为负
        }
        String sql = "select sum(score) from " + TABLE_WEBMALL_USERSCORE_LOG + " where username=?";
        BigDecimal big = dao.queryScalar(sql, username);
        if (big!=null && ((hdIscoreUser.getTotalscore()  - hdIscoreUser.getUsedscore() != 0 )|| big.intValue() != (hdIscoreUser.getTotalscore() - hdIscoreUser.getUsedscore())) ) {
            return -8;//数据异常 积分为负
        }
        ScoreInfoBean hdIscoreLog = new ScoreInfoBean();
        hdIscoreLog.setCreatetime(DateUtils.getCurrentFormatDateTime());
        hdIscoreLog.setCreatedate(DateUtils.getCurrentFormatDate());
        hdIscoreLog.setHdname(tableName);
        hdIscoreLog.setOrderid(orderId);
        hdIscoreLog.setType(type);
        hdIscoreLog.setUsername(username);
        hdIscoreLog.setScore(score);
        hdIscoreLog.setIp(ip);
        hdIscoreLog.setStatus(0);
        try {
            dao.insert(hdIscoreLog);
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -5; //系统错误，保存失败
        }
    }

    @Override
    public Integer getUserZScore(String username) {
        return null;
    }

    @Override
    public CommonList getTotalZLogByUser(Integer pageNo, Integer pageSize,  String username) {
        try {
            Search search = new Search();
            search.addSelectSql("SELECT * FROM " + TABLE_WEBMALL_USERSCORE_LOG);
            search.addSelectCountSql("SELECT count(1) FROM " + TABLE_WEBMALL_USERSCORE_LOG);
            search.addWhere(Search.SEARCH_AND, " username=? ", username);
            search.addWhere(Search.SEARCH_AND, " score>0 ");
            search.addWhere(Search.SEARCH_AND, " status != -1 ");
            search.setPageSize(pageSize);
            search.setPageNo(pageNo);
            search.addOrder("createtime", "desc");
            return dao.pagination(search, ScoreInfoBean.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Integer getRecNumOfPage(Search search) {
        return null;
    }

    @Override
    public CommonList getTotalZscoreByUser(Search search) {
        return null;
    }

    @Override
    public Integer getRecZscoreNumOfPage(Search search) {
        return null;
    }

    /**
     * @param bean
     * @return-1 查询玩家信息失败，-2 保存出错，>0 保存成功
     */
    @Override
    public int updateZUser(UserBean bean) {
        try {

            UserBean userBean = getUserBean(bean.getUsername());
            if (userBean == null) {
                return -1;
            }
            int type = (StringUtils.isBlank(userBean.getPhone()) || StringUtils.isBlank(userBean.getCity())) ? 1 : StringUtils.isBlank(userBean.getUpdatetime()) ? 2 : 3;
            if (type == 1) {//首次提交
                String sql = "update " + TABLE_WEBMALL_USER + " set province=?,city=?,country=?,address=?,truename=?,phone=?,email=?,ip=?,updatetime=? where username=?";
                int rtn = dao.update(sql, bean.getProvince(), bean.getCity(), bean.getCountry(), bean.getAddress(), bean.getTruename(),bean.getPhone(), bean.getEmail(), bean.getIp(), bean.getCreatetime(), bean.getUsername());
                if (rtn > 0) {
                    int result = consumeZScore(bean.getUsername(), 1, createOrderId(22), 10, "首次提交个人信息得魔石", bean.getIp());
                    if (result != 0) {
                        sql = "insert into " + TABLE_WEBMALL_FAIL_LOG + "(username,hdname,prizename,score ,createtime,ip,status) values(?,?,?,?,?,?,?)";
                        dao.update(sql, bean.getUsername(), "首次提交个人信息",
                                "", 10, DateUtils.getCurrentFormatDateTime(), ActionUtil.getRealIp(), 0);
                        return -2;
                    }
                    return rtn;
                }else{
                    return rtn;
                }
            } else {
                String sql = "update " + TABLE_WEBMALL_USER + " set province=?,city=?,country=?,address=?,truename=?,email=?,ip=? where username=?";
                int rtn = dao.update(sql, bean.getProvince(), bean.getCity(), bean.getCountry(), bean.getAddress(), bean.getTruename(), bean.getEmail(), bean.getIp(), bean.getUsername());
                return rtn;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -2;
        }

    }

    @Override
    public boolean CheckZData(String username) {
        return false;
    }

    @Override
    public Integer getUserScore(String username, String hdName) {
        return null;
    }

    @Override
    public UserBean getUserBean(String username) {
        UserBean hdIscoreUser;
        try {
            hdIscoreUser = dao.queryObject(UserBean.class, "select * from " + TABLE_WEBMALL_USER + " where username = ? ", username);
            if (hdIscoreUser == null) {
                hdIscoreUser = new UserBean();
                hdIscoreUser.setUsedscore(0);
                hdIscoreUser.setTotalscore(0);
                hdIscoreUser.setUsername(username);
                hdIscoreUser.setStatus(0);
                hdIscoreUser.setCreatetime(DateUtils.getCurrentFormatDateTime());
                dao.insert(hdIscoreUser);
            }
            return hdIscoreUser;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Integer getTotalIncomeScore(String username, String tableName) {
        return null;
    }

    @Override
    public Integer getTotalConsumeScore(String username, String tableName) {
        return null;
    }

    @Override
    public List<HdIscoreLog> getTotalLogByUser(String username, String starttime, String endtime, String tableName) {
        return null;
    }

    @Override
    public HdIscoreLog getLogByOrderId(Long orderId) {
        return null;
    }

    @Override
    public boolean CheckData(String username, Integer score, String tableName) {
        return false;
    }

    @Override
    public List<TopBean> gainTopListByType(String username, Integer type, String day) {
        return null;
    }

    @Override
    public boolean saveScoreEveryDay() throws Exception {
        return false;
    }
}
