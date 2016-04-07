package org.david.rain.monitor.monitor.job;

import com.google.common.collect.Sets;
import org.david.rain.monitor.monitor.domain.SendPrize;
import org.david.rain.monitor.monitor.service.server.AwardPrizesService;
import org.david.rain.monitor.monitor.service.server.SendPrizeService;
import org.david.rain.monitor.monitor.util.SpringContextSupport;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by
 * david* on 13-12-25.
 */
@Service
@DisallowConcurrentExecution
public class SendPrizeJob implements Job {

    static Logger logger = LoggerFactory.getLogger(SendPrizeJob.class);
    private static Set<Integer> set = Sets.newSetFromMap(new ConcurrentHashMap<Integer, Boolean>());
//    private static Set<String> set = Collections.newSetFromMap(new ConcurrentHashMap<String, Boolean>());


    public static final String JOB_DATA_MAP_KEY = "sendPrizeId";

    @Autowired
    AwardPrizesService awardPrizesService;

    @Autowired
    DataSource event;

    /**
     * quartz 提供了依赖注入的功能，这里的 sendPrizeId 就是从JobDataMap里面按属性名注入的
     */
//    private int sendPrizeId;
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
       /* logger.info("执行发奖逻辑JOB");
        if (!set.add(sendPrizeId)) {
            logger.info("当前逻辑未处理完，下次再来");
            return;
        }*/

        SendPrizeJob sendPrizeJob = SpringContextSupport.getSpringBean(SendPrizeJob.class);
        logger.info("sendprizeJOB:{}", sendPrizeJob);
        SendPrizeService sendPrizeService = SpringContextSupport.getSpringBean(SendPrizeService.class);
        SendPrize sendPrize = sendPrizeService.getSendPrizeById((Integer) context.getMergedJobDataMap().get(JOB_DATA_MAP_KEY));
        if (sendPrize.getStatus() == MonitorConst.JobStatus.STOP.value) {
            logger.info("发奖开关为关闭状态，直接返回");
            return;
        } else {
            logger.info("执行发奖流程---------------");
            awardPrizesService.execute(sendPrize);
        }
    }

}
