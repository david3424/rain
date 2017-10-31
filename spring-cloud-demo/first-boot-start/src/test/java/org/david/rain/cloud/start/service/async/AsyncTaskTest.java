package org.david.rain.cloud.start.service.async;

import org.david.rain.cloud.start.Application;
import org.david.rain.common.logback.LoggerUtil;
import org.david.rain.common.time.ClockUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.Future;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class AsyncTaskTest {

    @Autowired
    AsyncTask asyncTask;

    @Test
    public void doTask() throws Exception {
        long start = System.currentTimeMillis();
        Future<String> task1 = asyncTask.doTaskOne();
        Future<String> task2 = asyncTask.doTaskTwo();
        Future<String> task3 = asyncTask.doTaskThree();
        while (true) {
            if (task1.isDone() && task2.isDone() && task3.isDone()) {
                // 三个任务都调用完成，退出循环等待
                break;
            }
            Thread.sleep(1000);
        }
        LoggerUtil.info("async eclipse time:{}", ClockUtil.elapsedTime(start));
    }
}