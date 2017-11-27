package com.noah.crm.cloud.account.context;

import org.apache.commons.configuration.BaseConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * Created by liubin on 2016/3/28.
 */
@SpringBootApplication
@Import({BaseConfiguration.class, EventConfiguration.class, SchedulerConfiguration.class, ServiceClientConfiguration.class})
public class AccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountApplication.class, args);
    }
    
}