package org.david.rain.web.util.validate;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.david.rain.common.lang.Tuple;
import org.david.rain.web.util.hdswitch.HdSwitchSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * 通过 SwitchValidate注解完成活动开关的验证
 */

@Aspect
public class SwitchValidateAspect {
    Logger LOG = LoggerFactory.getLogger(SwitchValidateAspect.class);
    @Autowired
    private HdSwitchSupport hdSwitchSupport;

    @Around("@annotation(org.david.rain.web.util.validate.SwitchValidate)")
    public Object invoke(ProceedingJoinPoint point) throws Throwable {
        LOG.debug("switch-in:");
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();
        SwitchValidate methodValidate = method.getAnnotation(SwitchValidate.class);

        ResponseBody responseBody = method.getAnnotation(ResponseBody.class);
        if (responseBody == null) {
            throw new UnsupportedOperationException("this annotation can only use in the method" +
                    " witch annotated by @ResponseBody in @Controller");
        }

        if (methodValidate == null) {
            throw new RuntimeException("it is impossible, the cut method has no annotation of SwitchValidate");
        }


        String targetStatusKey = methodValidate.value();

        if (targetStatusKey == null || targetStatusKey.isEmpty()) {
            Object target = point.getTarget();
            Class targetClass = target.getClass();
            SwitchValidate switchControllerValidate = (SwitchValidate) targetClass.getAnnotation(SwitchValidate.class);
            if (switchControllerValidate != null) {
                targetStatusKey = switchControllerValidate.value();
            }
            if (targetStatusKey == null || targetStatusKey.isEmpty()) {
                throw new RuntimeException("you need to define the event status key in your SwitchValidate annotation");
            }
        }
        Tuple<Boolean, Map<String, Object>> result = hdSwitchSupport.isEventOpen(targetStatusKey, null);

        if (!result.l) {
            return result.r;
        }
        return point.proceed();
    }
}
