package org.david.rain.monitor.monitor.job;

import org.david.rain.monitor.monitor.service.server.MyJobFactory;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by david
 * * on 13-12-26.
 */
@Configuration
public class MonitorSchedulerContext {

    /*定义一个bean*/
    @Bean
    public MyJobFactory myJobFactory() {
        return new MyJobFactory();
    }

    public
    @Bean(name = "globalScheduler")
    Scheduler getGlobalScheduler() throws SchedulerException {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        scheduler.setJobFactory(myJobFactory()); //替换原jobFactory
        scheduler.start();
        return scheduler;
    }

}
