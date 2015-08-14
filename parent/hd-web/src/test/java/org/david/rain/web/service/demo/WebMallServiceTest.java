package org.david.rain.web.service.demo;

import org.david.rain.common.repository.Idao;
import org.david.rain.common.test.spring.SpringTransactionalTestCase;
import org.david.rain.web.controller.demo.WebMallService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;


/**
 * Created by user on 2015/8/13.
 *
 */
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=false)
@ContextConfiguration(locations = { "/spring/applicationContext.xml" })
public class WebMallServiceTest extends SpringTransactionalTestCase {

    private static Logger logger = LoggerFactory.getLogger(WebMallServiceTest.class);
    @Autowired
    WebMallService webMallService;


    @Autowired
    @Qualifier("commonWriterImp")
    Idao idao;
    @Test
    public void testSignIn()  {

        try {
            logger.info(webMallService.signIn("david3424").toString());
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void testIdao() throws Exception {
        String sql = "select (status)  from hex_webmall_signin_log where username=? and date=?";
        Integer bigDecimal = idao.queryScalar(sql, "david3424", "2015-08-13");
        logger.info("res:{}" ,bigDecimal);

    }
}
