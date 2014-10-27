package org.david.rain.example.list;

import java.util.List;
import java.util.Random;
import java.util.Vector;

/**
 * Created by zhangxingang on 14-10-27.
 */
public class VectorTest1 {

    public static void main(String[] args) {
        // 火车票列表
        final List<String> tickets = new Vector<String>();
        // 初始化票据池
        for (int i = 0; i < 100000; i++) {
            tickets.add("火车票" + i);
        }

        // 退票
        Thread returnThread = new Thread() {
            public void run() {
                while (true) {
                    tickets.add("车票" + new Random().nextInt());
                }
            };
        };

        // 售票
        Thread saleThread = new Thread() {
            public void run() {
                for (String ticket : tickets) {
                    tickets.remove(ticket);
                }
            };
        };

        // 启动退票线程
        returnThread.start();
        // 启动售票线程
        saleThread.start();

    }
}
