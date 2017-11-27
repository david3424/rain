package com.noah.crm.cloud.common.spring;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 */
@EnableDiscoveryClient
@EnableFeignClients("com.akkafun.**.service")
public class ServiceClientConfiguration {
/*
    @Bean(destroyMethod = "shutdown")
    @org.springframework.cloud.context.config.annotation.RefreshScope
    public EurekaClient eurekaClient(ApplicationInfoManager manager,
                                     EurekaClientConfig config,
                                     DiscoveryClient.DiscoveryClientOptionalArgs optionalArgs,
                                     ApplicationContext context) {
        manager.getInfo(); // force initialization
        return new CloudEurekaClient(manager, config, optionalArgs, context);
    }*/


}
