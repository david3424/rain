package org.david.rain.dubbox.consumer.controller;

import org.david.rain.dubbox.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by david on 2014/12/30.
 *
 */
@Controller
@RequestMapping(value = "/test")
public class TestController {

    @Autowired
    DemoService demoService;


    @RequestMapping(value = "test", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> test() {

        Map<String,Object> map = new HashMap<>();
        map.put("status",1);
        map.put("result",demoService.sayHello());
        return map;
    }

}
