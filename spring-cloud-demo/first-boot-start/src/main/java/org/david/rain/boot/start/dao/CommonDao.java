package org.david.rain.boot.start.dao;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.util.List;

/**
 * @author xdw9486
 */
public interface CommonDao {
    /**
     * @param sql 查询语句
     * @return
     * @throws
     * @Title: queryList
     * @Description: TODO 返回指定sql语句结果
     */
    List queryList(String sql);

    /**
     * @param sql
     * @param o   参数集
     * @return
     * @throws
     * @Title: queryList
     * @Description: TODO 返回指定sql语句结果
     */
    List queryList(String sql, Object[] o);

    <T> List<T> queryObjList(String sql, Object[] args, Class<T> clazz);

    <T> List<T> queryForList(String sql, Object[] args, Class<T> elementType);

    /**
     * @param sql
     * @param o
     * @return
     * @throws
     * @Title: queryForInt
     * @Description: TODO 返回条数信息
     */
    int queryForInt(String sql, Object[] o);

    /**
     * @param sql
     * @param o
     * @return
     * @throws
     * @Title: addOrUpdate
     * @Description: TODO 添加、修改、删除
     */
    int addOrUpdate(String sql, Object[] o);

    int addOrUpdateForTransaction(String sql, String sql1, String sql2,
                                  List<List<Object>> userCondition, String[]... o) throws Exception;

    int addOrUpdateForTransactionOneTable(String sql, String sqlselect,
                                          String[] o) throws Exception;

    <T> T queryObj(String sql, Object[] args, Class<T> clazz);

    <T> List<T> query(String sql, Object[] args, RowMapper<T> rowMapper);

    int[] batchUpdate(String sql, BatchPreparedStatementSetter pss);

    void execSql(String sqlString);

    List queryInList(String oraQueryListSql, MapSqlParameterSource parameters);
}
