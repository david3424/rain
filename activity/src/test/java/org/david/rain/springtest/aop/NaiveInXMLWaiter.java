package org.david.rain.springtest.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by mac on 14-12-8.
 */
public class NaiveInXMLWaiter implements Waiter {
    Logger logger = LoggerFactory.getLogger(NaiveInXMLWaiter.class);
    @Override
    public void greetTo(String name) {
        logger.debug("XML greeTo:{}",name);
    }

    @Override
    public void severTo(String name) {
        logger.debug("XML severTo:{}",name);
    }
}
