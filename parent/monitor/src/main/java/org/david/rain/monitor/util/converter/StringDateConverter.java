package org.david.rain.monitor.util.converter;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.util.Date;

/**
 * 字符串转日期
 *
 */
public class StringDateConverter extends DateConverterBase implements Converter<String, Date> {

    @Override
    public Date convert(String source) {
        if (source == null){
            return null;
        }
        String trim = source.trim();
        if (trim.length() == 0){
            return null;
        }
        try {
            return source.contains(":") ? getDateTimeFormat().parse(trim) : getDateFormat().parse(trim);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        finally {
            return null;
        }
    }

}
