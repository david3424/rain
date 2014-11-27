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

import java.sql.SQLException;
import java.util.List;

/**
 * Created by mac on 14-11-26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext.xml")
public class DbutilsTest {

    private final static Logger LOGGER = LoggerFactory.getLogger(DbutilsTest.class);
    @Autowired
    Idao  idao;

    @Test
    public void testList() throws Exception {
            List<Task> lists = idao.queryObjects(Task.class,"select * from ss_task ");
        for(Task t:lists){
            LOGGER.info("bean msg is {}",t);
        }
    }


  /*  @Test
    public void testQuery() {

        try {
            result = (int) daoImp.queryCount("select count(1) from ss_task ");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        LOGGER.info("total count in ss_task is {}",result);
    }

    @Test
    public  void testUpdate(){

        try {
            result = (int) writeDaoImp.queryCount("select max(id) from ss_task ");
            result =  writeDaoImp.update("delete from ss_task where id = ? ",result);
            LOGGER.info("delete ss_task maxid result: {}",result>=1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/
}
