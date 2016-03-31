package org.david.rain.service.logservice;

import org.codehaus.jackson.map.ObjectMapper;
import org.david.rain.monitor.monitor.domain.SendPrize;
import org.david.rain.monitor.monitor.util.DateUtils;
import org.david.rain.monitor.monitor.vo.PrizeBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * <p/>
 */

@Component
public class OperationLogger {

    @Autowired
    private LogCenter logCenter;

    public static Map<String, Integer> gidMap = new HashMap<String, Integer>();

    static {
        gidMap.put("game1", 1);
        gidMap.put("game2", 2);
        gidMap.put("lottery", 9);
    }

    public void log(Integer logType, SendPrize sendProperty, PrizeBean prizeBean, Integer status, Integer detail) {
        ObjectMapper objectMapper = new ObjectMapper();
        String params = null;
        try {
            params = objectMapper.writeValueAsString(prizeBean);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //build模式得出LOG
        OperationLog log = OperationLog.LogBuilder.init(getGidByPrizeTableName(sendProperty.getTableName()), sendProperty.getTableName(), logType)
                .setCreatetime(DateUtils.getCurrentFormatDateTime()).setUsername(prizeBean.getUserName()).setParams(params).setResult(status).setResultDetail(detail.toString())
                .build();
        logCenter.addLog(log);//队列处理

    }

    private String getGameNameByPrizeTableName(String tableName) {
        int end = tableName.indexOf('_');
        return tableName.substring(0, end);
    }

    private Integer getGidByPrizeTableName(String tableName) {
        Integer gid = gidMap.get(getGameNameByPrizeTableName(tableName));
        if (gid == null)
            return 0;
        return gid;
    }
}
