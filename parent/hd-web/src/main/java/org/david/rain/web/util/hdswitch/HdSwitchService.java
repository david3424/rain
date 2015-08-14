package org.david.rain.web.util.hdswitch;

import org.david.rain.common.repository.Idao;
import org.david.rain.common.util.DateUtils;
import org.david.rain.web.util.CommonMessageConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

/**
 */
@Component
public class HdSwitchService {

    public final static String TABLE_WM_EVENT_OPEN = "wm_huodong_switch";//活动开关表

    public final static Logger logger = LoggerFactory.getLogger(HdSwitchService.class);

    @Autowired
    @Qualifier("daoImp")
    private Idao readDao;

    @Autowired
    @Qualifier("writeDaoImp")
    private Idao writeDao;

    @Autowired
    private TestAccountService testAccountService;


    //包周包月正式服务器测试开关
    public int getMonthlyTest(String tbName) {
        String sql = "select status from " + TABLE_WM_EVENT_OPEN + " where tbname=? and open= ?";
        try {
            Integer status = readDao.queryScalar(sql, tbName, HdSwitchBean.SWITCH_CLOSE_STATUS);
            if (status == null)
                return -99;
            else return status;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return -99;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean open(int id) {
        String sql = "update " + TABLE_WM_EVENT_OPEN + " set open= ? where id=?";
        try {
            Integer recNum = writeDao.update(sql, HdSwitchBean.SWITCH_OPEN_STATUS, id);
            return recNum > 0;
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean close(int id) {
        String sql = "update " + TABLE_WM_EVENT_OPEN + " set open= ? where id=?";
        try {
            Integer recNum = writeDao.update(sql, HdSwitchBean.SWITCH_CLOSE_STATUS, id);
            return recNum > 0;
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public int getStatus(String tbName) {
        String sql = "select open from  " + TABLE_WM_EVENT_OPEN + " where tbname = ?";
        try {
            Integer status = readDao.queryScalar(sql, tbName);
            if (status == null) {
                return -1;
            }
            return status;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }


    /**
     * 开关判断，测试账号可以无视开关，继续访问活动 add by sswd
     *
     * @param tbname
     * @param username
     * @return
     */
    public int getStatus(String tbname, String username) {
        int rtn = -1;
        try {
            List<TestAccountBean> testAccounts = testAccountService.getTestAccounts();
            if (testAccounts != null) {
                rtn = testAccountService.isTestAccount(username, testAccounts);
                if (rtn == 1) {
                    return rtn;
                }
            }
        } catch (Exception e) {
            return rtn;
        }
        rtn = getStatus(tbname);
        return rtn;
    }

    @Cacheable(value = "cache3sec",key="'event_switch_' + #tbName")
    public HdSwitchBean getHdSwitch(String tbName) {
        HdSwitchBean defaultSwitch = new HdSwitchBean();
        defaultSwitch.setBeginTime("3000-01-01 00:00:01");
        defaultSwitch.setEndTime("1980-02-02 00:00:01");
        defaultSwitch.setScoreStart("3000-01-01 00:00:01");
        defaultSwitch.setScoreEnd("1980-02-02 00:00:01");
        defaultSwitch.setTestStart("3000-01-01 00:00:01");
        defaultSwitch.setTestEnd("1980-02-02 00:00:01");
        defaultSwitch.setOpen(HdSwitchBean.SWITCH_CLOSE_STATUS);

        String sql = "select * from " + TABLE_WM_EVENT_OPEN + " where tbname=?";
        try {
            HdSwitchBean hdSwitchBean = readDao.queryObject(HdSwitchBean.class, sql, tbName);
            if (hdSwitchBean == null) {
                hdSwitchBean = defaultSwitch;
            }
            return hdSwitchBean;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return defaultSwitch;
        }
    }

    public int isHdRunning(String tbName,String username){
        HdSwitchBean switchBean=getHdSwitch(tbName);
        String now= DateUtils.getCurrentFormatDateTime();
        if(now.compareTo(switchBean.getBeginTime())<0){
            return  CommonMessageConst.EVENT_NOT_START;
        }else if(now.compareTo(switchBean.getEndTime())>0){
            return  CommonMessageConst.EVENT_OVER;
        }else{
            return getStatus(tbName,username);
        }

    }

}
