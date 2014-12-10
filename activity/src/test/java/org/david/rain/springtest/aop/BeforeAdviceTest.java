package org.david.rain.springtest.aop;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.aop.BeforeAdvice;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by mac on 14-12-8.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("classpath:conf/aop-test.xml")
public class BeforeAdviceTest {


    @Test
    public void testBeforeAdvice() throws Exception {
        Waiter waiter = new NaiveWaiter();
        BeforeAdvice beforeAdvice = new GreetingBeforeAdvice();
        ProxyFactory pf = new ProxyFactory();
        pf.setTarget(waiter);
        pf.addAdvice(beforeAdvice);
        Waiter waiter1 = (Waiter) pf.getProxy();
        waiter1.greetTo("david");
        waiter1.severTo("cindy");
    }

    @Test
    public void testXMLAdvice() throws Exception {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("conf/aop-test.xml");
        Waiter waiter = (Waiter) ctx.getBean("waiter");
        waiter.greetTo("david");
        waiter.severTo("cindy");
    }

    @Test
    public void testAfterAdvice() throws Exception {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("conf/aop-test.xml");
        Waiter waiter = (Waiter) ctx.getBean("waiterAfter");
        waiter.greetTo("david");
        waiter.severTo("cindy");
    }

    @Test
    public void testBothAdvice() throws Exception {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("conf/aop-test.xml");
        Waiter waiter = (Waiter) ctx.getBean("waiterInterceptor");
        waiter.greetTo("david");
        waiter.severTo("cindy");

    }
}
