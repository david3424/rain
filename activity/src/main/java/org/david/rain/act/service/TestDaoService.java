package org.david.rain.act.service;

import org.david.rain.act.dao.Idao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

/**
 * Created by david on 2014/11/27.
 *
 */
@Service
public class TestDaoService {

    @Autowired
    Idao idao;
    public static final String UPDATE_OBJ_SQL = " update ss_task set title = ? where id <5 ";
    Logger LOGGER = LoggerFactory.getLogger(TestDaoService.class);


    public void testUpdate(){
        int re  = 0;
        try {
            re = idao.update(UPDATE_OBJ_SQL,"new title2");
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.error("exception {} in {}",e.getMessage(),"testUpdate");
        }
        LOGGER.info("re in {} is {} ", "testUpdateObj", re);
    }
}
