package org.david.rain.monitor.monitor.job;

import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by david
 * * on 13-12-26.
 */
@Configuration
public class MonitorSchedulerContext {

    /*itemid与job-key对应map*/
    public static Map<Integer, JobKey> jobKeyMap = new ConcurrentHashMap<>();
    public static Map<Integer, JobKey> dataJobKeyMap = new ConcurrentHashMap<>();

    public
    @Bean(name = "globalScheduler")
    Scheduler getGlobalScheduler() throws SchedulerException {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        scheduler.start();
        return scheduler;
    }


}
