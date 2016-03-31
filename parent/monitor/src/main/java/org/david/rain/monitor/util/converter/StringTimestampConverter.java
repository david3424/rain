package org.david.rain.monitor.util.converter;

import org.springframework.core.convert.converter.Converter;

import java.sql.Timestamp;
import java.text.ParseException;

/**
 * 字符串转日期
 *
 */
public class StringTimestampConverter extends DateConverterBase implements Converter<String, Timestamp> {

    @Override
    public Timestamp convert(String source) {
        if (source == null){
            return null;
        }
        String trim = source.trim();
        if (trim.length() == 0){
            return null;
        }
        try {
            return new Timestamp((source.contains(":") ? getDateTimeFormat().parse(trim) : getDateFormat().parse(trim)).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        finally {
            return null;
        }
    }

}
