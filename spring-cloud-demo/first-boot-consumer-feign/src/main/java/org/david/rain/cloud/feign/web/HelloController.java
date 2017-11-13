package org.david.rain.cloud.feign.web;


import org.david.rain.cloud.feign.web.outservice.DcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xdw9486
 */
@RestController
public class HelloController {


    @Autowired
    DcClient dcClient;
    @GetMapping("/consumer")
    public String dc() {
        return dcClient.consumer();
    }
    @RequestMapping("/hello")
    public String index() {
        return "Hello World";
    }

}