package com.wanmei.service.test;

import com.wanmei.webservice.ServiceManage;
import com.wanmei.webservice.textmessage.server.webservice.WSTextMessageBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: czw
 * Date: 13-5-28
 * Time: 下午4:29
 * To change this template use File | Settings | File Templates.
 */

@Component
public class MessageService implements MessageInterface {

    private static Logger logger = LoggerFactory.getLogger(MessageService.class);

    @Override
    public int sendMessage(String msg, String... phones) {

        if (phones == null || phones.length == 0) {
            throw new IllegalArgumentException("电话号码忘记传了，肯定不能发短信了。");
        }
        WSTextMessageBean wt = new WSTextMessageBean();
        wt.setClientId("hd-test");
        wt.setServiceId(new Integer(11));
        wt.setMessageContent(msg);
        wt.setMessageType("test");
        List ll = new ArrayList();
        for (String phone : phones) {
            ll.add(phone);
        }
        wt.setPassports(ll);
        boolean result_ll = WSTextMessageBean.validate(wt);

        if (!result_ll)
            return 300; //验证不通过

        try {
            return ServiceManage.wsTextMessageService.sendSimple(wt);
        } catch (Exception e) {
            logger.error("message exception: " + e.getMessage());
            e.printStackTrace();
            return -200;//接口报错
        }
    }
}
