package org.david.rain.dubbox.provider.serivce.impl;

import org.david.rain.dubbox.DemoService;

/**
 */
public class DemoServiceImpl implements DemoService {

    @Override
    public String sayHello() {
        System.out.println("我是生产者");
        return "hello 生产者返回数据";
    }
}
