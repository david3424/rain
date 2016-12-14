package org.david.rain.act.outservice.webservice;

import com.noah.app.webservice.withdraw.Withdraw4Php;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by xdw9486 on 2016/12/13.
 * client of test ws
 */


public class Client {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/spring-ws-client.xml");
        Withdraw4Php withdraw4Php = (Withdraw4Php) context.getBean("withdraw");
        String response = withdraw4Php.withdraw("1000", "退款原因-到期");
        System.out.println(response);
    }
}
