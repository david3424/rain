package com.noah.crm.cloud.order.context;

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
import org.springframework.context.annotation.Import;

/**
 * @author xdw9486
 */
@SpringBootApplication
@Import({BaseConfiguration.class, EventConfiguration.class, SchedulerConfiguration.class,
        ServiceClientConfiguration.class, WebApplication.class})
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

    @Bean
    public InitBindProducer initBindProducer() {

        InitBindProducer initBindProducer = new InitBindProducer();
        initBindProducer.addPreInitializeProducers(EventType.ASK_REDUCE_BALANCE);
        initBindProducer.addPreInitializeProducers(EventType.ASK_USE_COUPON);
        return initBindProducer;
    }


}