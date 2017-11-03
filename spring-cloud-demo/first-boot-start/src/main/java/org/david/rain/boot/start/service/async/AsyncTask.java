package org.david.rain.boot.start.service.async;

import org.apache.commons.lang3.RandomUtils;
import org.david.rain.common.logback.LoggerUtil;
import org.david.rain.common.time.ClockUtil;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

/**
 * @author xdw9486
 */
@Service
public class AsyncTask {

    /**
     * @throws Exception
     */
    @Async
    public Future<String> doTaskOne() throws Exception {
        LoggerUtil.info("开始做任务一");
        long start = System.currentTimeMillis();
        Thread.sleep(RandomUtils.nextInt(10000, 10000));
        LoggerUtil.info("完成任务一，耗时：" + ClockUtil.elapsedTime(start) + "毫秒");
        return new AsyncResult<>("完成任务一");
    }

    /**
     * @throws Exception
     */
    @Async
    public Future<String> doTaskTwo() throws Exception {
        LoggerUtil.info("开始做任务二");
        long start = System.currentTimeMillis();
        Thread.sleep(RandomUtils.nextInt(10000, 10000));
        LoggerUtil.info("完成任务二，耗时：" + ClockUtil.elapsedTime(start) + "毫秒");
        return new AsyncResult<>("完成任务二");
    }

    @Async
    public Future<String> doTaskThree() throws Exception {
        LoggerUtil.info("开始做任务三");
        long start = System.currentTimeMillis();
        Thread.sleep(RandomUtils.nextInt(10000, 10000));
        LoggerUtil.info("完成任务三，耗时：" + ClockUtil.elapsedTime(start) + "毫秒");
        return new AsyncResult<>("完成任务三");
    }
}
