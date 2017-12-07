package com.noah.crm.cloud.coupon.context;

import com.noah.crm.cloud.common.event.config.EventConfiguration;
import com.noah.crm.cloud.common.scheduler.config.SchedulerConfiguration;
import com.noah.crm.cloud.common.spring.BaseConfiguration;
import com.noah.crm.cloud.common.spring.ServiceClientConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 */
@SpringBootApplication
@Import({BaseConfiguration.class, EventConfiguration.class, SchedulerConfiguration.class, ServiceClientConfiguration.class})
public class CouponApplication {

    public static void main(String[] args) {
        SpringApplication.run(CouponApplication.class, args);
    }

}