package com.david.web.pppppp.service.logservice;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by czw on 14-2-18.
 * <p/>
 * 本平台的补发log没有记录，额，如果记录需要查表，但是补发又是大批量的，暂时没有加
 * 每次发送的时候查prize表是可以的，不要一次和gid查询到的时候一期查了，内存消耗太大
 */

@Component
public class OperationLogger {


    @Autowired
    private LogCenter logCenter;

    public static Map<String, Integer> gidMap = new HashMap<String, Integer>();

    static {

        gidMap.put("world2", 1);

        gidMap.put("wulin", 9);
        gidMap.put("wulin2", 9);

        gidMap.put("w2i", 10);

        gidMap.put("zhuxian", 11);
        gidMap.put("zhuxian2", 11);

        gidMap.put("chibi", 12);
        gidMap.put("rwpd", 13);

        gidMap.put("kdxy", 14);

        gidMap.put("mhzx2", 15);
        gidMap.put("mhzx", 15);

        gidMap.put("sgcq", 18);

        gidMap.put("shenmo", 19);

        gidMap.put("xlzj", 22);
        gidMap.put("sgsj", 25);

        gidMap.put("sdxl", 26);

        gidMap.put("xa", 27);
        gidMap.put("xajh", 27);

        gidMap.put("seiya", 28);


        gidMap.put("sw", 40);

        gidMap.put("xlc", 31);
        gidMap.put("xljz", 31);

        /*如果有识之士看到，请完善一下上面不完善的游戏id信息*/

    }

    public void log(Integer logType,SendProperty sendProperty, SendPrizeBean sendPrizeBean,Integer status, Integer detail) {
        ObjectMapper objectMapper = new ObjectMapper();
        String params = null;
        try {
            params = objectMapper.writeValueAsString(sendPrizeBean);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String result = status.toString();
        OperationLog log = com.david.web.pppppp.service.logservice.OperationLog.LogBuilder.init(getGidByPrizeTableName(sendProperty.getTable_name()), sendProperty.getTable_name(),logType)
                .setCreatetime(DateUtils.getCurrentFormatDateTime())
                .setUsername(sendPrizeBean.getUsername())
                .setParams(params).setResult(status)
                .setResultDetail(detail.toString())
                .build();
        logCenter.addLog(log);
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
