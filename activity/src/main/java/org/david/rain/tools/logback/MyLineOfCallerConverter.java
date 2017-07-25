package org.david.rain.tools.logback;

import ch.qos.logback.classic.pattern.LineOfCallerConverter;
import ch.qos.logback.classic.spi.CallerData;
import ch.qos.logback.classic.spi.ILoggingEvent;

/**
 * @author hxx9048
 * @since 2017/5/15
 */
public class MyLineOfCallerConverter extends LineOfCallerConverter {
    public String convert(ILoggingEvent le) {
        StackTraceElement[] cda = le.getCallerData();
        if (cda != null && cda.length > 0) {
            if (LoggerUtil.class.getName().equals(cda[0].getClassName()) && cda.length >= 2) {
                return Integer.toString(cda[1].getLineNumber());
            }
            return Integer.toString(cda[0].getLineNumber());
        } else {
            return CallerData.NA;
        }
    }
}
