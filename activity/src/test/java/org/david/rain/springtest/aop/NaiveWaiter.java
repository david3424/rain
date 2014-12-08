package org.david.rain.springtest.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by mac on 14-12-8.
 */
public class NaiveWaiter implements Waiter {
    Logger logger = LoggerFactory.getLogger(NaiveWaiter.class);
    @Override
    public void greetTo(String name) {
        System.out.println("greetTo :" +name);
        logger.debug("greeTo:{}",name);
    }

    @Override
    public void severTo(String name) {
        System.out.println("severTo :" +name);
        logger.debug("severTo:{}",name);
    }
}
