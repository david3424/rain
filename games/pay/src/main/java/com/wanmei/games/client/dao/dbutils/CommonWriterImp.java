
package com.wanmei.games.client.dao.dbutils;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.*;
import com.wanmei.games.client.dao.Idao;
import com.wanmei.games.client.dao.dbutils.search.Search;
import com.wanmei.games.utils.DaoValidater;
import com.wanmei.games.utils.QueryNewRunner;
import com.wanmei.games.utils.SqlResolver;
import com.wanmei.games.utils.entity.HdTable;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 数据库操作接口 处理事务时使用
 * david xu
 */
public class CommonWriterImp implements Idao {

    private QueryNewRunner runner;

    public CommonWriterImp() {

    }

    /**
     * 构造注入runner
     * @param runner dbutils封装
     */
    public CommonWriterImp(QueryNewRunner runner) {
        this.runner = runner;
    }

    /**
     * 插入新数据
     *用到hdtable注解
     * @param object
     * @return
     */
    @Override
    public int insert(Object object) throws SQLException {
        SqlResolver resolver = new SqlResolver(object);
        return runner.update(resolver.insertSql(), resolver.paramValues());
    }

    /**
     * 必须是同一个connection才能取到id，可将conn讲给spring管理
     * @param object
     * @return
     * @throws java.sql.SQLException
     */
    @Override
    public long insertAndGetId(Object object) throws SQLException {
        insert(object);
        String sql = "select last_insert_id() ";
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
     * 用到hdtable注解
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
     * 分页查询
     *
     * @param search
     * @param clazz
     * @return
     */
    @Override
    public <T> CommonList<T> pagination(Search search, Class<T> clazz) {
        Number recNum = 0; //查询的总页数；
        if (search.getPageNo() <= 0) {
            search.setPageNo(1);
        }
        List<T> objects = null;
        search.setWhetherPage(true);
        try {
            String countsql = search.builderCountSql();
            String objectssql = search.builderSearchSql();
            if (search.getWhereParas().size() == 0) {
                recNum =  queryScalar(countsql);
                objects = queryObjects(clazz, objectssql); //得到记录集合
            } else {
                recNum =  queryScalar(countsql, search.getWhereParas().toArray());
                objects = queryObjects(clazz, objectssql, search.getWhereParas().toArray());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        CommonList<T> commonList = new CommonList<>(search. getSearchStr(), recNum.intValue(), search.getPageNo(), search.getPageSize());
        if (objects != null) {
            commonList.addAll(objects);
        }
        return commonList;
    }
    
    
//    new add
public Map<Integer, Map> queryMapObjects(String sql, Object... paramValues) {

    ResultSetHandler h = new KeyedHandler("id");
    try {
        return (Map<Integer, Map>) runner.query(sql, h,paramValues);
    } catch (SQLException e) {
        e.printStackTrace();
        return null;
    }
}


    /**
     * 根据ID来更新object
     *
     * @param object
     * @return
     * @throws SQLException
     */
    public int updateBean(Object object) throws SQLException {
        SqlResolver resolver = new SqlResolver(object);
        return runner.update(resolver.updateSql(), resolver.paramValues());
    }

}
