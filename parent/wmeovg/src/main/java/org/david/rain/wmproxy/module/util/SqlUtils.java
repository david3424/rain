package org.david.rain.wmproxy.module.util;

/**
 * @ClassName SqlUtils
 * @version 1.0
 * @date 2010-8-30 上午10:16:05
 */
public class SqlUtils {

	/**
	 * 
	 * @Description sql特殊字符转义
	 * @param query
	 * @return
	 */
	public static String escape(String query) {
		if (query == null) {
			return query;
		}

		return query.replace("%", "\\%").replace("_", "\\_");
	}
}
