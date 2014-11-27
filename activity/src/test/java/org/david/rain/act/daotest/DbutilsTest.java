package org.david.rain.act.daotest;

import org.david.rain.act.dao.Idao;
import org.david.rain.act.entity.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by mac on 14-11-26.
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@ContextConfiguration("classpath:spring/applicationContext.xml")
public class DbutilsTest {

    private final static Logger LOGGER = LoggerFactory.getLogger(DbutilsTest.class);
    public static final String QUERY_LIST_SQL = "select * from ss_task where 1= ? ";
    public static final String QUERY_SCALAR_COUNT_SQL = "select count(1) from ss_task where 1!=? ";
    public static final String QUERY_SCALAR_SUM_SQL = "select sum(user_id) from ss_task where 1=? ";
    public static final String QUERY_SCALAR_SQL = "select title from ss_task where title=? ";
    public static final String QUERY_OBJ_SQL = " select * from ss_task where title=? ";
    public static final String UPDATE_OBJ_SQL = " update ss_task set title = ? where id <5 ";
    @Autowired
    Idao idao;

    @Test
    public void testList() throws Exception {
        List<Task> lists = idao.queryObjects(Task.class, QUERY_LIST_SQL, 1);
        for (Task t : lists) {
            LOGGER.info("bean msg is {}", t);
        }
    }

    @Test
    public void testQueryScarlar() throws Exception {
        long taskNum = idao.queryScalar(QUERY_SCALAR_COUNT_SQL, 1);
        LOGGER.info("taskNum in {} is {} ", "testQueryScarlar", taskNum);
        String title = idao.queryScalar(QUERY_SCALAR_SQL, "Release SpringSide 4.0");
        LOGGER.info("title in {} is {} ", "testQueryScarlar", title);
        BigDecimal sum = idao.queryScalar(QUERY_SCALAR_SUM_SQL, 1);
        LOGGER.info("sum in {} is {} ", "testQueryScarlar", sum.doubleValue());
    }

    @Test
    public void testQueryObj() throws Exception {
        Task task_by_id = idao.queryObject(Task.class, 1);
        LOGGER.info("task_by_id in {} is {} ", "testQueryObj", task_by_id);
        Task task_by_sql = idao.queryObject(Task.class, QUERY_OBJ_SQL, "Release SpringSide 4.0");
        LOGGER.info("task_by_sql in {} is {} ", "testQueryObj", task_by_sql);
    }

    @Test
    @Transactional
    public void testInsertObj() throws Exception {
        int re = idao.insert(new Task("title", "desc", 1l));
        LOGGER.info("re in {} is {} ", "testInsertObj", re);
        long newid = idao.insertAndGetId(new Task("title11", "desc11", 11l));
        LOGGER.info("newid in {} is {} ", "testInsertObj", newid);
    }

    @Transactional
    @Test
    public void testUpdateObj() throws Exception {
    int re  = idao.update(UPDATE_OBJ_SQL,"new title12");
        LOGGER.info("re in {} is {} ", "testUpdateObj", re);
        testList();
    }

    @Test
    public void testUpdateBean() throws Exception {
        int reb = idao.update(new Task(1l,"title11", "desc11", 11l));
        LOGGER.info("reb in {} is {} ", "testUpdateBean", reb);
        testList();
    }
}
