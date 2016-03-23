package org.david.rain.monitor.monitor.job.response.common;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.david.rain.monitor.hdinterface.ShortMessageInterface;
import org.david.rain.monitor.lang.Tuple;
import org.david.rain.monitor.monitor.domain.RemindInfo;
import org.david.rain.monitor.monitor.domain.RichServerItem;
import org.david.rain.monitor.monitor.domain.ServerMonitorValue;
import org.david.rain.monitor.monitor.job.MonitorConst;
import org.david.rain.monitor.monitor.job.response.ResponseChecker;
import org.david.rain.monitor.monitor.service.server.RemindService;
import org.david.rain.monitor.monitor.service.server.ServerMonitorService;
import org.david.rain.monitor.monitor.util.DateUtils;
import org.david.rain.monitor.monitor.util.SpringContextSupport;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by david
 * * on 13-12-25.
 */
public class CommonResponseChecker extends ResponseChecker {

    static Logger logger = org.slf4j.LoggerFactory.getLogger(CommonResponseChecker.class);

    public static final String TYPE_CODE = "common";


    private ServerMonitorService monitorService;

    private ShortMessageInterface shortMessageInterface;
    private RemindService remindService;

    @Override
    public void execute(RichServerItem item, Tuple<Integer, String> response) {

        List<ServerMonitorValue> monitorValueList = processPreCheck(item);
        check(item, response, monitorValueList);
        proccessAfterCheck(item);
    }

    /**
     * 对监控项的返回值按监控设置来判断是否异常，并保存到数据库。
     *
     * @param item
     * @param response
     * @param monitorValueList
     */
    private void check(RichServerItem item, Tuple<Integer, String> response, List<ServerMonitorValue> monitorValueList) {
        if (response.l == MonitorConst.SERVER_UNREACHABLE) {
            monitorService.updateMonitorValueAbnormal(item.getId(), monitorValueList);
        } else {
            String json = response.r;
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Integer> serverResponse = null;
            try {
                serverResponse = mapper.readValue(json, new TypeReference<Map<String, Object>>() {
                });
            } catch (IOException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
                monitorService.updateMonitorValueAbnormal(item.getId(), monitorValueList);
            }
            if (serverResponse == null) {
                monitorService.updateMonitorValueAbnormal(item.getId(), monitorValueList);
            } else {
                monitorService.updateMonitorValueWithJudge(item, serverResponse, monitorValueList);
            }
        }
    }

    private void proccessAfterCheck(RichServerItem item) {
        int level = 1;
        List<ServerMonitorValue> monitorValueList = monitorService.getServerMonitorValue(item.getId());
        for (ServerMonitorValue serverMonitorValue : monitorValueList) {
            if (serverMonitorValue.getAbnLevel() == MonitorConst.InfoLevel.ERROR.value) { //目前只有两个报警界别
                if (serverMonitorValue.getAbnTimes() >= 2) {
                    if (level < MonitorConst.InfoLevel.WARN.value)
                        level = MonitorConst.InfoLevel.WARN.value;
                }
                if (serverMonitorValue.getAbnTimes() >= 5) {
                    if (level < MonitorConst.InfoLevel.ERROR.value)
                        level = MonitorConst.InfoLevel.ERROR.value;
                }
            } else {
                if (serverMonitorValue.getAbnTimes() >= 5) {
                    if (level < MonitorConst.InfoLevel.WARN.value)
                        level = MonitorConst.InfoLevel.WARN.value;
                }
            }
        }
        if (item.getStatus() != level) {
            if (item.getStatus() < level) {
                logger.warn("item :" + item.getItemName() + " status change to " + level + " from " + item.getStatus());
            } else {
                logger.info("item :" + item.getItemName() + " status change to " + level + " from " + item.getStatus());
            }
            monitorService.changeStatusForItem(item, level);
            sendRemind(item, level);
        }
    }

    private void sendRemind(RichServerItem item, int nowLevel) {
        String message = "服务监控项 '" + item.getItemName() + "'(" + item.getItemNameCh() + ")状态由'" + MonitorConst.INFO_LEVEL_MAP.get(item.getStatus()) + "' 变为'"
                + MonitorConst.INFO_LEVEL_MAP.get(nowLevel) + "'，当前时间：" + DateUtils.getCurrentFormatDateTime();
        List<RemindInfo> remindInfos = remindService.getRemindListByItemId(item.getId());
        for (RemindInfo remindInfo : remindInfos) {
            if (remindInfo.getRemindType() == MonitorConst.REMIND_TYPE_MESSAGE) {
                shortMessageInterface.sendMessageWithType(MonitorConst.MESSAGE_KEY, remindInfo.getPhone(), message);
            }
        }
    }

    private List<ServerMonitorValue> processPreCheck(RichServerItem item) {

        if (monitorService == null) {
            monitorService = SpringContextSupport.getSpringBean(ServerMonitorService.class);
        }

        if (shortMessageInterface == null) {
            shortMessageInterface = SpringContextSupport.getSpringBean(ShortMessageInterface.class);
        }

        if (remindService == null) {
            remindService = SpringContextSupport.getSpringBean(RemindService.class);
        }
        return monitorService.getServerMonitorValue(item.getId());
    }


    public static boolean easyCheck(String method, Integer settingValue, Integer value) {
        if (method.equals("=")) {
            return value.equals(settingValue);
        } else if (method.equals(">"))
            return value > settingValue;
        else if (method.equals(">="))
            return value >= settingValue;
        else if (method.equals("<"))
            return value < settingValue;
        else if (method.equals("<="))
            return value <= settingValue;
        else {
            throw new RuntimeException("unSupport method:" + method);
        }
    }

    public static boolean isMethodIllegal(String method) {
        if (method.equals("=")) {
            return true;
        } else if (method.equals(">"))
            return true;
        else if (method.equals(">="))
            return true;
        else if (method.equals("<"))
            return true;
        else if (method.equals("<="))
            return true;
        else {
            return false;
        }
    }

}
