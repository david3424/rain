package org.david.rain.common.components.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 活动时间计算工具类
 * Date: 12-12-4
 * Time: 下午1:43
 */
public class DateCountUtil {
    /**
     * Date格式
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    /**
     * DateTime格式
     */
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final SimpleDateFormat DATE_FORMATER = new SimpleDateFormat(
            DATE_FORMAT);
    private static final SimpleDateFormat DATE_TIME_FORMATER = new SimpleDateFormat(
            DATE_TIME_FORMAT);

    /**
     * 系统当前时间与time比较
     *
     * @param time 传入的时间字符串 "yyyy-MM-dd"或者"yyyy-MM-dd HH:mm:ss"
     * @param unit 返回的时间单位
     * @return 相差的天数/小时/分钟/秒数根据 TimeUnit来判断
     */
    public static long timeDifference(String time, TimeUnit unit) {
        long currTime = new Date().getTime();
        long paramTime = parseTime(time);
        return unit.convert(currTime - paramTime, TimeUnit.MILLISECONDS);
    }

    /**
     * 计算时间差 endTime - startTime
     * @param startTime
     * @param endTime
     * @param unit 返回的时间单位
     * @return 相差的天数/小时/分钟/秒数根据 TimeUnit来判断
     */
    public static long timeDifference(String startTime, String endTime, TimeUnit unit) {
        return unit.convert(parseTime(endTime) - parseTime(startTime), TimeUnit.MILLISECONDS);
    }

    private static long parseTime(String time) {
        try {
            return time.length() <= 11 ? DATE_FORMATER.parse(time)
                    .getTime() : DATE_TIME_FORMATER.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("转换日期字符串错误");
        }
    }
}
