package org.david.rain.web.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
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
