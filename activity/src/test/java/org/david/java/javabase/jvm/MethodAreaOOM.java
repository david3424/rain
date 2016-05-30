package org.david.java.javabase.jvm;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by mac on 16/5/23.
 * *
 */

/**方法区溢出 -XX:PermSize=20m -XX:MaxPermSize=20m
 * 
 */
public class MethodAreaOOM {


    public static void main(String[] args) throws Exception {

        while (true) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(HeapOOM.class);
            enhancer.setUseCache(false);
            enhancer.setCallback(new MethodInterceptor() {
                @Override
                public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
                    return methodProxy.invokeSuper(o, args);
                }
            });
            enhancer.create();
        }
    }
}
