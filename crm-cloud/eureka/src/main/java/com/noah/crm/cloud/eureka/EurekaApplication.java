package com.noah.crm.cloud.eureka;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author xdw9486
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaApplication {

    public static void main(String[] args) {
/* SpringApplication.run(Application.class, args); */

        new SpringApplicationBuilder(EurekaApplication.class).web(true).run(args);
    }


}
