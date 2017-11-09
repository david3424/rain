package org.david.rain.cloud.start;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author xdw9486
 */
@SpringBootApplication
@EnableAsync
@EnableCaching
@EnableEurekaServer
public class Application {

    public static void main(String[] args) {
/* SpringApplication.run(Application.class, args); */

        new SpringApplicationBuilder(Application.class).web(true).run(args);
    }


}
