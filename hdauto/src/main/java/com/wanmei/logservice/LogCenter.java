package com.wanmei.logservice;

import com.wanmei.webservice.ServiceManage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by czw on 14-2-19.
 * 不开线程，存log会阻塞发奖，这个非常的不好 ，所以这里都交由LogCenter来处理这个问题
 * 每次请求都丢到这个队列中去，让后LogCenter有10个发送员来发送这个这个东西，不过可以用线程池的技术
 * 发送元可以多弄几个，都丢到线程池里面去执行，控制线程池的大小即可
 */

@Component
public class LogCenter {

    private static Logger logger = LoggerFactory.getLogger(LogCenter.class);

    public LinkedBlockingQueue<OperationLog> logQueue = new LinkedBlockingQueue<OperationLog>();

    private ILogService logService;

    public LogCenter() {
        logService = ServiceManage.logService;

        for (int i = 0; i < 20; i++) {
            LogSender logSender = new LogSender(this, logService);
            Thread sendThread = new Thread(logSender);
            sendThread.setDaemon(true); //其实这个也没有什么用，反正就是让这个东西不阻碍tomcat程序的结束？一般启动脚本都是直接干掉的
            sendThread.start();
        }
    }

    public void addLog(OperationLog log) {
        if (logQueue.size() > 2000) {
            logger.error("now the log queue is full loading, so log drop this log operation");
        }
        try {
            logQueue.put(log);
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    public ILogService getLogService() {
        return logService;
    }

    public void setLogService(ILogService logService) {
        this.logService = logService;
    }
}
