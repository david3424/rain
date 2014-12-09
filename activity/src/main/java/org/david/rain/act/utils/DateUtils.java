/*
*  时间： 2008-7-29 11:11:49<br>
*  北京完美时空网络技术有限公司<br>
*/
package org.david.rain.act.utils;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by IntelliJ IDEA.<br>
 * author: liliangyang<br>
 * date: 2007-12-20<br>
 * time: 17:42:48<br>
 * vision: 1.0<br>
 * description:Perfect World Project Manager System<br>
 * project:活动模块开发<br>
 */


public class DateUtils {
    // 系统默认日期格式
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    //小时分钟秒
    public static final String TIME_FORMAT = "HH:mm:ss";

    // 系统默认日起时间格式
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    // 8位日期格式
    public static final String DATE_FORMAT_8 = "yyyyMMdd";

    // 14为日期时间格式
    public static final String DATE_TIME_FORMAT_14 = "yyyyMMddHHmmss";

    public final static String YEAR = " year ";

    public final static String MONTH = " month ";

    public final static String DAY = " day ";

    public final static String WEEK = " week ";

    public final static String HOUR = " hour ";

    public final static String MINUTE = " minute ";

    public final static String SECOND = " second ";

   
    /**
     * 判断参数是否等于null或者空
     *
     * @param para
     * @return boolean
     */
    private static boolean checkPara(Object para) {
        return null == para || "" .equals(para);
    }


    /**
     * 获得系统的当前时间，毫秒.
     *
     * @return long
     */
    public static long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }


    /**
     * 获得系统的当前时间
     *
     * @return e.g.Thu Oct 12 10:25:14 CST 2006
     */
    public static Date getCurrentDate() {
        // return new Date(System.currentTimeMillis());
        return new Date(getCurrentTimeMillis());
    }

    /** */
    /**
     * 获得系统当前日期，以默认格式显示
     *
     * @return e.g.2006-10-12
     */
    public static String getCurrentFormatDate() {
        Date currentDate = getCurrentDate();
        SimpleDateFormat dateFormator = new SimpleDateFormat(DATE_FORMAT);

        return dateFormator.format(currentDate);
    }


    /**
     * 获得系统当前日期时间，以默认格式显示
     *
     * @return e.g.2006-10-12 10:55:06
     */
    public static String getCurrentFormatDateTime() {
        Date currentDate = getCurrentDate();
        SimpleDateFormat dateFormator = new SimpleDateFormat(DATE_TIME_FORMAT);

        return dateFormator.format(currentDate);
    }


     /**
     * 获得系统当前时间，以默认格式显示
     *
     * @return e.g.10:55:06
     */
    public static String getCurrentFormatTime() {
        Date currentDate = getCurrentDate();
        SimpleDateFormat dateFormator = new SimpleDateFormat(TIME_FORMAT);

        return dateFormator.format(currentDate);
    }

    /** */
    /**
     * 获得系统当前日期时间，按照指定格式返回
     *
     * @param pattern e.g.DATE_FORMAT_8 = "yyyyMMdd";
     *                DATE_TIME_FORMAT_14 = "yyyyMMddHHmmss";
     *                或者介于二者之间的格式,e.g."yyyyMMddHH"
     * @return e.g.200610121115
     */
    public static String getCurrentCustomFormatDateTime(String pattern) {
        if (checkPara(pattern)) {
            return "";
        }
        Date currentDate = getCurrentDate();
        SimpleDateFormat dateFormator = new SimpleDateFormat(pattern);

        return dateFormator.format(currentDate);
    }

    /** */
    /**
     * 输入日期，按照指定格式返回
     *
     * @param date
     * @param pattern e.g.DATE_FORMAT_8 = "yyyyMMdd";
     *                DATE_TIME_FORMAT_14 = "yyyyMMddHHmmss";
     *                或者类似于二者的格式,e.g."yyyyMMddHH"，"yyyyMM"
     * @return String
     */
    public static String formatDate(Date date, String pattern) {
        if (checkPara(pattern) || checkPara(date)) {
            return "";
        }
        SimpleDateFormat dateFormator = new SimpleDateFormat(pattern);

        return dateFormator.format(date);
    }

    /** */
    /**
     * 输入日期，按照指定格式返回
     *
     * @param date
     * @param pattern e.g.DATE_FORMAT_8 = "yyyyMMdd";
     *                DATE_TIME_FORMAT_14 = "yyyyMMddHHmmss";
     *                或者类似于二者的格式,e.g."yyyyMMddHH"，"yyyyMM"
     * @param locale  国家地区，
     *                e.g.new Locale("zh","CN","") 中国
     *                Locale.getDefault();
     * @return string
     */
    public static String formatDate(Date date, String pattern, Locale locale) {
        if (checkPara(pattern) || checkPara(date)) {
            return "";
        }
        if (checkPara(locale)) {
            locale = Locale.getDefault();
        }
        SimpleDateFormat dateFormator = new SimpleDateFormat(pattern, locale);
        String customFormatDate = dateFormator.format(date);

        return customFormatDate;
    }


    /**
     * 将时间字符串按照默认格式DATE_FORMAT = "yyyy-MM-dd"，转换为Date
     *
     * @param dateStr
     * @return Date
     */
    public static Date parseStrToDate(String dateStr) {
        if (checkPara(dateStr)) {
            return null;
        }
        SimpleDateFormat dateFormator = new SimpleDateFormat(DATE_FORMAT);
        Date resDate = dateFormator.parse(dateStr, new ParsePosition(0));
        return resDate;
    }


    /**
     * 将时间字符串按照默认格式DATE_TIME_FORMAT ="yyyy-MM-dd HH:mm:ss",转换为Date
     *
     * @param dateStr
     * @return Date
     */
    public static Date parseStrToDateTime(String dateStr) {
        if (checkPara(dateStr)) {
            return null;
        }
        SimpleDateFormat dateFormator = new SimpleDateFormat(DATE_TIME_FORMAT);
        Date resDate = dateFormator.parse(dateStr, new ParsePosition(0));
        return resDate;
    }


    /**
     * 将时间字符串按照默认格式DATE_FORMAT = "yyyy-MM-dd"，转换为Calender
     *
     * @param dateStr
     * @return Calendar
     */
    public static Calendar parseStrToCalendar(String dateStr) {
        if (checkPara(dateStr)) {
            return null;
        }
        SimpleDateFormat dateFormator = new SimpleDateFormat(DATE_TIME_FORMAT);
        Date resDate = dateFormator.parse(dateStr, new ParsePosition(0));

        Locale locale = Locale.getDefault();
        Calendar cal = new GregorianCalendar(locale);
        cal.setTime(resDate);

        return cal;
    }

    /** */
    /**
     * 将日期字符串转换成日期时间字符串
     *
     * @param dateStr
     * @return string
     */
    public static String parseDateStrToDateTimeStr(String dateStr) {
        if (checkPara(dateStr)) {
            return "";
        }
        SimpleDateFormat dateFormator = new SimpleDateFormat(DATE_FORMAT);
        Date resDate = dateFormator.parse(dateStr, new ParsePosition(0));
        return formatDate(resDate, DATE_TIME_FORMAT);
    }

    /** */
    /**
     * 将时间或者时间日期字符串按照指定格式转换为Date
     *
     * @param dateStr
     * @param pattern
     * @return Date
     */
    public static Date parseStrToCustomPatternDate(String dateStr, String pattern) {
        if (checkPara(pattern) || checkPara(dateStr)) {
            return null;
        }
        SimpleDateFormat dateFormator = new SimpleDateFormat(
                pattern);
        Date resDate = dateFormator.parse(dateStr, new ParsePosition(0));

        return resDate;
    }

    /** */
    /**
     * 将时间字符串从一种格式转换成另一种格式.
     *
     * @param dateStr     e.g. String dateStr = "2006-10-12 16:23:06";
     * @param patternFrom e.g. DatePattern.DATE_TIME_FORMAT
     * @param patternTo   e.g. DatePattern.DATE_TIME_FORMAT_14
     * @return string
     */
    public static String convertDatePattern(String dateStr,
                                            String patternFrom, String patternTo) {
        if (checkPara(patternFrom) || checkPara(patternTo) || checkPara(dateStr)) {
            return "";
        }

        SimpleDateFormat dateFormator = new SimpleDateFormat(patternFrom);
        Date resDate = dateFormator.parse(dateStr, new ParsePosition(0));
//        System.out.println("resDate = " + resDate);
        return formatDate(resDate, patternTo);
    }

    /** */
    /**
     * 日期天数增加
     *
     * @param date
     * @param days
     * @return Date
     */
    public static Date addDays(Date date, int days) {
        if (checkPara(date)) {
            return null;
        }
        if (0 == days) {
            return date;
        }
        Locale loc = Locale.getDefault();
        Calendar cal = new GregorianCalendar(loc);
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, days);

        return cal.getTime();
    }

    /** */
    /**
     * 日期天数减少
     *
     * @param date
     * @param days
     * @return Date
     */
    public static Date minusDays(Date date, int days) {
        return addDays(date, (0 - days));
    }

    /** */
    /**
     * 按时间格式相加
     *
     * @param dateStr 原来的时间
     * @param pattern 时间格式
     * @param type    "year"年、"month"月、"day"日、"week"周、
     *                "hour"时、"minute"分、"second"秒
     *                通过常量来设置,e.g.DateFormatUtils.YEAR
     * @param count   相加数量
     * @return Date
     */
    public static String addDate(String dateStr, String pattern,
                                 String type, int count) {
        if (checkPara(dateStr) || checkPara(pattern) || checkPara(type)) {
            return "";
        }
        if (0 == count) {
            return dateStr;
        }
        Date date = parseStrToCustomPatternDate(dateStr, pattern);
        Locale loc = Locale.getDefault();
        Calendar cal = new GregorianCalendar(loc);
        cal.setTime(date);

        if (YEAR.equals(type)) {
            cal.add(Calendar.YEAR, count);
        } else if (MONTH.equals(type)) {
            cal.add(Calendar.MONTH, count);
        } else if (DAY.equals(type)) {
            cal.add(Calendar.DAY_OF_MONTH, count);
        } else if (WEEK.equals(type)) {
            cal.add(Calendar.WEEK_OF_MONTH, count);
        } else if (HOUR.equals(type)) {
            cal.add(Calendar.HOUR, count);
        } else if (MINUTE.equals(type)) {
            cal.add(Calendar.MINUTE, count);
        } else if (SECOND.equals(type)) {
            cal.add(Calendar.SECOND, count);
        } else {
            return "";
        }

        return formatDate(cal.getTime(), pattern);
    }

    /** */
    /**
     * 那时间格式相减
     *
     * @param dateStr
     * @param pattern
     * @param type
     * @param count
     * @return string
     */
    public static String minusDate(String dateStr, String pattern,
                                   String type, int count) {
        return addDate(dateStr, pattern, type, (0 - count));
    }

    /** */
    /**
     * 日期大小比较
     *
     * @param dateStr1
     * @param dateStr2
     * @param pattern
     * @return int
     */
    public static int compareDate(String dateStr1, String dateStr2, String pattern) {
        if (checkPara(dateStr1) || checkPara(dateStr2) || checkPara(pattern)) {
            return 888;
        }
        Date date1 = parseStrToCustomPatternDate(dateStr1, pattern);
        Date date2 = parseStrToCustomPatternDate(dateStr2, pattern);

        return date1.compareTo(date2);
    }

    /** */
    /**
     * 获得这个月的第一天
     *
     * @param dateStr
     * @return string
     */
    public static String getFirstDayInMonth(String dateStr) {
        if (checkPara(dateStr)) {
            return "";
        }
        Calendar cal = parseStrToCalendar(dateStr);
        int firstDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, firstDay);

        return formatDate(cal.getTime(), DATE_FORMAT);
    }

    /** */
    /**
     * 获得这个月的最后一天
     *
     * @param dateStr
     * @return string
     */
    public static String getLastDayInMonth(String dateStr) {
        if (checkPara(dateStr)) {
            return "";
        }
        Calendar cal = parseStrToCalendar(dateStr);
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, lastDay);

        return formatDate(cal.getTime(), DATE_FORMAT);
    }

    /** */
    /**
     * 获得这周的第一天
     *
     * @param dateStr
     * @return string
     */
    public static String getFirstDayInWeek(String dateStr) {
        if (checkPara(dateStr)) {
            return "";
        }
        Calendar cal = parseStrToCalendar(dateStr);
        int firstDay = cal.getActualMinimum(Calendar.DAY_OF_WEEK);
        cal.set(Calendar.DAY_OF_WEEK, firstDay);

        return formatDate(cal.getTime(), DATE_FORMAT);
    }

    /** */
    /**
     * 获得这周的最后一天
     *
     * @param dateStr
     * @return string
     */
    public static String getLastDayInWeek(String dateStr) {
        if (checkPara(dateStr)) {
            return "";
        }
        Calendar cal = parseStrToCalendar(dateStr);
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_WEEK);
        cal.set(Calendar.DAY_OF_WEEK, lastDay);

        return formatDate(cal.getTime(), DATE_FORMAT);
    }


    public static void main(String[] args) {
        String dates = "2007-05-24 12:22:22";
        System.out.println("convertDatePattern(dates,DATE_TIME_FORMAT,yyyy:MM;dd) = " + convertDatePattern(dates, DATE_TIME_FORMAT, "yyyy年MM月dd日"));
//        java.text.DateFormat dft1=new SimpleDateFormat(DATE_TIME_FORMAT);
//        java.text.DateFormat dft=new SimpleDateFormat("yyyy年MM月dd日");
//        Date date=null;
//        try {
//            date=dft1.parse(dates);
//        } catch (ParseException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//
//        System.out.println("dft.parse(new Date()) = " + dft.format(date));
    }

}
