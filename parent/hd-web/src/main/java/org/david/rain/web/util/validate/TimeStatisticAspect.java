package org.david.rain.web.util.validate;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.util.Map;

/**
 * 用于统计json返回的时间，当然，也可以增加一个非json放回的功能，比如放到reqeust中去。
 */


@Aspect
public class TimeStatisticAspect {
    @Around("@annotation(org.david.rain.web.util.validate.TimeStatistic)")
    public Object invoke(ProceedingJoinPoint point) throws Throwable {
        long begin = System.currentTimeMillis();
        Object returnObject = point.proceed();
        long end = System.currentTimeMillis();
        @SuppressWarnings("unchecked")
        Map<String, Object> returnMap = (Map<String, Object>) returnObject;
        returnMap.put("consumingTime", end - begin);
        return returnMap;
    }
}
