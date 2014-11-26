package org.david.rain.act.utils.entity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 默认数据库链接
 * User: david
 * Date: 12-4-10
 * Time: 上午11:15
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DefaultDBLink {
    /**
     * 对应数据库链接
     * @return 数据库链接字符串
     */
	String value() default "test";
}
