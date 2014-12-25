package org.david.rain.dubbox;

/**
 */
public class DemoServiceImpl implements DemoService {

    @Override
    public String sayHello() {
        System.out.println("我是生产者");
        return "hello 生产者返回数据";
    }
}
