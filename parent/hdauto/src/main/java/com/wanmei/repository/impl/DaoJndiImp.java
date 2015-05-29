package com.david.web.wanmei.repository.impl;

import com.david.web.wanmei.common.CommonList;
import com.david.web.wanmei.common.search.Search;
import com.david.web.wanmei.entity.HdTable;
import com.david.web.wanmei.repository.DaoValidater;
import com.david.web.wanmei.repository.Idao;
import com.david.web.wanmei.repository.SqlResolver;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 数据库操作接口
 * User: chenzhiyi
 * Date: 12-11-20
 * Time: 上午11:44
 */
@Component
public class DaoJndiImp implements Idao {
    private QueryRunner runner;

    public DaoJndiImp(QueryRunner runner) {
        this.runner = runner;
    }

    @Override
    public QueryRunner getQueryRunner() {
        return runner;
    }

    /**
     * 插入新数据
     *
     * @param object
     * @return
     */
    public int insert(Object object) throws SQLException {
        SqlResolver resolver = new SqlResolver(object);
        return runner.update(resolver.insertSql(), resolver.paramValues());
    }

    @Override
    public boolean batchInsert(String sql, Object[][] objects) throws SQLException {
        try {
            runner.batch(sql, objects);
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    /**
     * 需要Connection的插入新数据，主要用于控制事务
     *
     * @param conn   Connection
     * @param object
     * @return
     */
    public int insert(Connection conn, Object object) throws SQLException {
        SqlResolver resolver = new SqlResolver(object);
        return runner.update(conn, resolver.insertSql(), resolver.paramValues());
    }

    /**
     * 数据库增insert删delete改update操作执行
     *
     * @param sql
     * @return int 影响数据库行数
     */
    public int update(String sql) throws SQLException {
        return runner.update(sql);
    }

    /**
     * 需要Connection数据库增insert删delete改update操作执行 应用于事务
     *
     * @param conn
     * @param sql
     * @return
     */
    public int update(Connection conn, String sql) throws SQLException {
        return runner.update(conn, sql);
    }

    /**
     * 数据库增insert删delete改update操作执行
     *
     * @param sql
     * @param paramValues 参数列表的值
     * @return int 影响数据库行数
     */
    public int update(String sql, Object... paramValues) throws SQLException {
        return runner.update(sql, paramValues);
    }

    /**
     * 需要Connection数据库增insert删delete改update操作执行 应用于事务
     *
     * @param conn
     * @param sql
     * @param paramValues 参数列表的值
     * @return
     */
    public int update(Connection conn, String sql, Object... paramValues) throws SQLException {
        return runner.update(conn, sql, paramValues);
    }


    /**
     * 根据id查询对象
     *
     * @param clazz 类需要有HdTable注解
     * @param id
     * @return
     */
    public <T> T queryObject(Class<T> clazz, Integer id) throws SQLException {
        DaoValidater.assertHasHdTable(clazz);
        ResultSetHandler<T> handler = new BeanHandler<T>(clazz);
        String tableName = clazz.getAnnotation(HdTable.class).value();
        return runner.query("select * from " + tableName + " where id=?", handler, id);
    }

    /**
     * 需要Connection的根据id查询对象 应用事务控制
     *
     * @param conn
     * @param clazz 类需要有HdTable注解
     * @param id
     * @return
     */
    public <T> T queryObject(Connection conn, Class<T> clazz, Integer id) throws SQLException {
        DaoValidater.assertHasHdTable(clazz);
        ResultSetHandler<T> handler = new BeanHandler<T>(clazz);
        String tableName = clazz.getAnnotation(HdTable.class).value();
        return runner.query(conn, "select * from " + tableName + " where id=?", handler, id);
    }

    /**
     * 根据sql查询单个对象
     *
     * @param clazz
     * @param sql
     * @return
     */
    public <T> T queryObject(Class<T> clazz, String sql) throws SQLException {
        ResultSetHandler<T> handler = new BeanHandler<T>(clazz);
        return runner.query(sql, handler);
    }

    /**
     * 根据sql查询单个对象
     *
     * @param clazz
     * @param sql
     * @param paramValues
     * @return
     */
    public <T> T queryObject(Class<T> clazz, String sql, Object... paramValues) throws SQLException {
        ResultSetHandler<T> handler = new BeanHandler<T>(clazz);
        return runner.query(sql, handler, paramValues);
    }

    /**
     * 需要Connection的根据sql查询单个对象 应用事务控制
     *
     * @param conn
     * @param clazz
     * @param sql
     * @return
     */
    public <T> T queryObject(Connection conn, Class<T> clazz, String sql) throws SQLException {
        ResultSetHandler<T> handler = new BeanHandler<T>(clazz);
        return runner.query(conn, sql, handler);
    }

    /**
     * 需要Connection的根据sql查询单个对象 应用事务控制
     *
     * @param conn
     * @param clazz
     * @param sql
     * @param paramValues
     * @return
     */
    public <T> T queryObject(Connection conn, Class<T> clazz, String sql, Object... paramValues) throws SQLException {
        ResultSetHandler<T> handler = new BeanHandler<T>(clazz);
        return runner.query(conn, sql, handler, paramValues);
    }

    public <T> T queryScalar(String sql) throws SQLException {
        ResultSetHandler<T> handler = new ScalarHandler<T>();
        return runner.query(sql, handler);
    }

    /**
     * 根据Sql查询单个字段值
     *
     * @param sql select count(xxx)/sum(xxx)/id/username from .....
     * @return count()函数返回Long sum()函数返回BigDecimal 其他返回字段对应的类型
     */
    public <T> T queryScalar(String sql, Object... paramValues) throws SQLException {
        ResultSetHandler<T> handler = new ScalarHandler<T>();
        return runner.query(sql, handler, paramValues);
    }

    /**
     * 需要Connection 根据Sql查询单个字段值  应用事务控制
     * @param conn
     * @param sql select count(xxx)/sum(xxx)/id/username from .....
     * @return count()函数返回Long sum()函数返回BigDecimal 其他返回字段对应的类型
     */
    public <T> T queryScalar(Connection conn, String sql) throws SQLException {
        ResultSetHandler<T> handler = new ScalarHandler<T>();
        return runner.query(conn, sql, handler);
    }

    /**
     * 需要Connection 根据Sql查询单个字段值  应用事务控制
     * @param conn
     * @param sql select count(xxx)/sum(xxx)/id/username from .....
     * @return count()函数返回Long sum()函数返回BigDecimal 其他返回字段对应的类型
     */
    public <T> T queryScalar(Connection conn, String sql, Object... paramValues) throws SQLException {
        ResultSetHandler<T> handler = new ScalarHandler<T>();
        return runner.query(conn, sql, handler, paramValues);
    }

    public <T> List<T> queryOneColumnList(String sql) throws SQLException {
        ColumnListHandler<T> hander = new ColumnListHandler<T>(1);
        return runner.query(sql, hander);
    }


    /**
     * 查询数据列表
     *
     * @param clazz
     * @param sql
     * @return
     */
    public <T> List<T> queryObjects(Class<T> clazz, String sql) throws SQLException {
        ResultSetHandler<List<T>> handler = new BeanListHandler<T>(clazz);
        return runner.query(sql, handler);
    }

    /**
     * 需要Connection查询数据列表  应用事务控制
     *
     * @param conn
     * @param clazz
     * @param sql
     * @return
     */
    public <T> List<T> queryObjects(Connection conn, Class<T> clazz, String sql) throws SQLException {
        ResultSetHandler<List<T>> handler = new BeanListHandler<T>(clazz);
        return runner.query(conn, sql, handler);
    }

    /**
     * 查询数据列表
     *
     * @param clazz
     * @param sql
     * @return
     */
    public <T> List<T> queryObjects(Class<T> clazz, String sql, Object... paramValues) throws SQLException {
        ResultSetHandler<List<T>> handler = new BeanListHandler<T>(clazz);
        return runner.query(sql, handler, paramValues);
    }

    /**
     * 需要Connection查询数据列表  应用事务控制
     *
     * @param conn
     * @param clazz
     * @param sql
     * @return
     */
    public <T> List<T> queryObjects(Connection conn, Class<T> clazz, String sql, Object... paramValues) throws SQLException {
        ResultSetHandler<List<T>> handler = new BeanListHandler<T>(clazz);
        return runner.query(conn, sql, handler, paramValues);
    }

    /**
     * 查询数量SQL
     *
     * @param sql 必须是查询数量的 select count(1)....
     * @return
     */
    public long queryCount(String sql) throws SQLException {
        ResultSetHandler handler = new ScalarHandler();
        return (Long) runner.query(sql, handler);
    }

    /**
     * 需要Connection查询数量SQL  应用事务控制
     *
     * @param conn
     * @param sql  必须是查询数量的 select count(1)....
     * @return
     */
    public long queryCount(Connection conn, String sql) throws SQLException {
        ResultSetHandler handler = new ScalarHandler();
        return (Long) runner.query(conn, sql, handler);
    }

    /**
     * 查询数量SQL
     *
     * @param sql 必须是查询数量的 select count(1)....
     * @return
     */
    public long queryCount(String sql, Object... paramValues) throws SQLException {
        ResultSetHandler handler = new ScalarHandler();
        return (Long) runner.query(sql, handler, paramValues);
    }

    /**
     * 需要Connection查询数量SQL  应用事务控制
     *
     * @param conn
     * @param sql  必须是查询数量的 select count(1)....
     * @return
     */
    public long queryCount(Connection conn, String sql, Object... paramValues) throws SQLException {
        ResultSetHandler handler = new ScalarHandler();
        return (Long) runner.query(conn, sql, handler, paramValues);
    }

    /**
     * 分页查询
     *
     * @param search
     * @param clazz
     * @return
     */
    public <T> CommonList<T> pagination(Search search, Class<T> clazz) {
        int recNum = 0; //查询的总页数；
        if (search.getPageNo() <= 0) {
            search.setPageNo(1);
        }
        List<T> objects = null;
        search.setWhetherPage(true);
        try {
            String countsql = search.builderCountSql();
            String objectssql = search.builderSearchSql();
            if (search.getWhereParas().size() == 0) {
                recNum = (int) queryCount(countsql);
                objects = queryObjects(clazz, objectssql); //得到记录集合
            } else {
                recNum = (int) queryCount(countsql, search.getWhereParas().toArray());
                objects = queryObjects(clazz, objectssql, search.getWhereParas().toArray());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        CommonList<T> commonList = new CommonList<T>(search.getSearchStr(), recNum, search.getPageNo(), search.getPageSize());
        commonList.addAll(objects);
        return commonList;
    }

}
