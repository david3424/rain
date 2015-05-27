package com.wanmei.repository;

import com.wanmei.repository.impl.DaoJndiImp;
import org.apache.commons.dbutils.QueryRunner;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据源管理
 * User: chenzhiyi
 * Date: 12-11-16
 * Time: 上午10:54
 */
public class DaoManager {
    public final static String HUO_DONG_161 = "huodong161";
    private static final Map<String, DataSource> dataSourceMap = new ConcurrentHashMap<String, DataSource>();
    private static final Map<String, QueryRunner> queryRunnerMap = new ConcurrentHashMap<String, QueryRunner>();
    private static final Map<String, DaoJndiImp> daoMap = new ConcurrentHashMap<String,DaoJndiImp>();

    static {
        try {
            initDataSource();
            System.out.println("------datasource init successful------------");
        } catch (NamingException e) {
            System.out.println("------datasource init fails------------");
            e.printStackTrace();
        }
    }

    //初始化数据源
    private static void initDataSource() throws NamingException {
        Context initCtx = new InitialContext();
        Context envCtx = (Context) initCtx.lookup("java:comp/env");
        dataSourceMap.put(HUO_DONG_161, (DataSource) envCtx.lookup("jdbc/huodong161"));
        queryRunnerMap.put(HUO_DONG_161, new QueryRunner(getDataSource(HUO_DONG_161)));
        daoMap.put(HUO_DONG_161,new DaoJndiImp(queryRunnerMap.get(HUO_DONG_161)));
    }

    /**
     * 获取数据源
     *
     * @param dataSourceName huodong218 huodong161
     * @return DataSource
     */
    public static DataSource getDataSource(String dataSourceName) {
        return dataSourceMap.get(dataSourceName);
    }

    /**
     * 获得数据库链接
     *
     * @param dataSourceName huodong218 huodong161
     * @return Connection
     */
    public static Connection getConnection(String dataSourceName) throws SQLException {
        return dataSourceMap.get(dataSourceName).getConnection();
    }

    /**
     * 获得QueryRunner huodong218 huodong161
     * @param dataSourceName
     * @return QueryRunner
     */
    public static QueryRunner getQueryRunner(String dataSourceName) {
        return queryRunnerMap.get(dataSourceName);
    }

    /**
     * 获得DAO huodong218 huodong161
     * @param dataSourceName
     * @return DaoJndiImp
     */
    public static DaoJndiImp getDao(String dataSourceName){
        return daoMap.get(dataSourceName);
    }
}
