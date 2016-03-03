package org.david.rain.monitor.dbutils.repository.impl;

import org.david.rain.monitor.dbutils.repository.DaoValidater;
import org.david.rain.monitor.dbutils.repository.Idao;
import org.david.rain.monitor.dbutils.repository.QueryNewRunner;
import org.david.rain.monitor.dbutils.repository.SqlResolver;
import org.david.rain.monitor.dbutils.repository.anotation.HdTable;
import org.david.rain.monitor.util.CommonList;
import org.david.rain.monitor.util.search.Search;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * 数据库操作接口 处理事务时使用
 * david xu
 */
public class WriteDaoImp implements Idao {

    private QueryNewRunner runner;

    public WriteDaoImp() {

    }

    public WriteDaoImp(QueryNewRunner runner) {
        this.runner = runner;
    }

    /**
     * 插入新数据
     *
     * @param object
     * @return
     */
    @Override
    public int insert(Object object) throws SQLException {
        SqlResolver resolver = new SqlResolver(object);
        return runner.update(resolver.insertSql(), resolver.paramValues());
    }

    @Override
    public long insertAndGetId(Object object) throws SQLException {
        insert(object);
        String sql = "select last_insert_id()";
        Long id = queryScalar(sql);
        if (id != null)
            return id;
        else
            return 0;
    }

    @Override
    public int[] batchInsert(String sql, Object[][] objects) throws SQLException {
        return runner.batch(sql, objects);
    }

    /**
     * 数据库增insert删delete改update操作执行
     *
     * @param sql
     * @return int 影响数据库行数
     */
    @Override
    public int update(String sql) throws SQLException {
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
        return runner.update(sql, paramValues);
    }


    /**
     * 根据ID来更新object
     *
     * @param object
     * @return
     * @throws java.sql.SQLException
     */
    @Override
    public int update(Object object) throws SQLException {
        SqlResolver resolver = new SqlResolver(object);
        return runner.update(resolver.updateSql(), resolver.paramValues());
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

        DaoValidater.assertHasHdTable(clazz);
        ResultSetHandler<T> handler = new BeanHandler<>(clazz);
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
        ResultSetHandler<T> handler = new BeanHandler<>(clazz);
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
        ResultSetHandler<T> handler = new BeanHandler<>(clazz);
        return runner.query(sql, handler, paramValues);
    }


    @Override
    public <T> T queryScalar(String sql) throws SQLException {
        ResultSetHandler<T> handler = new ScalarHandler<>();
        return runner.query(sql, handler);
    }

    /**
     * 根据Sql查询单个字段值
     *
     * @param sql select count(xxx)/sum(xxx)/id/username from .....
     * @return count()函数返回Long sum()函数返回BigDecimal 其他返回字段对应的类型
     */
    @Override
    public <T> T queryScalar(String sql, Object... paramValues) throws SQLException {
        ResultSetHandler<T> handler = new ScalarHandler<>();
        return runner.query(sql, handler, paramValues);
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
        ResultSetHandler<List<T>> handler = new BeanListHandler<>(clazz);
        return runner.query(sql, handler);
    }


    /**
     * @param sql
     * @param <T>
     * @return
     * @throws java.sql.SQLException
     */
    @Override
    public <T> List<T> queryOneColumnList(String sql) throws SQLException {
        ColumnListHandler<T> hander = new ColumnListHandler<>(1);
        return runner.query(sql, hander);
    }


    @Override
    public <T> List<T> queryOneColumnList(String sql, Object... paramValues) throws SQLException {
        ColumnListHandler<T> hander = new ColumnListHandler<>(1);
        return runner.query(sql, hander, paramValues);
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
        ResultSetHandler<List<T>> handler = new BeanListHandler<>(clazz);
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
    public <T> CommonList<T> pagination(Search search, Class<T> clazz) throws Exception{
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
            throw ex;
        }
        CommonList<T> commonList = new CommonList<>(search.getSearchStr(), recNum, search.getPageNo(), search.getPageSize());
        if (objects != null) {
            commonList.addAll(objects);
        }
        return commonList;
    }

}
