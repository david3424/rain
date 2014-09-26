package org.david.rain.utils;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

public final class JodaUtils {
	
	/**
	 * 时间计算策略，根据需要添加
	 */
	public static enum CategoryComputeStrategy {
		ONE_HOUR {
			@Override
			public List<String> compute(DateTime start, DateTime end, DateTimeFormatter formatter) {
				List<String> list = new ArrayList<String>(16);
				while (start.isBefore(end)) {
					list.add(formatter.print(start));
					start.plusHours(1);
				}
				list.add(formatter.print(end));
				return list;
			}
		},		
		ONE_DAY {
			@Override
			public List<String> compute(DateTime start, DateTime end, DateTimeFormatter formatter) {
				List<String> list = new ArrayList<String>(16);
				while (start.isBefore(end)) {
					list.add(formatter.print(start));
					start = start.plusDays(1);
				}
				list.add(formatter.print(end));
				return list;
			}
		};
		public abstract List<String> compute(DateTime start, DateTime end, DateTimeFormatter formatter);
	}
	
	public static final String NORMAL_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	public static final String NORMAL_DATE_FORMAT = "yyyy-MM-dd";
	
	public static final String NORMAL_MONTH_FORMAT = "yyyy-MM";
	
	public static final String DEFAULT_DATE_FORMAT = "yyyyMMdd";
	

	private static DateTimeFormatter timeFormatter = DateTimeFormat.forPattern(NORMAL_TIME_FORMAT);
	
	private static DateTimeFormatter normalDateFormatter = DateTimeFormat.forPattern(NORMAL_DATE_FORMAT);
	
	private static DateTimeFormatter normalMonthFormatter = DateTimeFormat.forPattern(NORMAL_MONTH_FORMAT);
	
	private static DateTimeFormatter defaultDateFormatter = DateTimeFormat.forPattern(DEFAULT_DATE_FORMAT);
	
	private JodaUtils() {
	}
	
	public static DateTimeFormatter getTimeFormatter() {
		return timeFormatter;
	}
	
	public static DateTimeFormatter getNormalDateFormatter() {
		return normalDateFormatter;
	}
	
	public static DateTimeFormatter getNormalMonthFormatter() {
		return normalMonthFormatter;
	}
	
	public static DateTimeFormatter getDefaultDateFormatter() {
		return defaultDateFormatter;
	}

	/**
	 * 获取两个时间点之间构成的时间段列表，
	 * @param start
	 * @param end
	 * @param expectFormat 期望展现的形式
	 * @param strategy
	 * @return
	 */
	@SuppressWarnings("serial")
	public static List<String> getCategories(DateTime start, final DateTime end, String expectFormat, CategoryComputeStrategy strategy) {
		if (StringUtils.isEmpty(expectFormat)) {
			expectFormat = DEFAULT_DATE_FORMAT;
		}
		if (strategy == null) {
			strategy = CategoryComputeStrategy.ONE_DAY;
		}
		final DateTimeFormatter formatter = DateTimeFormat.forPattern(expectFormat);
		if (start == null || end == null) {
			return new ArrayList<String>(1){
				{
					add(formatter.print(new DateTime()));
				}
			};
		}
		if (start.isEqual(end) || start.isAfter(end)) {
			return new ArrayList<String>(1){
				{
					add(formatter.print(end));
				}
			};
		}
		
		return strategy.compute(start, end, formatter);
	}
}
