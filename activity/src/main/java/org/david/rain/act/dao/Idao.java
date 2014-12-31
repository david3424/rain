package org.david.rain.act.dao;

import org.david.rain.act.dao.dbutils.CommonList;
import org.david.rain.act.dao.dbutils.search.Search;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: davidxu
 * Date: 12-12-12
 * Time: 下午4:12
 */
public interface Idao {

    int insert(Object object) throws SQLException;


    public long insertAndGetId(Object object) throws SQLException ;

    public int[] batchInsert(String sql, Object[][] objects) throws SQLException ;

    int update(String sql) throws SQLException;


    int update(String sql, Object... paramValues) throws SQLException;

    public int update(Object object) throws SQLException;


    <T> T queryObject(Class<T> clazz, Integer id) throws SQLException;


    <T> T queryObject(Class<T> clazz, String sql) throws SQLException;

    <T> T queryObject(Class<T> clazz, String sql, Object... paramValues) throws SQLException;


    <T> List<T> queryObjects(Class<T> clazz, String sql) throws SQLException;


    <T> List<T> queryObjects(Class<T> clazz, String sql, Object... paramValues) throws SQLException;


    public <T> T queryScalar(String sql) throws SQLException;
    /**
     * 根据Sql查询单个字段值
     *
     * @param sql select count(xxx)/sum(xxx)/id/username from .....
     * @return count()函数返回Long sum()函数返回BigDecimal,不存在返回0 其他返回字段对应的类型，不存在返回null
     */
    public <T> T queryScalar(String sql, Object... paramValues) throws SQLException;


    public <T> List<T> queryOneColumnList(String sql) throws SQLException;

    public <T> List<T> queryOneColumnList(String sql, Object... paramValues) throws SQLException;


    public <T> CommonList<T> pagination(Search search, Class<T> clazz) throws Exception;

}