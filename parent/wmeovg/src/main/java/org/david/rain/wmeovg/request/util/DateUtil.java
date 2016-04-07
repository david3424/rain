package org.david.rain.wmeovg.request.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName DateUtil
 * @Description 日期时间操作
 * @version 1.0
 * @date 2010-8-6 下午02:25:35
 */
public class DateUtil {

	public static final String DATETIME = "yyyy-MM-dd HH:mm:ss";

	public static String format(final Date date, String format) {

		if (null == date) {
			return null;
		}

		SimpleDateFormat sdf = new SimpleDateFormat(format);

		return sdf.format(date);
	}
}
