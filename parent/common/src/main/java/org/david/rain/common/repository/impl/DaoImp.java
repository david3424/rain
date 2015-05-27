package org.david.rain.common.repository.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.david.rain.common.entity.HdTable;
import org.david.rain.common.lang.CommonList;
import org.david.rain.common.repository.DaoValidater;
import org.david.rain.common.repository.Idao;
import org.david.rain.common.search.Search;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

/**
 * 数据库操作接口 只读数据库
 * User: david
 * Date: 12-11-20
 * Time: 上午11:44
 */
public class DaoImp implements Idao {
/*    @Autowired
    DataSource dataSource_W;*/

    private QueryRunner runner;

    public DaoImp() {
    }

    public DaoImp(QueryRunner runner) {
        this.runner = runner;
    }


    private static Logger logger = LoggerFactory.getLogger(DaoImp.class);


    @Override
    public int insert(Object object) throws SQLException {
        throw new UnsupportedOperationException("can use insert in a read dao");
    }

    @Override
    public long insertAndGetId(Object object) throws SQLException {
        throw new UnsupportedOperationException("can use insert in a read dao");
    }

    @Override
    public int[] batchInsert(String sql, Object[][] objects) throws SQLException {
        throw new UnsupportedOperationException("can use insert in a read dao");
    }

    @Override
    public int update(String sql) throws SQLException {
        throw new UnsupportedOperationException("can use update in a read dao");
    }

    @Override
    public int update(String sql, Object... paramValues) throws SQLException {
        throw new UnsupportedOperationException("can use update in a read dao");
    }

    @Override
    public int update(Object object) throws SQLException {
        throw new UnsupportedOperationException("can use update in a read dao");
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
     * 该方法还没有测试
     *
     * @param sql
     * @param <T>
     * @return
     * @throws java.sql.SQLException
     */
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
        long tt2 = System.currentTimeMillis();
        Long counts = (Long) runner.query(sql, handler);
//        logger.warn("queryCount-tt2:" + (System.currentTimeMillis() - tt2));
        return counts;
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
    public <T> CommonList<T> pagination(Search search, Class<T> clazz) throws Exception {
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
