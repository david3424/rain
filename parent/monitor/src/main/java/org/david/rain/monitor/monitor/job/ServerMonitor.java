package org.david.rain.monitor.monitor.job;

import org.david.rain.monitor.monitor.domain.DataItem;
import org.david.rain.monitor.monitor.domain.ServerItem;
import org.david.rain.monitor.monitor.service.data.DataItemService;
import org.david.rain.monitor.monitor.service.server.ItemService;
import org.david.rain.monitor.monitor.util.SpringContextSupport;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by czw on 13-12-23.
 */

@Component
@Lazy(false)
public class ServerMonitor implements InitializingBean {


    static Logger logger = LoggerFactory.getLogger(ServerMonitor.class);

    @Autowired
    ItemService itemService;

    @Autowired
    SpringContextSupport springContextSupport;


    @Autowired
    DataItemService dataItemService;


    @Autowired
    ServerItemJobService serverItemJobService;

    @Autowired
    DataItemJobService dataItemJobService;

    @Override
    public void afterPropertiesSet() {
        initServerItemJob();
        initDataItemJob();
    }

    private void initServerItemJob() {
        List<ServerItem> itemList = itemService.getAllItem();
        for (ServerItem item : itemList) {
            try {
                serverItemJobService.addItemJobAndStart(item);
            } catch (SchedulerException e) {
                //这里的异常时需要短信通知的，这个是job的初始化

                //关于异常的处理，不同的业务处理的效果不一样
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void initDataItemJob() {
        List<DataItem> itemList = dataItemService.getAllDataItem();
        for (DataItem dataItem : itemList) {
            try {
                dataItemJobService.addItemJobAndStart(dataItem);
            } catch (SchedulerException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
            }
        }
    }
}
