package com.david.web.pppppp.repository.impl;


import com.david.web.pppppp.repository.ResendPrizeIdao;
import com.david.web.pppppp.service.ServiceException;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: davidxu
 * Date: 13-1-10
 * Time: 下午2:45
 */
@Qualifier("ResendPrizeDaoImp")
@Repository
public class ResendPrizeDaoImp extends Prize161DaoImp implements ResendPrizeIdao {

    public List queryGroupByTablename(String tablename) {
        runner = new QueryRunner(dataSource161);
        try {
            return (List) runner.query(" select status, count(status) as a  from  " + tablename + " group by status ", new MapListHandler());
        } catch (SQLException e) {
            throw new ServiceException("查询 发奖状态sql异常");
        }
    }

    public List queryFailedGid(String tablename) {
         runner = new QueryRunner(dataSource161);
        try {
            return (List) runner.query(" select gid  from  " + tablename + " where status in (3,8)  ", new MapListHandler());
        } catch (SQLException e) {
            throw new ServiceException("查询 发奖状态sql异常");
        }
    }
}
