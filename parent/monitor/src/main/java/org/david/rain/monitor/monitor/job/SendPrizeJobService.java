package org.david.rain.monitor.monitor.job;

import org.david.rain.monitor.monitor.domain.SendPrize;
import org.david.rain.monitor.monitor.domain.ServerItem;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by david
 * * on 13-12-27.
 */
@Component
public class SendPrizeJobService {

    public static String JOB_GROUP_NAME = "prize_send_group";


    static Logger logger = LoggerFactory.getLogger(SendPrizeJobService.class);

    @Autowired
    Scheduler scheduler;

    public boolean deleteSendPrizeJob(SendPrize sendPrize) {
        try {
            return scheduler.deleteJob(MonitorConst.sendPrizeJobKeyMap.get(sendPrize.getId()));
        } catch (SchedulerException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public void updateJob(SendPrize sendPrize) throws SchedulerException {
        deleteSendPrizeJob(sendPrize);
        addsendPrizeJobAndStart(sendPrize);
    }


    /**
     * 添加一个item的job
     *
     * @param sendPrize 发奖配置
     * @throws SchedulerException
     */
    public void addsendPrizeJobAndStart(SendPrize sendPrize) throws SchedulerException {
        /*利用jobdetail 自带的jobDataMap 传递severId*/
        JobDetail job = JobBuilder.newJob(SendPrizeJob.class).withIdentity(sendPrize.getTableName(), JOB_GROUP_NAME).usingJobData(SendPrizeJob.JOB_DATA_MAP_KEY, sendPrize.getId()).build();
        Trigger trigger = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule(sendPrize.getJobCron())).forJob(job).build();
        scheduler.scheduleJob(job, trigger);
        MonitorConst.sendPrizeJobKeyMap.put(sendPrize.getId(), job.getKey()); //方便通过prizeId操作Job
    }
}
