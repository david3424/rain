package com.noah.crm.cloud.user.test;

import com.noah.crm.cloud.apis.event.constants.EventType;
import com.noah.crm.cloud.common.event.config.EventConfiguration;
import com.noah.crm.cloud.common.event.service.EventActivator;
import com.noah.crm.cloud.common.spring.BaseConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 */
@SpringBootApplication
@Import({BaseConfiguration.class, EventConfiguration.class})
public class TestUserApplication {


    /**
     * 测试EventBus.sendUnpublishedEvent中调用sendMessage抛出异常会不会导致整个事务回滚
     * @param eventActivator
     * @return
     */
    @Bean
    public EventActivator testEventActivator(EventActivator eventActivator) {

        return new EventActivator() {
            @Override
            public boolean sendMessage(String message, String destination) {
                //当遇到TEST_EVENT_SECOND事件时, 抛出异常
                if(destination.equals(EventType.NOTIFY_SECOND_TEST_EVENT.toString())) {
                    throw new RuntimeException("我是异常");
                }
                return eventActivator.sendMessage(message, destination);
            }

            @Override
            public void receiveMessage(Object payload) {
                eventActivator.receiveMessage(payload);
            }
        };


    }

}