package org.david.rain.springtest.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * Created by mac on 14-12-8.
 */

public class GreetingBeforeAdvice implements MethodBeforeAdvice {
    Logger logger = LoggerFactory.getLogger(GreetingBeforeAdvice.class);
    @Override
    public void before(Method method, Object[] args, Object o) throws Throwable {
        String clientName = (String)args[0];
        logger.debug("How are u :" + clientName);
    }
}
