package com.noah.crm.cloud.zuul;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author xdw9486
 */
@EnableZuulProxy
@EnableEurekaClient
@SpringBootApplication
public class Application {



    public static void main(String[] args) {

        new SpringApplicationBuilder(Application.class).web(true).run(args);
    }

}
