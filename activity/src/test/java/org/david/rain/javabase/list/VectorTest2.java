package org.david.rain.javabase.list;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangxingang on 14-10-27.
 */
public class VectorTest2 {

    public static void main(String[] args) {
        //火车票列表
        final List<String> tickets = new ArrayList<String>();
        //初始化票据池
        for(int i=0;i<100000;i++){
            tickets.add("火车票" + i);
        }
        //10个窗口售票
        for(int i=0;i<10;i++){
            new Thread(){
                public void run() {
                    while(true){
                        System.out.println(Thread.currentThread().getId() +"——"+ tickets.remove(0));
                    }
                };
            }.start();
        }


    }
}
