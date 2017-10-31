package com.didispace;

import org.david.rain.cloud.start.Application;
import org.david.rain.cloud.start.dao.CommonDao;
import org.david.rain.cloud.start.pojo.KoTask;
import org.david.rain.common.logback.LoggerUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedCaseInsensitiveMap;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by mac on 14-11-29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional("oracleTransactionManager")
@SpringBootTest(classes = Application.class)
public class JdbcTemplateTests {

    @Autowired
    @Qualifier("kingDaoImp")
    private CommonDao kingCommonDao;
    @Autowired
    @Qualifier("oracleDaoImp")
    private CommonDao oraleCommonDao;

    private static final String ORA_QUERY_LIST_SQL = "SELECT p.PRODUCT_ID_ productId,p.FUNDCODE_ fundcode  FROM PAY_PRODUCT_SD  p where FUNDCODE_ in (:ids) ";
    private static final String QUERY_LIST_SQL = "SELECT task_id,status,remark FROM ko_task where 1= ? ";
    private static final String QUERY_MAPPER_LIST_SQL = "SELECT task_id,status,remark FROM ko_task where 1= ? ";
    private static final String QUERY_SCALAR_COUNT_SQL = "select count(1) from ko_task where 1=? ";
    private static final String QUERY_SCALAR_SUM_SQL = "select sum(task_id) from ko_task where 1=? ";
    private static final String QUERY_SCALAR_SQL = "select remark from ko_task where status=? ";
    private static final String QUERY_OBJ_SQL = " select * from ko_task where id=? ";
    private static final String UPDATE_OBJ_SQL = " update ko_task set remark = ? where status = 1 ";


    @Test
    public void testQueryList() throws Exception {
        List lists = kingCommonDao.queryList(QUERY_LIST_SQL, new Object[]{1});
        for (Object t : lists) {
            Map<String, Object> tt = (LinkedCaseInsensitiveMap) t;
            for (Map.Entry<String, Object> m : tt.entrySet()) {
                LoggerUtil.info("【{}】:{}", m.getKey(), m.getValue());
            }
        }
    }

    @Test
    public void testQueryForInt() throws Exception {
        int count = kingCommonDao.queryForInt(QUERY_SCALAR_COUNT_SQL, new Object[]{1});
        LoggerUtil.info("count in {} is {}", "testQueryForInt", count);
        int sum = kingCommonDao.queryForInt(QUERY_SCALAR_SUM_SQL, new Object[]{1});
        LoggerUtil.info("sum in {} is {}", "testQueryForInt", sum);
    }

    @Test
    public void testObjectList() throws Exception {
        List<KoTask> lists = kingCommonDao.queryObjList(QUERY_LIST_SQL, new Object[]{1}, KoTask.class);
        for (KoTask t : lists) {
            LoggerUtil.info("obj in {} is {}", "testObjectList", t);
        }
    }

    @Test
    public void testUpdateSql() throws Exception {
        int result = kingCommonDao.addOrUpdate(UPDATE_OBJ_SQL, new Object[]{"testTile2"});
        LoggerUtil.info("result of {},={}", "testUpdateBean", result);
        testQueryList();
    }


    /**
     * 测试oracle jdbcTemplate 操作IN 方式 有问题 todo
     * @return
     */
    @Test
    public void testOracleQueryList() throws Exception {
        Set<String> ids = new HashSet<>();
        ids.add("050003");
        ids.add("161616");
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("ids", ids);
        List lists = oraleCommonDao.queryInList(ORA_QUERY_LIST_SQL, parameters);
        for (Object t : lists) {
            Map<String, Object> tt = (LinkedCaseInsensitiveMap) t;
            for (Map.Entry<String, Object> m : tt.entrySet()) {
                LoggerUtil.info("【{}】:{}", m.getKey(), m.getValue());
            }
        }
    }


}
