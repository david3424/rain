package com.noah.crm.cloud.common.event.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Supplier;

/**
 */
@Service
public class EventHandlerExecutor {

    private static Logger logger = LoggerFactory.getLogger(EventHandlerExecutor.class);


    /**
     * 执行handler处理
     * @param supplier
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public <T> T executeEventHandler(Supplier<T> supplier){

        return supplier.get();

    }
}
