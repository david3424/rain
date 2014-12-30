package org.david.rain.dubbox.provider.dao;

import org.david.rain.dubbox.provider.entity.Task;
import org.david.rain.dubbox.provider.entity.UserTask;
import org.david.rain.dubbox.provider.entity.UserTaskMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedCaseInsensitiveMap;

import java.util.List;
import java.util.Map;

/**
 * Created by mac on 14-11-29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
@ContextConfiguration("classpath:spring/applicationContext.xml")
public class JdbcTempalteTest {


    @Autowired
    private CommonDao imp;

    private final static Logger LOGGER = LoggerFactory.getLogger(JdbcTempalteTest.class);
    public static final String QUERY_LIST_SQL = "select * from ss_task where 1= ? ";
    public static final String QUERY_MAPPER_LIST_SQL = "select title,user_id,login_name from ss_task a ,ss_user b where a.user_id =b.id and  a.id> ? ";
    public static final String QUERY_SCALAR_COUNT_SQL = "select count(1) from ss_task where 1=? ";
    public static final String QUERY_SCALAR_SUM_SQL = "select sum(user_id) from ss_task where 1=? ";
    public static final String QUERY_SCALAR_SQL = "select title from ss_task where title=? ";
    public static final String QUERY_OBJ_SQL = " select * from ss_task where id=? ";
    public static final String UPDATE_OBJ_SQL = " update ss_task set title = ? where id <5 ";


    @Test
    public void testQueryList() throws Exception {
        List lists = imp.queryList(QUERY_LIST_SQL,new Object[]{1});
        for (Object t : lists) {
            Map<String,Object> tt = (LinkedCaseInsensitiveMap)t;
            for(Map.Entry<String,Object> m: tt.entrySet()){
                LOGGER.info("key is {},value is {}", m.getKey(),m.getValue());
            }
        }
    }

    @Test
    public void testQueryForInt() throws Exception {
         int count = imp.queryForInt(QUERY_SCALAR_COUNT_SQL,new Object[]{1});
        LOGGER.info("count in {} is {}","testQueryForInt",count);
        int sum = imp.queryForInt(QUERY_SCALAR_SUM_SQL,new Object[]{1});
        LOGGER.info("sum in {} is {}","testQueryForInt",sum);
    }

    @Test
    public void testObjectList() throws Exception {
            List<Task> lists = imp.queryObjList(QUERY_LIST_SQL,new Object[]{1},Task.class);
        for(Task t:lists){
            LOGGER.info("obj in {} is {}","testObjectList",t);
        }
    }

    @Test
    public void testQueryObj() throws Exception {
        Task t = imp.queryObj(QUERY_OBJ_SQL,new Object[]{1},Task.class);
        LOGGER.info("obj in {} is {}","testQueryObj",t);
    }

    @Test
    public void testQueryMapperList() throws Exception {
    List<UserTask> lists = imp.query(QUERY_MAPPER_LIST_SQL, new Object[]{3}, new UserTaskMapper());
//        List<UserTask> lists = imp.queryObjList(QUERY_MAPPER_LIST_SQL,new Object[]{3}, UserTask.class);
        for(UserTask ut:lists){
            LOGGER.info("obj in {} is {}","testObjectList",ut);
        }
    }

    @Test
    public void testUpdateSql() throws Exception {
      int result =   imp.addOrUpdate(UPDATE_OBJ_SQL,new Object[]{"testTile2"});
    LOGGER.info("result of {},={}","testUpdateBean",result);
        testQueryList();
    }

}
