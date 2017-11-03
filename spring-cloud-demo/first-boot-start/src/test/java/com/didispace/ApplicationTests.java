package com.didispace;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.david.rain.boot.start.Application;
import org.david.rain.common.logback.LoggerUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * @version 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class ApplicationTests {

    private static final Log log = LogFactory.getLog(ApplicationTests.class);

    @Value("${org.david.rain.properties.test.value}")
    private String randomValue;

    // 注入启动server后的实际端口号
    @Value("${test.server.port}")
    protected int port;



    @Test
    public void test1() throws Exception {

        log.info("随机数测试输出：");
        log.info("随机字符串 : " + randomValue);
        LoggerUtil.info("端口 : " + port);

    }


}
