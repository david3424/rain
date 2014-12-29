package org.david.rain.dubbox;

import org.springframework.stereotype.Service;

/**
 */
@Service
public class DemoServiceImpl implements DemoService {

    @Override
    public String sayHello() {
        System.out.println("我是生产者");
        return "hello 生产者返回数据";
    }
}
