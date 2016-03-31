package org.david.rain.monitor.monitor.job;

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
public class ServerItemJobService {

    public static String JOB_GROUP_NAME = "server_monitor_group";


    static Logger logger = LoggerFactory.getLogger(ServerItemJobService.class);

    @Autowired
    Scheduler scheduler;

    public boolean deleteItemJob(ServerItem serverItem) {
        try {
            return scheduler.deleteJob(MonitorConst.jobKeyMap.get(serverItem.getId()));
        } catch (SchedulerException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteItemJobByItemId(Integer itemId) {

        try {
            return scheduler.deleteJob(MonitorConst.jobKeyMap.get(itemId));
        } catch (SchedulerException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public void updateJob(ServerItem serverItem) throws SchedulerException {
        deleteItemJob(serverItem);
        addItemJobAndStart(serverItem);
    }


    /**
     * 添加一个item的job*
     *
     * @param serverItem 某个服务item
     * @throws SchedulerException
     */
    public void addItemJobAndStart(ServerItem serverItem) throws SchedulerException {
        /*利用jobdetail 自带的jobDataMap 传递severId*/
        JobDetail job = JobBuilder.newJob(ServerItemJob.class).withIdentity(serverItem.getItemName(), JOB_GROUP_NAME).usingJobData(ServerItemJob.JOB_DATA_MAP_KEY, serverItem.getId()).build();
        Trigger trigger = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule(serverItem.getJobCron())).forJob(job).build();
        scheduler.scheduleJob(job, trigger);
        MonitorConst.jobKeyMap.put(serverItem.getId(), job.getKey());
    }

}
