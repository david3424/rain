package org.david.rain.web.util.customannotation;

import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created with IntelliJ IDEA.
 * User: ssw
 * Date: 13-12-24
 * Time: 下午5:03
 * The best part of using custom annotations is that you don't have to make any configuration,
 * Spring will auto detect that these are transactional component and everything will work fine.
 * Annotations are a very small feature added in Spring but are very userful.
 * use customized annotations 抛异常事务回滚注解，更方便,减少参数输入;
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Transactional(rollbackFor = Exception.class)
public @interface TransactionalForExceptionRollback {

}
