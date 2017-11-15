package org.david.rain.boot.start.web;

import org.david.rain.common.logback.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xdw9486
 */
@RestController
public class DcController {


    @Autowired
    DiscoveryClient discoveryClient;

    @GetMapping("/dc")
    public String dc() {
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            LoggerUtil.error("sleep exception:{}", e);
        }
        String services = "Services: " + discoveryClient.getServices();
        LoggerUtil.info("sleep services are :{}", services);
        return services;
    }
}
