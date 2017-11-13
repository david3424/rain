package org.david.rain.cloud.feign;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author xdw9486
 */
@EnableFeignClients
@SpringBootApplication
@EnableDiscoveryClient
public class Application {



    public static void main(String[] args) {

        new SpringApplicationBuilder(Application.class).web(true).run(args);
    }

}
