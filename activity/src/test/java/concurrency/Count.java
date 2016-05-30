package concurrency;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by mac on 16/5/29.
 * 电商系统高并发例子*
 */
public class Count {
    public AtomicInteger count = new AtomicInteger(0);

    static class Job implements Runnable {
        private CountDownLatch countDown;
        private Count count;

        public Job(Count count, CountDownLatch countDown) {
            this.count = count;
            this.countDown = countDown;
        }

        @Override
        public void run() {
            boolean isSuccess = false;
            while (!isSuccess) {
                int countValue = count.count.get();
                isSuccess = count.count.compareAndSet(countValue, countValue + 1);
            }
            countDown.countDown();
//            System.out.println("theadId:" + Thread.currentThread().getId() + ",count is : " + count.count);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDown = new CountDownLatch(1500);
        Count count = new Count();
        ExecutorService ex = Executors.newFixedThreadPool(50);
        for (int i = 0; i < 1500; i++) {
            ex.execute(new Job(count, countDown));
        }
        countDown.await();
        System.out.println(count.count);
        ex.shutdown();
    }
}
