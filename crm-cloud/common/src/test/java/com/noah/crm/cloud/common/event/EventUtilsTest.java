package com.noah.crm.cloud.common.event;

import com.noah.crm.cloud.common.test.domain.NotifyFirstTestEvent;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


/**
 */
public class EventUtilsTest {


    @Test
    public void testSerialize() {

        NotifyFirstTestEvent testEventFirst = new NotifyFirstTestEvent("张三", LocalDateTime.now());

        String json = EventUtils.serializeEvent(testEventFirst);

        NotifyFirstTestEvent testEventFirstFromJson = EventUtils.deserializeEvent(json, NotifyFirstTestEvent.class);

        assertThat(testEventFirstFromJson, is(testEventFirst));


    }

}
