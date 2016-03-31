package org.david.rain.monitor.util.converter;

import org.springframework.core.convert.converter.Converter;

import java.sql.Timestamp;

/**
 * 日期转字符串 
 * 
 */
public class TimestampStringConverter extends DateConverterBase implements Converter<Timestamp, String> {

    @Override
    public String convert(Timestamp source) {
        if (null == source){
            return "";
        }
        return getDateFormat().format(source);
    }

}
