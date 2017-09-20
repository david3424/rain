package org.david.rain.common.logback;

import ch.qos.logback.classic.pattern.ClassOfCallerConverter;
import ch.qos.logback.classic.spi.CallerData;
import ch.qos.logback.classic.spi.ILoggingEvent;

/**
 * @author hxx9048
 * @since 2017/5/15
 */
public class MyClassOfCallerConverter extends ClassOfCallerConverter {

    protected String getFullyQualifiedName(ILoggingEvent event) {

        StackTraceElement[] cda = event.getCallerData();
        if (cda != null && cda.length > 0) {
            if (LoggerUtil.class.getName().equals(cda[0].getClassName()) && cda.length >= 2) {
                return cda[1].getClassName();
            }
            return cda[0].getClassName();
        } else {
            return CallerData.NA;
        }
    }
}
