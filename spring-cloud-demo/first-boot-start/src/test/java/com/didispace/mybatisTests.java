package com.didispace;

import org.david.rain.cloud.start.Application;
import org.david.rain.cloud.start.dao.mapper.KoTaskMapper;
import org.david.rain.cloud.start.pojo.KoTask;
import org.david.rain.common.logback.LoggerUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by mac on 14-11-29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@SpringBootTest(classes = Application.class)
public class mybatisTests {


    @Autowired
    KoTaskMapper koTaskMapper;


    @Test
    @Rollback
    public void testQuery() throws Exception {

        KoTask koTask = koTaskMapper.findByTaskId("33");
        LoggerUtil.info("task before info :{}", koTask);
        koTask.setRemark("test");
        int re = koTaskMapper.updateByPrimaryKeySelective(koTask);
        LoggerUtil.info("task after info :{}", koTask);

    }
}
