package org.david.rain.springtest.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * Created by mac on 14-12-8.
 */

public class GreetingAfterAdvice implements AfterReturningAdvice {
    Logger logger = LoggerFactory.getLogger(GreetingAfterAdvice.class);

    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {

        logger.debug("afterReturning:[{}]","have a good time sir");
    }
}
