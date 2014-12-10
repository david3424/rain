package org.david.rain.springtest.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by mac on 14-12-8.
 */

public class GreetingInterceptorAdvice implements MethodInterceptor {
    Logger logger = LoggerFactory.getLogger(GreetingInterceptorAdvice.class);


    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Object[] args = methodInvocation.getArguments();
        String clientName = (String) args[0];
        logger.debug("How are u :" + clientName);
        Object obj = methodInvocation.proceed();
        logger.debug("afterReturning:[{}]","have a good time sir");
        return obj;
    }
}
