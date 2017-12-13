package com.didispace;

import org.david.rain.boot.start.Application;
import org.david.rain.boot.start.dao.mapper.king.KoTaskMapper;
import org.david.rain.boot.start.dao.mapper.oracle.PayFundMapper;
import org.david.rain.boot.start.pojo.KoTask;
import org.david.rain.common.logback.LoggerUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

/**
 * Created by mac on 14-11-29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class mybatisTests {


    @Autowired
    KoTaskMapper koTaskMapper;

    @Autowired
    PayFundMapper payFundMapper;

    @Autowired
    private CacheManager cacheManager;



    @Test
    @Rollback
    public void testQuery() throws Exception {

        KoTask koTask;
        koTask = koTaskMapper.findByTaskId("33");
        LoggerUtil.info("task before info :{}", koTask);
        LoggerUtil.info("task cache info :{}", cacheManager.getCache("tasks"));
//        koTask.setRemark("test");
//        int re = koTaskMapper.updateByPrimaryKeySelective(koTask);
//        LoggerUtil.info("task after info :{}", koTask);
    }

    @Test
    public void testFundcodeQuery() throws Exception {

        Map<String, Object> resultMap = payFundMapper.findByFundcode("050003");
        LoggerUtil.info("pay_product  info :{}", resultMap);
    }

}
