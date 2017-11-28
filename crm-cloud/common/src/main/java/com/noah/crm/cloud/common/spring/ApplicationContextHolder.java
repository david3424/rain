package com.noah.crm.cloud.common.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author xdw9486
 */
public class ApplicationContextHolder implements ApplicationContextAware {

    public static ApplicationContext context;

    public static ApplicationConstant constant;

    public static final ApplicationContextHolder INSTANCE = new ApplicationContextHolder();

    private ApplicationContextHolder(){}

    public static ApplicationContextHolder getInstance() {
        return INSTANCE;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
        constant = applicationContext.getBean(ApplicationConstant.class);
    }
}
