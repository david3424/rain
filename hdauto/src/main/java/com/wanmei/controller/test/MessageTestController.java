package com.wanmei.controller.test;

import com.wanmei.service.test.MessageInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: czw
 * Date: 13-5-28
 * Time: 下午3:35
 * To change this template use File | Settings | File Templates.
 */

@Controller
@RequestMapping(value = "/test/message")
public class MessageTestController {

    private static final String PASSWORD = "12!@";

    @Autowired
    private MessageInterface messageInterface;

    @RequestMapping(value = "send")
    public String send(String phone, String password, Model model,RedirectAttributes redirectAttributes) {
        if (!password.equals(PASSWORD)) {
            redirectAttributes.addFlashAttribute("message", "密码不正确！");
            return "redirect:/test/message/toSend";
        }
        String msg = "这里是hdauto测试页发送给您的短信，本次随机码：" + new Random().nextInt(100000);
        int re = messageInterface.sendMessage(msg, phone);
        if (re == 200) {
            model.addAttribute("message",msg);
            return "test/message/sendresult";
        } else {
            redirectAttributes.addFlashAttribute("message", "短信发送失败！错误代码：【" + re + "】");
            return "redirect:/test/message/toSend";
        }
    }

    @RequestMapping(value = "toSend", method = RequestMethod.GET)
    public String message() {
        return "test/message/sendtest";
    }
}
