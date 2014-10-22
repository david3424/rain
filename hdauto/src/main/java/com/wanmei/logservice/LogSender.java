package com.wanmei.logservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by czw on 14-2-19.
 * <p/>
 * 这里用20个线程在queue中取了，通过hessian 发给 log服务器。
 * 1个线程肯定太慢了
 */
public class LogSender implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(LogSender.class);

    private LogCenter logCenter;

    private ILogService logService;

    public LogSender(LogCenter logCenter, ILogService logService) {
        this.logCenter = logCenter;
        this.logService = logService;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                OperationLog log = logCenter.logQueue.take();
                try {
                    logService.addLog(log);
                } catch (Exception e) {
                    logger.error("Important!!! add log to hdbase failed :" + e.getMessage());
                }
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
            }
        }
    }

    public LogCenter getLogCenter() {
        return logCenter;
    }

    public void setLogCenter(LogCenter logCenter) {
        this.logCenter = logCenter;
    }

    public ILogService getLogService() {
        return logService;
    }

    public void setLogService(ILogService logService) {
        this.logService = logService;
    }
}
