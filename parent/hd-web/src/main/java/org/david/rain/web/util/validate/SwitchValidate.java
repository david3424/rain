package org.david.rain.web.util.validate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 开关验证注解，属性 loginValidate 默认是需要登录验证的，如果想不登录验证，请用@LoginValidate(0)
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface SwitchValidate {
    public String value() default "";

}
