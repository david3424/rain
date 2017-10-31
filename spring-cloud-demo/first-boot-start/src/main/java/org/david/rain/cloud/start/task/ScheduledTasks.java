package org.david.rain.cloud.start.task;

import org.david.rain.common.logback.LoggerUtil;
import org.david.rain.common.time.ClockUtil;
import org.david.rain.common.time.DateFormatUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author xdw9486
 */
@Service
public class ScheduledTasks {

    /**
     * @ Scheduled(fixedDelay = 5000) 上次执行成功后5s
     * initialDelay=1000, fixedRate=5000 第一次延迟1秒后执行，之后按fixedRate的规则每5秒执行一次
     * (cron="*\/5 * * * * *") ：通过cron表达式定义规则
    * @Scheduled(fixedRate = 5000)
     */
    public void reportCurrentTime() {
        LoggerUtil.info("现在时间：" + DateFormatUtil.formatDate(DateFormatUtil.PATTERN_DEFAULT, ClockUtil.currentDate()));
    }
}
