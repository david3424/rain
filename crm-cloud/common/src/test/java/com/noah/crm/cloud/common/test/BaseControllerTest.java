package com.noah.crm.cloud.common.test;

import com.noah.crm.cloud.common.spring.WebApplication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

/**
 */
@SpringBootTest(classes = WebApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@WebIntegrationTest(randomPort = true)
//@SpringApplicationConfiguration(classes = WebApplication.class)
public abstract class BaseControllerTest extends BaseTest {

    @Value("${local.server.port}")
    protected int serverPort;

    protected String localServerUrl() {
        return String.format("http://localhost:%d", serverPort);
    }

    protected String buildRequestUrl(String url) {
        return String.format("%s/%s", localServerUrl(), url);
    }

}
