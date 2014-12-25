package org.david.rain.dubbox;


import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 */
public class DubboProvider {

    public static void main(String[] args){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"application.xml"});
        context.start();

        System.out.println("press any key to exit");

        try {
            System.in.read();
        }catch (IOException e){
            e.printStackTrace();
        }
    }



}
