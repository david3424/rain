package com.didispace;

import org.david.rain.cloud.start.Application;
import org.david.rain.cloud.start.dao.CommonDao;
import org.david.rain.cloud.start.pojo.KoTask;
import org.david.rain.common.logback.LoggerUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedCaseInsensitiveMap;

import java.util.List;
import java.util.Map;

/**
 * Created by mac on 14-11-29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@SpringBootTest(classes = Application.class)
public class JdbcTempalteTests {

    @Autowired
    private CommonDao imp;

    private static final String QUERY_LIST_SQL = "SELECT task_id,status,remark FROM ko_task where 1= ? ";
    private static final String QUERY_MAPPER_LIST_SQL = "SELECT task_id,status,remark FROM ko_task where 1= ? ";
    private static final String QUERY_SCALAR_COUNT_SQL = "select count(1) from ko_task where 1=? ";
    private static final String QUERY_SCALAR_SUM_SQL = "select sum(task_id) from ko_task where 1=? ";
    private static final String QUERY_SCALAR_SQL = "select remark from ko_task where status=? ";
    private static final String QUERY_OBJ_SQL = " select * from ko_task where id=? ";
    private static final String UPDATE_OBJ_SQL = " update ko_task set remark = ? where status = 1 ";


    @Test
    public void testQueryList() throws Exception {
        List lists = imp.queryList(QUERY_LIST_SQL, new Object[]{1});
        for (Object t : lists) {
            Map<String, Object> tt = (LinkedCaseInsensitiveMap) t;
            for (Map.Entry<String, Object> m : tt.entrySet()) {
                LoggerUtil.info("【{}】:{}", m.getKey(), m.getValue());
            }
        }
    }

    @Test
    public void testQueryForInt() throws Exception {
        int count = imp.queryForInt(QUERY_SCALAR_COUNT_SQL, new Object[]{1});
        LoggerUtil.info("count in {} is {}", "testQueryForInt", count);
        int sum = imp.queryForInt(QUERY_SCALAR_SUM_SQL, new Object[]{1});
        LoggerUtil.info("sum in {} is {}", "testQueryForInt", sum);
    }

   @Test
    public void testObjectList() throws Exception {
        List<KoTask> lists = imp.queryObjList(QUERY_LIST_SQL, new Object[]{1}, KoTask.class);
        for (KoTask t : lists) {
            LoggerUtil.info("obj in {} is {}", "testObjectList", t);
        }
    }

  /*   @Test
    public void testQueryObj() throws Exception {
        Task t = imp.queryObj(QUERY_OBJ_SQL, new Object[]{1}, Task.class);
        LoggerUtil.info("obj in {} is {}", "testQueryObj", t);
    }

    @Test
    public void testQueryMapperList() throws Exception {
        List<UserTask> lists = imp.query(QUERY_MAPPER_LIST_SQL, new Object[]{3}, new UserTaskMapper());
//        List<UserTask> lists = imp.queryObjList(QUERY_MAPPER_LIST_SQL,new Object[]{3}, UserTask.class);
        for (UserTask ut : lists) {
            LoggerUtil.info("obj in {} is {}", "testObjectList", ut);
        }
    }
*/
    @Test
    public void testUpdateSql() throws Exception {
        int result = imp.addOrUpdate(UPDATE_OBJ_SQL, new Object[]{"testTile2"});
        LoggerUtil.info("result of {},={}", "testUpdateBean", result);
        testQueryList();
    }

}
