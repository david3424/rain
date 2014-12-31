package org.david.rain.dubbox.provider.dao;

import org.david.rain.dubbox.provider.dao.utils.EasyPageInfo;
import org.david.rain.dubbox.provider.entity.Task;
import org.david.rain.dubbox.provider.persistence.TaskMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by david on 2014/12/31.
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
@ContextConfiguration("classpath:spring/applicationContext.xml")
public class MybatisTest {



    @Autowired
    TaskMapper taskMapper;


    @Test
    public void testQuery() throws Exception {

//        System.out.println(taskMapper.getTask(11));
        List<Task> list = taskMapper.getMemberListPage(new EasyPageInfo(5,3,"id","id"));
        for(Task t:list){
            System.out.println(t);
        }

    }
}
