package org.david.rain.monitor.monitor.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * 所有用到(可能是多层关系)这个bean的lazy bean 必须依赖这个bean，要不是执行顺序会到值报错，如果这个后执行的话
 * 所以这个地方不建议使用，亲们注意
 * Created by czw on 13-12-27.
 */

@Component
@Lazy(false)
public class SpringContextSupport implements ApplicationContextAware {

    public static ApplicationContext springApplicationContext;

    public static <T> T getSpringBean(Class<T> tClass) {
        return springApplicationContext.getBean(tClass);
    }

    public static <T> T getSpringBean(String id, Class<T> tClass) {
        return springApplicationContext.getBean(id, tClass);
    }


    public static <T> T getSpringBean(String id) {
        return (T) springApplicationContext.getBean(id);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        springApplicationContext = applicationContext;
    }
}
