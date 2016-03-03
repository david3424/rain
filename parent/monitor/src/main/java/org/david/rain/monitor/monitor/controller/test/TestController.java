package org.david.rain.monitor.monitor.controller.test;

import org.david.rain.monitor.hdinterface.ShortMessageInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by czw on 14-2-25.
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    @Qualifier("shortMessageInterface")
    ShortMessageInterface shortMessageInterface;

    @RequestMapping("message")
    public String testMessage(Model model) {
        boolean flag = shortMessageInterface.sendMessageWithType("test", "18901273995", "测试motnitor链接短信接口发短信。");
        model.addAttribute("flag",flag);
        return "/test/test";
    }
}
