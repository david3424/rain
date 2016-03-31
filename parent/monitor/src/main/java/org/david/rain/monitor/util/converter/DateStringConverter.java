package org.david.rain.monitor.util.converter;

import org.springframework.core.convert.converter.Converter;

import java.util.Date;

/**
 * 日期转字符串 
 * 
 */
public class DateStringConverter extends DateConverterBase implements Converter<Date, String> {

    @Override
    public String convert(Date source) {
        if (null == source){
            return "";
        }
        return getDateFormat().format(source);
    }

}
