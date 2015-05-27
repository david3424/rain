package com.wanmei.repository.impl;

import com.wanmei.common.CommonList;
import com.wanmei.common.search.Search;
import com.wanmei.entity.HdTable;
import com.wanmei.repository.DaoValidater;
import com.wanmei.repository.Idao;
import com.wanmei.repository.SqlResolver;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 数据库操作接口
 * User: chenzhiyi
 * Date: 12-11-20
 * Time: 上午11:44
 */
@Qualifier("PrizeDaoImp")
@Repository
public class PrizeDaoImp implements Idao {
    protected QueryRunner runner;
    protected DataSource dataSource20;
    private static final Log LOG = LogFactory.getLog(PrizeDaoImp.class);

    @Autowired
    public void setDataSource20(@Qualifier("dataSource20") DataSource dataSource20) {
        this.dataSource20 = dataSource20;
        this.runner = new QueryRunner(dataSource20);
    }

    /* public DaoImp() {
        this.runner = new QueryRunner(dataSource);
    }*/

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
    @Override
    public int insert(Object object) throws SQLException {
        runner = new QueryRunner(dataSource20);
        SqlResolver resolver = new SqlResolver(object);
        return runner.update(resolver.insertSql(), resolver.paramValues());
    }

    public boolean batchInsert(String sql, Object[][] objects) {
        runner = new QueryRunner(dataSource20);
        try {
            runner.batch(sql, objects);
        } catch (SQLException e) {
            LOG.error(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 数据库增insert删delete改update操作执行
     *
     * @param sql
     * @return int 影响数据库行数
     */
    @Override
    public int update(String sql) throws SQLException {
        runner = new QueryRunner(dataSource20);
        return runner.update(sql);
    }


    /**
     * 数据库增insert删delete改update操作执行
     *
     * @param sql
     * @param paramValues 参数列表的值
     * @return int 影响数据库行数
     */
    @Override
    public int update(String sql, Object... paramValues) throws SQLException {
        runner = new QueryRunner(dataSource20);
        return runner.update(sql, paramValues);
    }


    /**
     * 根据id查询对象
     *
     * @param clazz 类需要有HdTable注解
     * @param id
     * @return
     */
    @Override
    public <T> T queryObject(Class<T> clazz, Integer id) throws SQLException {
        runner = new QueryRunner(dataSource20);
        DaoValidater.assertHasHdTable(clazz);
        ResultSetHandler<T> handler = new BeanHandler<T>(clazz);
        String tableName = clazz.getAnnotation(HdTable.class).value();
        return runner.query("select * from " + tableName + " where id=?", handler, id);
    }


    /**
     * 根据sql查询单个对象
     *
     * @param clazz
     * @param sql
     * @return
     */
    @Override
    public <T> T queryObject(Class<T> clazz, String sql) throws SQLException {
        runner = new QueryRunner(dataSource20);
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
    @Override
    public <T> T queryObject(Class<T> clazz, String sql, Object... paramValues) throws SQLException {
//        runner = new QueryRunner(dataSource20);
        ResultSetHandler<T> handler = new BeanHandler<T>(clazz);
        return runner.query(sql, handler, paramValues);
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
    @Override
    public <T> List<T> queryObjects(Class<T> clazz, String sql) throws SQLException {
        runner = new QueryRunner(dataSource20);
        ResultSetHandler<List<T>> handler = new BeanListHandler<T>(clazz);
        return runner.query(sql, handler);
    }


    /**
     * 查询数据列表
     *
     * @param clazz
     * @param sql
     * @return
     */
    @Override
    public <T> List<T> queryObjects(Class<T> clazz, String sql, Object... paramValues) throws SQLException {
        runner = new QueryRunner(dataSource20);
        ResultSetHandler<List<T>> handler = new BeanListHandler<T>(clazz);
        return runner.query(sql, handler, paramValues);
    }


    /**
     * 查询数量SQL
     *
     * @param sql 必须是查询数量的 select count(1)....
     * @return
     */
    @Override
    public long queryCount(String sql) throws SQLException {
        runner = new QueryRunner(dataSource20);
        ResultSetHandler handler = new ScalarHandler();
        return (Long) runner.query(sql, handler);
    }


    /**
     * 查询数量SQL
     *
     * @param sql 必须是查询数量的 select count(1)....
     * @return
     */
    @Override
    public long queryCount(String sql, Object... paramValues) throws SQLException {
        runner = new QueryRunner(dataSource20);
        ResultSetHandler handler = new ScalarHandler();
        return (Long) runner.query(sql, handler, paramValues);
    }


    /**
     * 分页查询
     *
     * @param search
     * @param clazz
     * @return
     */
    @Override
    public <T> CommonList<T> pagination(Search search, Class<T> clazz) {
        runner = new QueryRunner(dataSource20);
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
