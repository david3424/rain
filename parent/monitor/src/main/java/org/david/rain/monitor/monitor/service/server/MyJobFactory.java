package org.david.rain.monitor.monitor.service.server;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.AdaptableJobFactory;

/**
 * Created by mac on 16/4/5.
 * 继承原jobFactory,把新生成的job放入spring容器*
 */
//public class MyJobFactory extends AdaptableJobFactory implements ApplicationContextAware {
public class MyJobFactory extends AdaptableJobFactory {


    //    ApplicationContext applicationContext;
    @Autowired
    AutowireCapableBeanFactory capableBeanFactory;

    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        //调用父类的方法  
        Object jobInstance = super.createJobInstance(bundle);
//        AutowireCapableBeanFactory aaa = applicationContext.getAutowireCapableBeanFactory();
//        aaa.autowireBeanProperties(jobInstance, AutowireCapableBeanFactory.AUTOWIRE_BY_NAME, false);
        //进行注入,这属于Spring的技术,不清楚的可以查看Spring的API.  
        capableBeanFactory.autowireBean(jobInstance);
        return jobInstance;
    }

   /* @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }
*/
}
