package com.noah.crm.cloud.common.event.config;

import com.noah.crm.cloud.common.event.EventRegistry;
import com.noah.crm.cloud.common.spring.cloud.stream.CustomChannelBindingService;
import org.springframework.cloud.stream.binder.BinderFactory;
import org.springframework.cloud.stream.binding.*;
import org.springframework.cloud.stream.config.BindingServiceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.MessageChannel;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @author xdw9486
 */
@EnableAsync
public class EventConfiguration extends AsyncConfigurerSupport {

    @Bean
    public EventRegistry eventRegistry() {

        return new EventRegistry();

    }

    @Bean
    public BindingService bindingService(BindingServiceProperties channelBindingServiceProperties,
                                         BinderFactory binderFactory, EventRegistry eventRegistry) {

        return new CustomChannelBindingService(channelBindingServiceProperties, binderFactory, eventRegistry);

    }

    /*@Bean
    public BinderAwareChannelResolver binderAwareChannelResolver(BindingService channelBindingService,
                                                                 BindingTargetFactory binderFactory,
                                                                 DynamicDestinationsBindable dynamicDestinationsBindable) {

        return new BinderAwareChannelResolver(channelBindingService, (AbstractBindingTargetFactory<? extends MessageChannel>) binderFactory, dynamicDestinationsBindable);
    }*/

    @Bean
    public InitBindProducer initBindProducer() {

        return new InitBindProducer();
    }




    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(20);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(10000);
        executor.setThreadNamePrefix("EventExecutor-");
        executor.initialize();
        return executor;
    }

}
