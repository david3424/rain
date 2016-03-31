package org.david.rain.monitor.monitor.job;

import com.google.common.collect.Sets;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.david.rain.monitor.lang.Tuple;
import org.david.rain.monitor.monitor.domain.SendPrize;
import org.david.rain.monitor.monitor.service.server.AwardPrizesService;
import org.david.rain.monitor.monitor.service.server.SendPrizeService;
import org.david.rain.monitor.monitor.util.SpringContextSupport;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by
 * david* on 13-12-25.
 */
@Service
public class SendPrizeJob implements Job {

    static Logger logger = LoggerFactory.getLogger(SendPrizeJob.class);
    private static Set<Integer> set = Sets.newSetFromMap(new ConcurrentHashMap<Integer, Boolean>());
//    private static Set<String> set = Collections.newSetFromMap(new ConcurrentHashMap<String, Boolean>());


    public static final String JOB_DATA_MAP_KEY = "sendPrizeId";


    /**
     * quartz 提供了依赖注入的功能，这里的 sendPrizeId 就是从JobDataMap里面按属性名注入的
     */
    private int sendPrizeId;


    public int getSendPrizeId() {
        return sendPrizeId;
    }

    public void setSendPrizeId(int sendPrizeId) {
        this.sendPrizeId = sendPrizeId;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("执行发奖逻辑JOB");
        if (!set.add(sendPrizeId)) {
            logger.info("当前逻辑未处理完，下次再来");
            return;
        }
        SendPrizeService sendPrizeService = SpringContextSupport.getSpringBean(SendPrizeService.class);
        SendPrize sendPrize = sendPrizeService.getSendPrizeById(sendPrizeId);
        if (sendPrize.getStatus() == MonitorConst.JobStatus.STOP.value) {
            logger.info("发奖开关为关闭状态，直接返回");
            return;
        } else {
            logger.info("执行发奖流程---------------");
            AwardPrizesService awardPrizesService = new AwardPrizesService(sendPrize.getDatasource());
            awardPrizesService.execute();
        }
/*
        //检测逻辑
        Tuple<Integer, String> response = send(item.getItemUrl());

        ResponseChecker responseChecker = ResponseChecker.CHECKER_MAP.get(item.getReturnType());
        if (responseChecker == null) {
            logger.error("there is no response checker registed for monitor item: " + item.getItemName());
        } else {
            responseChecker.execute(item, response);
        }*/
    }

    public Tuple<Integer, String> send(String url) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        try {
            CloseableHttpResponse response = client.execute(httpGet);
            return new Tuple<>(1, EntityUtils.toString(response.getEntity()));
        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return new Tuple<>(MonitorConst.SERVER_UNREACHABLE, null);
        }
    }


}
