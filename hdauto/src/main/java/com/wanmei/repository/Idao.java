package com.wanmei.repository;

import com.wanmei.common.CommonList;
import com.wanmei.common.search.Search;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: davidxu
 * Date: 12-12-12
 * Time: 下午4:12
 */
public interface Idao {
    QueryRunner getQueryRunner();

    int insert(Object object) throws SQLException;

    boolean batchInsert(String sql, Object[][] objects) throws SQLException;

    int update(String sql) throws SQLException;


    int update(String sql, Object... paramValues) throws SQLException;


    <T> T queryObject(Class<T> clazz, Integer id) throws SQLException;


    <T> T queryObject(Class<T> clazz, String sql) throws SQLException;

    <T> T queryObject(Class<T> clazz, String sql, Object... paramValues) throws SQLException;

    public <T> T queryScalar(String sql) throws SQLException;

    /**
     * 根据Sql查询单个字段值
     *
     * @param sql select count(xxx)/sum(xxx)/id/username from .....
     * @return count()函数返回Long sum()函数返回BigDecimal 其他返回字段对应的类型
     */
    public <T> T queryScalar(String sql, Object... paramValues) throws SQLException;

    /**
     * 需要Connection 根据Sql查询单个字段值  应用事务控制
     *
     * @param conn
     * @param sql  select count(xxx)/sum(xxx)/id/username from .....
     * @return count()函数返回Long sum()函数返回BigDecimal 其他返回字段对应的类型
     */
    public <T> T queryScalar(Connection conn, String sql) throws SQLException;

    /**
     * 需要Connection 根据Sql查询单个字段值  应用事务控制
     *
     * @param conn
     * @param sql  select count(xxx)/sum(xxx)/id/username from .....
     * @return count()函数返回Long sum()函数返回BigDecimal 其他返回字段对应的类型
     */
    public <T> T queryScalar(Connection conn, String sql, Object... paramValues) throws SQLException;


    /**
     * 该方法还没有测试
     *
     * @param sql
     * @param <T>
     * @return
     * @throws java.sql.SQLException
     */
    public <T> List<T> queryOneColumnList(String sql) throws SQLException;


    <T> List<T> queryObjects(Class<T> clazz, String sql) throws SQLException;


    <T> List<T> queryObjects(Class<T> clazz, String sql, Object... paramValues) throws SQLException;


    long queryCount(String sql) throws SQLException;


    long queryCount(String sql, Object... paramValues) throws SQLException;


    <T> CommonList<T> pagination(Search search, Class<T> clazz);


}
