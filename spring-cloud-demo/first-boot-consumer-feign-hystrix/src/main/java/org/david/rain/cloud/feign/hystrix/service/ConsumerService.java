package org.david.rain.cloud.feign.hystrix.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.david.rain.cloud.feign.hystrix.web.outservice.DcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


/**
 * @author xdw9486
 */
@Service
public class ConsumerService {

    @Autowired
            @Qualifier("dcClient")
    DcClient dcClient;

    @HystrixCommand(fallbackMethod = "fallback")
    public String consumer() {
        return dcClient.consumer();
    }

    public String fallback() {
        return "fallback";
    }
}
