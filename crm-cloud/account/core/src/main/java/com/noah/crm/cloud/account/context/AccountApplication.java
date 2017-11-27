package com.noah.crm.cloud.account.context;

import com.noah.crm.cloud.common.event.config.EventConfiguration;
import com.noah.crm.cloud.common.scheduler.config.SchedulerConfiguration;
import com.noah.crm.cloud.common.spring.BaseConfiguration;
import com.noah.crm.cloud.common.spring.ServiceClientConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * @author xdw9486
 */
@SpringBootApplication
@ComponentScan({"com.noah.crm.cloud.**.service", "com.noah.crm.cloud.**.web"})
@Import({BaseConfiguration.class, EventConfiguration.class, SchedulerConfiguration.class, ServiceClientConfiguration.class})
public class AccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountApplication.class, args);
    }
    
}