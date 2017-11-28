package com.noah.crm.cloud.common.event.config;

import com.noah.crm.cloud.common.event.EventRegistry;
import org.springframework.cloud.stream.binder.BinderFactory;
import org.springframework.cloud.stream.binding.BinderAwareChannelResolver;
import org.springframework.cloud.stream.binding.DynamicDestinationsBindable;
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

    /*@Bean
    public ChannelBindingService bindingService(ChannelBindingServiceProperties channelBindingServiceProperties,
                                                BinderFactory<MessageChannel> binderFactory, EventRegistry eventRegistry) {

        return new CustomChannelBindingService(channelBindingServiceProperties, binderFactory, eventRegistry);

    }*/

   /* @Bean
    public BinderAwareChannelResolver binderAwareChannelResolver(ChannelBindingService channelBindingService,
                                                                 BindableChannelFactory bindableChannelFactory,
                                                                 DynamicDestinationsBindable dynamicDestinationsBindable) {

        return new BinderAwareChannelResolver(channelBindingService, bindableChannelFactory,
                dynamicDestinationsBindable);
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
