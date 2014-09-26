package org.david.rain.utils;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 时间戳与字符串转换
 * 
 * @author Administrator
 * 
 */
public class DateUtils {

	/**
	 * 默认日期格式
	 */
	public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static final String DEFAULT_FORMAT_2 = "yyyyMMdd";

	public static final String DEFAULT_FORMAT_3 = "M月d";
	
	public static final String DEFAULT_FORMAT_4 = "yyyy/MM/dd";
	
	public static final String YYYYMMDD = "yyyy-MM-dd";
	
	public static final String YYYYMMDDHM = "yyyy-MM-dd HH:mm";

	/**
	 * 默认构造函数
	 */
	private DateUtils() {
	}

	public static Map<String, String> getDateMapForChart(Integer startDate,
			Integer endDate) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		SimpleDateFormat sdf2 = new SimpleDateFormat(DEFAULT_FORMAT_2);
		SimpleDateFormat sdf3 = new SimpleDateFormat(DEFAULT_FORMAT_4);
		try {
			Date start = sdf2.parse(startDate.toString());
			Calendar c = Calendar.getInstance();
			c.setTime(start);
			String key = sdf2.format(c.getTime());
			String value = sdf3.format(c.getTime());
			map.put(key, value);
			if(startDate.intValue()<endDate.intValue()){
				boolean flag = true;
				while (flag) {
					c.add(Calendar.DATE, 1);
					if (sdf2.format(c.getTime()).equals(endDate.toString())) {
						flag = false;
					}
					key = sdf2.format(c.getTime());
					value = sdf3.format(c.getTime());
					map.put(key, value);
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return map;
	}

	public static Map<String, String> getDateMapForHour(Integer startDate,
			Integer endDate) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		SimpleDateFormat sdf2 = new SimpleDateFormat(DEFAULT_FORMAT_2);
		SimpleDateFormat sdf3 = new SimpleDateFormat(DEFAULT_FORMAT_3);
		try {
			Date start = sdf2.parse(startDate.toString());
			Calendar c = Calendar.getInstance();
			c.setTime(start);
			String key = sdf2.format(c.getTime());
			String value = sdf3.format(c.getTime());
			for (int i = 0; i < 24; i++) {
				if (i < 10) {
					key = sdf2.format(c.getTime()) + "0" + i;
				} else {
					key = sdf2.format(c.getTime()) + i;
				}
				value = sdf3.format(c.getTime()) + "日" + i + "时";
				map.put(key, value);
			}
			if(startDate.intValue()<endDate.intValue()){
				boolean flag = true;
				while (flag) {
					c.add(Calendar.DATE, 1);
					if (sdf2.format(c.getTime()).equals(endDate.toString())) {
						flag = false;
					}
					for (int i = 0; i < 24; i++) {
						if (i < 10) {
							key = sdf2.format(c.getTime()) + "0" + i;
						} else {
							key = sdf2.format(c.getTime()) + i;
						}
						value = sdf3.format(c.getTime()) + "日" + i + "时";
						map.put(key, value);
					}
				}
			}		
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 字符串转换成日期 如果转换格式为空，则利用默认格式进行转换操作
	 * 
	 * @param str
	 *            字符串
	 * @param format
	 *            日期格式
	 * @return 日期
	 * @throws java.text.ParseException
	 */
	public static Date str2Date(String str, String format) {
		if (null == str || "".equals(str)) {
			return null;
		}
		// 如果没有指定字符串转换的格式，则用默认格式进行转换
		if (null == format || "".equals(format)) {
			format = DEFAULT_FORMAT;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = sdf.parse(str);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 日期转换为字符串
	 * 
	 * @param date
	 *            日期
	 * @param format
	 *            日期格式
	 * @return 字符串
	 */
	public static String date2Str(Date date, String format) {
		if (null == date) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	/**
	 * 时间戳转换为字符串
	 * 
	 * @param time
	 * @return
	 */
	public static String timestamp2Str(Timestamp time) {
		Date date = null;
		if (null != time) {
			date = new Date(time.getTime());
		}
		return date2Str(date, YYYYMMDD);
	}

	/**
	 * 字符串转换时间戳
	 * 
	 * @param str
	 * @return
	 */
	public static Timestamp str2Timestamp(String str) {
		Date date = str2Date(str, DEFAULT_FORMAT);
		return new Timestamp(date.getTime());
	}

	/**
	 * 
	* @Title: getBeforeDay 
	* @Description:获得指定日期的前几天
	* @param date
	* @param days
	* @return
	* @throws
	 */
	public static String getBeforeDay(String date,int days){
		Date d = str2Date(date,"yyyy-MM-dd");
		Calendar cd = Calendar.getInstance();
		cd.setTime(d);
		cd.add(Calendar.DAY_OF_MONTH, days);
		Date ad = cd.getTime();
		return date2Str(ad,"yyyy-MM-dd");
	}
	
	public static Long threeDaysAgo() {
		Long time = System.currentTimeMillis() - 3 * 24 * 60 * 60 * 1000L;
		Date date = new Date(time);
		return Long.parseLong(date2Str(date, DEFAULT_FORMAT_2));
	}

	public static Long oneDaysAgo() {
		Long time = System.currentTimeMillis() - 1 * 24 * 60 * 60 * 1000L;
		Date date = new Date(time);
		return Long.parseLong(date2Str(date, DEFAULT_FORMAT_2));
	}

	public static Long dealLongTime(Long time) {
		Date date = new Date(time);
		return Long.parseLong(date2Str(date, DEFAULT_FORMAT_2));
	}
	
	
	/**
	 *
	 * @param strDate
	 * @return
	 */
	public static Date parse(String strDate) {
		return parse(strDate, null);
	}

	/**
	 * parseDate
	 *
	 * @param strDate
	 * @param pattern
	 * @return
	 */
	public static Date parse(String strDate, String pattern) {
		Date date = null;
		try {
			if(pattern == null) {
				pattern = YYYYMMDD;
			}
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			date = format.parse(strDate);
		} catch (Exception e) {
		}
		return date;
	}
	
	public static Date parse(String strDate, String pattern, Integer days) {
		Date date = null;
		try {
			if(pattern == null) {
				pattern = YYYYMMDD;
			}
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			date = format.parse(strDate);
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.DATE, days);
			date = c.getTime();
		} catch (Exception e) {
		}
		return date;
	}
	
	public static int getMonthOrQuarterOrYearByDate(String date,String type){
		Calendar c = Calendar.getInstance();
		c.setTime(DateUtils.parse(date));
		if(type.equals("y")){
			return c.get(Calendar.YEAR);
		}else if(type.equals("m")){
			return c.get(Calendar.MONTH)+1;
		}else{
			int month = c.get(Calendar.MONTH)+1;
			if(month<=3){
				return 1;
			}else if(month<=6){
				return 2;
			}else if(month<=9){
				return 3;
			}else{
				return 4;
			}
		}
	}
	public static int getDayOfYearOrQuarterOrMonthByDate(String date,String type){
		Calendar c = Calendar.getInstance();
		c.setTime(DateUtils.parse(date));
		if(type.equals("y")){
			return c.getActualMaximum(Calendar.DAY_OF_YEAR);
		}else if(type.equals("m")){
			return c.getActualMaximum(Calendar.DAY_OF_MONTH);
		}else{
			int quarter = getMonthOrQuarterOrYearByDate(date,"q");
			if(quarter==1){
				return 90;
			}else if(quarter==2){
				return 91;
			}else if(quarter==3){
				return 92;
			}else{
				return 92;
			}
		}
	}
	public static int getExsitDayOfYearOrQuarterOrMonthByDate(String date,String type){
		Calendar c = Calendar.getInstance();
		c.setTime(DateUtils.parse(date));
		if(type.equals("y")){
			return c.get(Calendar.DAY_OF_YEAR);
		}else if(type.equals("m")){
			return c.get(Calendar.DAY_OF_MONTH);
		}else{
			int days = c.get(Calendar.DAY_OF_MONTH);
			int month = c.get(Calendar.MONTH);
			while(month%3!=0){
				c.add(Calendar.MONTH, -1);
				days = days + c.getActualMaximum(Calendar.DAY_OF_MONTH);
				month = c.get(Calendar.MONTH);
			}
			return days;
		}
	}
	
	public static String getMonthOrQuarterOrYearFirstDayByDate(String date,String type){
		Calendar c = Calendar.getInstance();
		c.setTime(DateUtils.parse(date));
		if(type.equals("y")){
			return c.get(Calendar.YEAR)+"-01-01";
		}else if(type.equals("m")){
			return c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-01";
		}else{
			int month = c.get(Calendar.MONTH)+1;
			if(month<=3){
				return c.get(Calendar.YEAR)+"-01-01";
			}else if(month<=6){
				return c.get(Calendar.YEAR)+"-04-01";
			}else if(month<=9){
				return c.get(Calendar.YEAR)+"-07-01";
			}else{
				return c.get(Calendar.YEAR)+"-10-01";
			}
		}
	}
	public static String getPercent(int a,int b){
		DecimalFormat df = new DecimalFormat("#.##");
		if( b == 0 ){
			return df.format(100.0);
		}
		return df.format((double)a/b*100);
	}
	
	public static String getPercent(Double a,Double b){
		DecimalFormat df = new DecimalFormat("#.##");
		if(b == 0 || (a/b) > 1 ){
			return df.format(100.0);
		}
		return df.format(a/b*100);
	}
	
	public static String formatNumber(Double num){
		DecimalFormat df = new DecimalFormat("#.##");
		return df.format(num);
	}
	
	public static String getScale(double a,double b){
		DecimalFormat df = new DecimalFormat("#.00");
		if(b==0){
			return "0.00";
		}
		return df.format((a-b)/b*100);
	}
	/**
	* 取得当前日期是多少周
	*
	* @param date
	* @return
	*/
	public static int getWeekNumber(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setMinimalDaysInFirstWeek(7);
		c.setTime (date);

		return c.get(Calendar.WEEK_OF_YEAR);
	}
	
	/**
	 * 按月加,指定日期
	 * 
	 * @param date
	 * @param value
	 * @return
	 */
	public static Date addMonth(Date date, int value) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.add(Calendar.MONTH, value);
		return now.getTime();
	}
	
	/**
	 * 获取指定时间n个月内所有周末时间
	 * 
	 * @param n 月数
	 * @param endDate yyyy-MM-dd
	 * @return List<String> string 为 yyyy-MM-dd 格式的日期
	 */
	public static List<String> getDateListForChart(String endDate, int n) {
		List<String> list = new ArrayList<String>();
		SimpleDateFormat sdf4 = new SimpleDateFormat(YYYYMMDD);
		try {
			Date end = sdf4.parse(endDate);
			Date start = addMonth(end, n);
			Calendar c = Calendar.getInstance();
			c.setTime(end);
			list.add(endDate);
			while(true){
				c.add(Calendar.DATE, -7);
				if(c.getTime().getTime() <  start.getTime() ){
					break;
				}
				list.add(sdf4.format(c.getTime()));
			}
			Collections.sort(list);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 
	 * 获取指定日期为当年的第几天
	 * 
	 * @param date yyyy-MM-dd
	 * @return
	 */
	public static int dayOfYear(String date){
		Calendar now = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMDD);
		try {
			now.setTime(sdf.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return now.get(Calendar.DAY_OF_YEAR);
	}
	
	/**
	 * 
	 * 获取指定日为当年的第几周(注意处理周日)
	 * 
	 * @param date yyyy-MM-dd
	 * @return
	 */
	public static int weekOfYear(String dateStr){
		Calendar now = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMDD);
		try {
			Date date = sdf.parse(dateStr);
			now.setTime(date);
			if(now.get(Calendar.DAY_OF_WEEK) == 1){ //周日
				now.add(Calendar.DAY_OF_YEAR, -1); //去掉1天
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return now.get(Calendar.WEEK_OF_YEAR);
	}
	
	public static String formatDate(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMDD);
		return sdf.format(date);
	}
	
	public static String formatDate(Date date, String formatStr){
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
		return sdf.format(date);
	}
	
	/**
	 * 获取一段时间
	 * @param startDate : yyyy-MM-dd
	 * @param endDate: yyyy-MM-dd
	 * @return
	 */
	public static List<String> getDateMapForChart(String startDate,
			String endDate) {
		List<String> list = new ArrayList<String>();
		SimpleDateFormat yyyy_MM_dd = new SimpleDateFormat(YYYYMMDD); 
		SimpleDateFormat yyyyMMdd = new SimpleDateFormat(DEFAULT_FORMAT_2); 
		try {
			Date start = yyyy_MM_dd.parse(startDate);
			Date end = yyyy_MM_dd.parse(endDate);
			Calendar c = Calendar.getInstance();
			c.setTime(start);
			list.add(startDate);
			
			Integer starti = Integer.parseInt(yyyyMMdd.format(start));
			Integer endi = Integer.parseInt(yyyyMMdd.format(end));
			
			if( starti<endi ){
				boolean flag = true;
				while (flag) {
					c.add(Calendar.DATE, 1);
					String d = yyyy_MM_dd.format(c.getTime());
					if (d.equals(endDate)) {
						flag = false;
					}
					list.add(d);
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 计算出date day天之前或之后的日期
	 * 
	 * @param date
	 *            日期
	 * @param date
	 *            天数，正数为向后几天，负数为向前几天
	 * @return 返回Date日期类型
	 */
	public static Date getDateBeforeOrAfterDays(Date date, int days) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + days);
		return now.getTime();
	}
	
	public static void main(String[] args) throws Exception {
	}
}
