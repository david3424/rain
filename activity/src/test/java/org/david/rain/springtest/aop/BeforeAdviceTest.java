package org.david.rain.springtest.aop;

import org.junit.Test;
import org.springframework.aop.BeforeAdvice;
import org.springframework.aop.framework.ProxyFactory;

/**
 * Created by mac on 14-12-8.
 */

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
}
