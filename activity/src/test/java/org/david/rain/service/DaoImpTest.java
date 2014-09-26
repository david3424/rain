package org.david.rain.service;

import org.david.rain.common.repository.Idao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLException;

/**
 * Created by david on 2014/8/21.
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext.xml")
public class DaoImpTest {

@Autowired
private Idao daoImp;
@Autowired
private Idao writeDaoImp;
    private int result;
    private final static Logger LOGGER = LoggerFactory.getLogger(DaoImpTest.class);


    @Test
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
    }


    @Test
    public void testII(){
        int m = 0 ;
        for(int i=0;i<10;i++){
            System.out.println("i="+i);
         m = ++i ;
            System.out.println("m="+m);
        }
    }
}
