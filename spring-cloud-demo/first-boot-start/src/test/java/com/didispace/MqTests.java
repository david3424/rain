package com.didispace;

import org.david.rain.cloud.start.Application;
import org.david.rain.cloud.start.rabbit.Sender;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by mac on 14-11-29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class MqTests {

    @Autowired
    private Sender sender;

    @Test
    public void hello() throws Exception {
        sender.send();
    }

}
