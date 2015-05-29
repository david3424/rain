package org.david.rain.web.init.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-12-7
 * Time: 上午11:36
 * To change this template use File | Settings | File Templates.
 */
public class HdListener implements ServletContextListener {


    private static Logger logger = LoggerFactory.getLogger(HdListener.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        initSSOConfig();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     */
    private void initSSOConfig() {
    }
}
