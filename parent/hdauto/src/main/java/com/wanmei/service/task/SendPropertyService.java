package com.david.web.wanmei.service.task;

import com.david.web.wanmei.common.CommonList;
import com.david.web.wanmei.entity.SendProperty;
import com.david.web.wanmei.repository.SendPrizeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by czw on 13-12-16.
 */
@Component
public class SendPropertyService {

    @Autowired
    SendPrizeDao sendPrizeDao;

    public CommonList<SendProperty> getAllSendProperty(int pageSize, int pageNo, String tableName) throws Exception {
        return sendPrizeDao.getAllSendProperty(pageSize, pageNo, tableName);
    }

    public SendProperty querySendProperty(String tablename, String dataSource) throws Exception {
        return sendPrizeDao.querySendProperty(tablename, dataSource);
    }

    public int closeSwitch(String prizeTable) throws Exception {
        return sendPrizeDao.closeSwitch(prizeTable);
    }

    public int openSwitch(String prizeTable) throws Exception {
        return sendPrizeDao.openSwitch(prizeTable);
    }


    public int closeSwitch(String prizeTable, String datasource) throws Exception {
        return sendPrizeDao.closeSwitch(prizeTable, datasource);
    }

    public int openSwitch(String prizeTable, String datasource) throws Exception {
        return sendPrizeDao.openSwitch(prizeTable, datasource);
    }

    public int closeAll() throws Exception {
        return sendPrizeDao.closeMainSwitch();
    }

    public int openAll() throws Exception {
        return sendPrizeDao.openMainSwitch();
    }
}
