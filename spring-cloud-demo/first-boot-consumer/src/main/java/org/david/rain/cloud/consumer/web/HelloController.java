package org.david.rain.cloud.consumer.web;


import org.david.rain.common.logback.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author xdw9486
 */
@RestController
public class HelloController {

    @Autowired
    LoadBalancerClient loadBalancerClient;
    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/consumer")
    public String dc() {
        ServiceInstance serviceInstance = loadBalancerClient.choose("client-service-start");
        String url = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/dc";
        LoggerUtil.info("consumer url is {}", url);
        return restTemplate.getForObject(url, String.class);
    }
    @GetMapping("/consumer-lb")
    public String dcLb() {
        return restTemplate.getForObject("http://client-service-start/dc", String.class);
    }


    @RequestMapping("/hello")
    public String index() {
        return "Hello World";
    }

}