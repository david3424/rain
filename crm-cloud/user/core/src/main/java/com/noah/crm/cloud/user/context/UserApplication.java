package com.noah.crm.cloud.user.context;

import com.noah.crm.cloud.apis.event.constants.EventType;
import com.noah.crm.cloud.common.event.config.EventConfiguration;
import com.noah.crm.cloud.common.event.config.InitBindProducer;
import com.noah.crm.cloud.common.scheduler.config.SchedulerConfiguration;
import com.noah.crm.cloud.common.spring.BaseConfiguration;
import com.noah.crm.cloud.common.spring.ServiceClientConfiguration;
import com.noah.crm.cloud.common.spring.WebApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 */
@SpringBootApplication
@ComponentScan({"com.noah.crm.cloud.**.service", "com.noah.crm.cloud.**.web"})
@Import({BaseConfiguration.class, EventConfiguration.class, SchedulerConfiguration.class,
        ServiceClientConfiguration.class, WebApplication.class})
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

    @Bean
    public InitBindProducer initBindProducer() {

        InitBindProducer initBindProducer = new InitBindProducer();
        initBindProducer.addPreInitializeProducers(EventType.USER_CREATED);
        return initBindProducer;
    }


}