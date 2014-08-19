package org.david.rain.common.util.memcached;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Created by david on 13-11-25.
 * memcache 缓存 注解化
 */

@Aspect
@Component
public class MemcacheableAspect {

    static final Logger logger = LoggerFactory.getLogger(MemcacheableAspect.class);

    @Autowired
    MemcachedManager memcachedManager;
    ExpressionParser parser = new SpelExpressionParser();

    @Around("@annotation(org.david.rain.common.util.memcached.Memcacheable)")
    public Object invoke(ProceedingJoinPoint point) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();
        Memcacheable memcacheable = method.getAnnotation(Memcacheable.class);
        String key;
        if (memcacheable.key().isEmpty()) {
            key = getDefaultKey(point);
        } else {
            String targetName = point.getTarget().getClass().getName();
            String methodName = point.getSignature().getName();
            StandardEvaluationContext context = new StandardEvaluationContext();
            String[] names = methodSignature.getParameterNames();
            Object[] args = point.getArgs();
            for (int i = 0; i < names.length; i++) {
                context.setVariable(names[i], args[i]);
            }
            Expression expression = parser.parseExpression(memcacheable.key());
            key = targetName + methodName + expression.getValue(context, String.class);
        }
        logger.debug(key);
        Object object = memcachedManager.get(key);
        if (object == null) {
            object = point.proceed();
            memcachedManager.add(key, object, memcacheable.expire());
        }
        return object;
    }

    private String getDefaultKey(ProceedingJoinPoint point) {
        String targetName = point.getTarget().getClass().getName();
        String methodName = point.getSignature().getName();
        Object[] arguments = point.getArgs();
        StringBuilder sb = new StringBuilder();
        sb.append(targetName).append(".").append(methodName);
        if ((arguments != null) && (arguments.length != 0)) {
            for (Object argument : arguments) {
                sb.append(".").append(argument);
            }
        }
        return sb.toString();
    }
}
