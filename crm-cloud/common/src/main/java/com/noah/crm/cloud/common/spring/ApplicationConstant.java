package com.noah.crm.cloud.common.spring;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author xdw9486
 */
public class ApplicationConstant {

    @Value("${spring.cloud.stream.kafka.binder.zkNodes:}")
    public String zkAddress;

    @Value("${spring.application.name}")
    public String applicationName;

    @Value("${spring.application.index}")
    public int applicationIndex;

}
