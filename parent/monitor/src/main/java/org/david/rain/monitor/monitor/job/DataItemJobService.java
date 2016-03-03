package org.david.rain.monitor.monitor.job;

import org.david.rain.monitor.monitor.domain.DataItem;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by czw on 14-2-24.
 */
@Service
public class DataItemJobService {


    Logger logger = LoggerFactory.getLogger(DataItemJobService.class);

    public static final String JOB_GROUP_NAME = "data_monitor_job";

    @Autowired
    Scheduler scheduler;


    public boolean deleteItemJob(DataItem dataItem) {
        try {
            return scheduler.deleteJob(MonitorSchedulerContext.dataJobKeyMap.get(dataItem.getId()));
        } catch (SchedulerException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteItemJobByItemId(Integer itemId) {
        try {
            return scheduler.deleteJob(MonitorSchedulerContext.dataJobKeyMap.get(itemId));
        } catch (SchedulerException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public void updateJob(DataItem dataItem) throws SchedulerException {
        deleteItemJob(dataItem);
        addItemJobAndStart(dataItem);
    }

    public void addItemJobAndStart(DataItem dataItem) throws SchedulerException {
        JobDetail job = JobBuilder.newJob(DataItemJob.class).withIdentity(dataItem.getItemName(), JOB_GROUP_NAME)
                .usingJobData(DataItemJob.JOB_DATA_MAP_KEY, dataItem.getId())
                .build();
        Trigger trigger = TriggerBuilder.newTrigger().
                withSchedule(CronScheduleBuilder.cronSchedule(dataItem.getJobCron()))
                .forJob(job).build();
        scheduler.scheduleJob(job, trigger);
        MonitorSchedulerContext.dataJobKeyMap.put(dataItem.getId(), job.getKey());
    }
}
