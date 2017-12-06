package com.noah.crm.cloud.product.context;

import com.noah.crm.cloud.common.event.config.EventConfiguration;
import com.noah.crm.cloud.common.scheduler.config.SchedulerConfiguration;
import com.noah.crm.cloud.common.spring.BaseConfiguration;
import com.noah.crm.cloud.common.spring.ServiceClientConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @author xdw9486
 */
@SpringBootApplication
@Import({BaseConfiguration.class, EventConfiguration.class, SchedulerConfiguration.class, ServiceClientConfiguration.class})
public class ProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }


}