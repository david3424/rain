package com.david.web.pppppp.service.quartz;

import com.david.web.pppppp.entity.SendProperty;
import com.david.web.pppppp.service.logservice.OperationLogger;
import com.david.web.pppppp.repository.SendPrizeDao;
import com.david.web.pppppp.service.ServiceException;
import com.david.web.pppppp.service.sendprize.AwardPrizesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
public class SimpleService implements Serializable {

    private static final long serialVersionUID = 122323233244334343L;
    private static final Logger logger = LoggerFactory.getLogger(SimpleService.class);
    private static Set<String> set = Collections.synchronizedSet(new HashSet<String>());

    @Autowired
    private OperationLogger operationLogger;


    public void testService(String triggerName, String group) throws Exception {

        logger.debug("发奖定时器工作:" + triggerName + "==" + group);

        WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();

        SendPrizeDao sendPrizeDao = (SendPrizeDao) wac.getBean("sendPrizeDao");

        String tablename = triggerName;
        String[] typeGroups = group.split("[|]");
        String dataSource = typeGroups.length > 1 ? typeGroups[0] : "huodong161";
        logger.debug("datascoure is " + dataSource);

        SendProperty sendProperty = sendPrizeDao.querySendProperty(tablename, dataSource);

        if (sendProperty.getStatus() == 0) {
            logger.debug("活动发奖整体被关闭," + sendProperty.getTable_name() + "(" + sendProperty.getDatasource() + ")发奖退出");
//            if (set.contains(tablename))
//                set.remove(tablename);
            return;
        }

        if (!set.add(tablename)) {
            logger.error("---------定时器启动了两次----------" + tablename);
            return;
        }

        AwardPrizesService ap = new AwardPrizesService(dataSource,operationLogger);//数据源的区分?

        ap.setSendInterface(sendProperty.getSend_interface());
        ap.setSendProperty(sendProperty);

        ap.setTableName(tablename);
        switch (Integer.parseInt(typeGroups.length > 1 ? typeGroups[1] : typeGroups[0])) {
            case 1:
                ap.setIfMz(false);
                break;
            case 2:
                ap.setIfMz(true);
                break;
            case 3:
                ap.setIfCoupon(true);
                break;
            case 4:
                ap.setIfCoupon(true);
                break;
            default:
                logger.error("---------参数错误----------");
                if (set.contains(tablename)) {
                    set.remove(tablename);
                }
                throw new ServiceException("---------throw参数错误----------");
        }
        try {
            ap.setCounts(150);
            ap.execute();
        } catch (ServiceException se) {
            logger.error(se.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Exception is " + e.getMessage());
        } finally {
            if (set.contains(tablename)) {
                set.remove(tablename);
            }
        }
    }
}
