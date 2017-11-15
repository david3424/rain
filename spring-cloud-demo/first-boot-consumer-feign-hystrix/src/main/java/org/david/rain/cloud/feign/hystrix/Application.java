package org.david.rain.cloud.feign.hystrix;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author xdw9486
 */
@EnableHystrixDashboard
@EnableFeignClients
@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrix
public class Application {


    public static void main(String[] args) {

        new SpringApplicationBuilder(Application.class).web(true).run(args);
    }

}
