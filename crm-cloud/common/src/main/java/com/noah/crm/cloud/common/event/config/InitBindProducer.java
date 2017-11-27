package com.noah.crm.cloud.common.event.config;

import com.noah.crm.cloud.apis.event.constants.EventType;
import com.noah.crm.cloud.apis.event.domain.AskResponseEvent;
import com.noah.crm.cloud.apis.event.domain.RevokeAskEvent;
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
        preInitializeProducers.add(AskResponseEvent.EVENT_TYPE);
        preInitializeProducers.add(RevokeAskEvent.EVENT_TYPE);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        preInitializeProducers.stream().forEach(x -> binderAwareChannelResolver.resolveDestination(x.name()));
    }

    public void addPreInitializeProducers(EventType eventType) {
        preInitializeProducers.add(eventType);
    }

}
