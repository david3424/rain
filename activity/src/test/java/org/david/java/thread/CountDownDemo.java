package org.david.java.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by philo on 2016/1/5.
 */
public class CountDownDemo {


    public static void main(String[] args) {
        List<Message> messageList = initMessageList();

        CountDownLatch latch = new CountDownLatch(messageList.size());
        ExecutorService executor = Executors.newFixedThreadPool(5);
        for (Message message : messageList) {
            executor.execute(new MessageSender(message, latch));
        }
        try {
            latch.await();
            System.out.println("所有任务执行完毕，处理成功消息" + checkMessageList(messageList));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }


    }


    /**
     * 检查是否全部完成任务
     * *
     */
    private static int checkMessageList(List<Message> messageList) {
        int success = 0;
        for (Message message : messageList) {
            if (message.getStatus() == 1) {
                success++;
            }
        }
        return success;
    }


    /**
     * 初始化任务列表
     * *
     */
    private static List<Message> initMessageList() {
        List<Message> messageList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Message message = new Message((long) i, 0);
            messageList.add(message);
        }
        return messageList;
    }

}
