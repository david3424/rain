package com.david.web.wanmei.service.sendcheck;

import com.david.web.wanmei.entity.Task;
import com.david.web.wanmei.repository.DaoManager;
import com.david.web.wanmei.repository.Idao;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: czw
 * Date: 13-4-18
 * Time: 下午1:55
 */

@Service
public class CheckService implements ApplicationContextAware {

    ApplicationContext context;

    Idao dao;

    List<String> getIllegalPrizeTableList(String dataSource) throws SQLException {

        Idao dao = DaoManager.getDao(dataSource);

        String tablename = "auto_game_prize_roleid_newlogs";
        String sql = "SELECT tablename, pid,count(1) from( select tb1.tablename, tb1.pid,tb1.gid from auto_game_prize_roleid_newlogs as tb1, " +
                "(select pid, tablename,count(1) from auto_game_prize_roleid_newlogs where flag = 1 and status = 0 group by tablename,pid having count(1)>1) as tb2 " +
                "where tb1.tablename=tb2.tablename and tb1.pid=tb2.pid and tb1.flag = 1 and tb1.status = 0 " +
                "group by tb1.tablename ,tb1.pid,tb1.gid) as tb group by tablename ,pid having count(1) > 1;";
        List<ExceptionSendBean> exceptionSendBeanList = dao.queryObjects(ExceptionSendBean.class, sql);
        Set<String> tables = new HashSet<String>();
        if (exceptionSendBeanList != null) {
            for (ExceptionSendBean bean : exceptionSendBeanList) {
                tables.add(bean.getTableName());
            }
        }
        List<String> reList = new ArrayList<String>(tables.size());
        Iterator<String> itr = tables.iterator();
        while (itr.hasNext()) {
            reList.add(itr.next());
        }
        return reList;
    }

    public Task getTask(String triggerName) throws SQLException {
        String sql = "select trigger_name,trigger_group,trigger_state from QRTZ_TRIGGERS where trigger_name = ?";
        return dao.queryObject(Task.class, sql, triggerName);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @Autowired
    public void setDao(@Qualifier("hdManagerDao") Idao dao) {
        this.dao = dao;
    }
}
