package com.noah.crm.cloud.common.event.config;

import com.noah.crm.cloud.apis.event.constants.EventType;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.binding.BinderAwareChannelResolver;

import java.util.HashSet;
import java.util.Set;

/**
 * @author xdw9486
 */
public class InitBindProducer implements InitializingBean {

    @Autowired
    private BinderAwareChannelResolver binderAwareChannelResolver;

    private Set<EventType> preInitializeProducers = new HashSet<>();

    public InitBindProducer() {
        //初始化需要监听的队列
//        preInitializeProducers.add(RevokeAskEvent.EVENT_TYPE);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        preInitializeProducers.forEach(x -> binderAwareChannelResolver.resolveDestination(x.name()));
    }

    public void addPreInitializeProducers(EventType eventType) {
        preInitializeProducers.add(eventType);
    }

}
