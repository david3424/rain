package org.david.rain.cloud.feign.hystrix.web;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.david.rain.cloud.feign.hystrix.service.ConsumerService;
import org.david.rain.cloud.feign.hystrix.web.outservice.DcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xdw9486
 */
@RestController
public class HelloController {


    @Autowired
    @Qualifier("dcClient")
    DcClient dcClient;
    @Autowired
    ConsumerService consumerService;

    @GetMapping("/consumer")
    public String dc() {
        return consumerService.consumer();
    }

    @GetMapping("/consumer-feign")
    public String dcc() {
        return dcClient.consumer();
    }

    @RequestMapping("/hello")
    public String index() {
        return "Hello World";
    }

}