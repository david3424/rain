package org.david.java.thread;

import java.util.concurrent.CountDownLatch;

/**
 * Created by philo on 2016/1/5.
 */
public class MessageSender implements Runnable {
    private Message message;
    private CountDownLatch latch;


    public MessageSender(Message message, CountDownLatch latch) {
        this.message = message;
        this.latch = latch;
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p/>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        System.out.println("线程:["+Thread.currentThread().getName()+"]开始处理发送消息--"+message.getId());
        try {
            Thread.sleep(1000);
            message.setStatus(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("线程:["+Thread.currentThread().getName()+"]处理消息--"+message.getId()+"--完毕！");
        latch.countDown();
    }
}
