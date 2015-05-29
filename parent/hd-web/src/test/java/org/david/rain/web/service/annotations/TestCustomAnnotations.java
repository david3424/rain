package org.david.rain.web.service.annotations;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-1-5
 * Time: 下午1:29
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml"})
public class TestCustomAnnotations {

    @Autowired
    @Qualifier("ssw")
    TestAnnotationService testService;


    @Test
    public void testTransaction() throws Exception {
        testService.testCustomAnnotations();
    }
}
