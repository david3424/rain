package org.david.rain.monitor.monitor.job;

import org.david.rain.monitor.lang.Tuple;
import org.david.rain.monitor.monitor.domain.RichServerItem;
import org.david.rain.monitor.monitor.job.response.ResponseChecker;
import org.david.rain.monitor.monitor.service.server.ItemService;
import org.david.rain.monitor.monitor.util.SpringContextSupport;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Created by
 * david* on 13-12-25.
 */

public class ServerItemJob implements Job {

    static Logger logger = LoggerFactory.getLogger(ServerItemJob.class);

    public static final String JOB_DATA_MAP_KEY = "serverItemId";


    /**
     * quartz 提供了依赖注入的功能，这里的 serverItemId 就是从JobDataMap里面按属性名注入的
     */
    private int serverItemId;


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
//        logger.info("执行job任务");
        ItemService itemService = SpringContextSupport.getSpringBean(ItemService.class);
        RichServerItem item = itemService.getRichServerItemById(serverItemId);

        if (item.getJobStatus() == MonitorConst.JobStatus.STOP.value) {
            return;
        }

        //检测逻辑
        Tuple<Integer, String> response = send(item.getItemUrl());

        ResponseChecker responseChecker = ResponseChecker.CHECKER_MAP.get(item.getReturnType());
        if (responseChecker == null) {
            logger.error("there is no response checker registed for monitor item: " + item.getItemName());
        } else {
            responseChecker.execute(item, response);
        }
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

  /*  public int getServerItemId() {
        return serverItemId;
    }

    public void setServerItemId(int serverItemId) {
        this.serverItemId = serverItemId;
    }*/
}
