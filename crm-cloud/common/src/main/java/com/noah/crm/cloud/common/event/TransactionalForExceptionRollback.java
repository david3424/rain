package com.noah.crm.cloud.common.event;

import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
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
