package com.noah.crm.cloud.common.test;

import com.noah.crm.cloud.common.event.EventUtils;
import com.noah.crm.cloud.common.test.domain.NotifyFirstTestEvent;
import org.junit.Test;

import java.time.LocalDateTime;

public class CommonTest {



    @Test
    public void testJsonMapper() throws Exception {
        NotifyFirstTestEvent event = new NotifyFirstTestEvent("正式测试-1", LocalDateTime.now());
        String payload = EventUtils.serializeEvent(event);
        System.out.println(payload);
        event = EventUtils.deserializeEvent(payload,NotifyFirstTestEvent.class);
        System.out.println(event);
    }
}
