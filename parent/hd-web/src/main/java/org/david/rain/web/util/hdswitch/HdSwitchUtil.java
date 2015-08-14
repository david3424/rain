package org.david.rain.web.util.hdswitch;

import org.david.rain.common.components.util.ActionUtil;
import org.david.rain.common.repository.Idao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-3-4
 * Time: 上午10:27
 * To change this template use File | Settings | File Templates.
 */
public class HdSwitchUtil {

    public final static String TABLE_WM_EVENT_OPEN = "wm_huodong_switch";//活动开关表

    public final static String TABLE_WM_TEST_ACCOUNT = "wm_huodong_testaccount";
    public final static Integer SWITCH_NOT_EXISTS = -1;
    public final static Integer SWITCH_IS_CLOSED = 0;
    public final static Integer SWITCH_IS_OPEN = 1;

    public final static Logger logger = LoggerFactory.getLogger(HdSwitchService.class);

    /**
     * @param hdid
     * @param request
     * @param sessionUser
     * @return -1   没有添加开关 -2 错误或其他  0 活动关闭  1 活动已开
     */
    public static Integer getOpen(String hdid, HttpServletRequest request,String sessionUser) {
        int open;
        try {
            WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession().getServletContext());
            Idao dao = (Idao) wac.getBean("writeDaoImp");
            String username= ActionUtil.getUserName();
            open = getStatus(hdid, username, dao);
        } catch (Exception e) {
            e.getMessage();
            open = -2;
        }
        return open;
    }

    /**
     * 开关判断，测试账号可以无视开关，继续访问活动 add by ssw
     *
     * @param tbname
     * @param username
     * @return
     */
    public static int getStatus(String tbname, String username, Idao dao) {
        int rtn = -2;
        try {
            List<TestAccountBean> testAccounts = getTestAccounts(dao);
            if (testAccounts != null && (rtn = isTestAccount(username, testAccounts)) == 1) {
                return rtn;
            }
        } catch (Exception e) {
            return rtn;
        }
        rtn = getStatus(tbname, dao);
        return rtn;
    }

    public static int getStatus(String tbName, Idao dao) {
        String sql = "select open from  " + TABLE_WM_EVENT_OPEN + " where tbname = ?";
        try {
            Integer status = dao.queryScalar(sql, tbName);
            if (status == null) {
                return -1;
            }
            return status;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return -2;
        }
    }

    public static List<TestAccountBean> getTestAccounts(Idao dao) throws SQLException {
        String sql = "select * from " + TABLE_WM_TEST_ACCOUNT;
        return dao.queryObjects(TestAccountBean.class, sql);
    }

    public static int isTestAccount(String username, List<TestAccountBean> testAccounts) {
        if (username == null || "".equals(username))
            return -1;
        for (TestAccountBean accountBean : testAccounts) {
            String account = accountBean.getUserName();
            if (username.equals(account)) {
                return 1;
            }
        }
        return -1;
    }

    public static boolean isTestAccount(String username, Idao dao) throws SQLException {
        List<TestAccountBean> testAccountBeanList = getTestAccounts(dao);
        return isTestAccount(username, testAccountBeanList) == 1;
    }


}
