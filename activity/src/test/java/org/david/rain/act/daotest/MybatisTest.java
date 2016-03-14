package org.david.rain.act.daotest;

import org.david.rain.act.entity.Task;
import org.david.rain.act.persistence.TaskMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by mac on 14-11-26.
 * d* d
 */

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@ContextConfiguration("classpath:spring/applicationContext.xml")
public class MybatisTest {

    private final static Logger LOGGER = LoggerFactory.getLogger(MybatisTest.class);
    @Autowired
    TaskMapper taskMapper;

    @Test
    public void testList() throws Exception {
        List<Task> lists = taskMapper.getTaskList();
        for (Task t : lists) {
            LOGGER.info("bean msg is {}", t);
        }
    }
    

    @Transactional
    @Test
    public void testUpdateObj() throws Exception {
        Task t = new Task();
        t.setTitle("cindy");
        t.setId(1l);
        taskMapper.updateTaskById(t);
//        testList();
    }

}
