package org.david.rain.games.pay.validate;

import org.david.rain.games.pay.utils.ActionUtil;
import org.david.rain.games.pay.utils.ObjectResponseWrapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 *
 */
@Aspect
public class IpValidateAspect {
    @Around("@annotation(org.david.rain.games.pay.validate.IpValidate)")
    public Object invoke(ProceedingJoinPoint point) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();
        IpValidate adminValidate = method.getAnnotation(IpValidate.class);
        ResponseBody responseBody = method.getAnnotation(ResponseBody.class);

        String realIp = ActionUtil.getRealIp();

        if (realIp == null || !ips.contains(realIp)) {
            if (responseBody == null) {
                return "error/404";
            } else {
                return ObjectResponseWrapper.commonResponse(false, -999, "对不起，您没有权限访问这个页面");
            }
        }
        return point.proceed();
    }

    static Set<String> ips;

    static {
        ips = new HashSet<>();
        ips.add("127.0.0.1");
    }
}
