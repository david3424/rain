package com.noah.crm.cloud.common.scheduler.config;

import com.noah.crm.cloud.common.event.scheduler.EventScheduler;
import com.noah.crm.cloud.common.event.service.EventBus;
import com.noah.crm.cloud.common.scheduler.ZkCoordinateScheduledExecutor;
import com.noah.crm.cloud.common.scheduler.ZkSchedulerCoordinator;
import com.noah.crm.cloud.common.spring.ApplicationConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * @author xdw9486
 */
@EnableScheduling
public class SchedulerConfiguration implements SchedulingConfigurer {


    @Bean
    public ZkSchedulerCoordinator zkSchedulerCoordinator(ApplicationConstant applicationConstant){

        return new ZkSchedulerCoordinator(applicationConstant);

    }

    @Bean
    public EventScheduler eventScheduler(EventBus eventBus) {
        return new EventScheduler(eventBus);
    }


    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {

        TaskScheduler taskScheduler = new ConcurrentTaskScheduler(new ZkCoordinateScheduledExecutor(5));
        taskRegistrar.setTaskScheduler(taskScheduler);

    }


}
