package org.david.rain.web.util.hdswitch;

import org.david.rain.common.repository.Idao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

/**
 */
@Component
public class TestAccountService {
    public final static String TABLE_WM_TEST_ACCOUNT = "wm_huodong_testaccount";
    @Autowired
    @Qualifier("daoImp")
    private Idao readDao;

    @Cacheable(value = "cache3min", key = "'event_testaccount'")
    public List<TestAccountBean> getTestAccounts() throws SQLException {
        String sql = "select * from " + TABLE_WM_TEST_ACCOUNT;
        return readDao.queryObjects(TestAccountBean.class, sql);
    }

    public int isTestAccount(String username, List<TestAccountBean> testAccounts) {
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

    public boolean isTestAccount(String username) throws SQLException {
        List<TestAccountBean> testAccountBeanList = getTestAccounts();
        return isTestAccount(username, testAccountBeanList) == 1;
    }

}
