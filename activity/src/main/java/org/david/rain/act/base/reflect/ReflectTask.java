package org.david.rain.act.base.reflect;

import org.david.rain.act.entity.Task;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by mac on 14-12-1.
 */
public class ReflectTask {

    public static Task initByDefaultConst() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {

        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        System.out.println("currend loader:"+loader);
        System.out.println("parent loader:"+loader.getParent());
        System.out.println("root loader:"+loader.getParent().getParent());
        Class clazz = loader.loadClass("org.david.rain.act.entity.Task");
//        Constructor constructor = clazz.getDeclaredConstructor((Class[]) null);//可以不用构造函数，直接用clazz实例化
        Task task = (Task) clazz.newInstance();
        Method setTitle = clazz.getMethod("setTitle",String.class);
        setTitle.invoke(task,"test");
        Method setId = clazz.getMethod("setId",Long.class);
        setId.invoke(task,11l);
        Field field = clazz.getDeclaredField("description");
        field.setAccessible(true);//需要设置
        field.set(task,"测试描述信息");
        return task;
    }


}
