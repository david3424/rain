package org.david.rain.monitor.monitor.service.server;

import org.david.rain.monitor.monitor.domain.RichServerItem;
import org.david.rain.monitor.monitor.domain.ServerMonitorValue;
import org.david.rain.monitor.monitor.job.response.common.CommonResponseChecker;
import org.david.rain.monitor.monitor.persistence.ServerItemMapper;
import org.david.rain.monitor.monitor.persistence.ServerMonitorValueMapper;
import org.david.rain.monitor.monitor.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by czw on 13-12-30.
 */


@Service
public class ServerMonitorService {

    static final Logger logger = LoggerFactory.getLogger(ServerMonitorService.class);

    @Autowired
    ServerMonitorValueMapper serverMonitorValueMapper;

    @Autowired
    ServerItemMapper serverItemMapper;

    public List<ServerMonitorValue> getServerMonitorValue(Integer itemId) {
        return serverMonitorValueMapper.getServerMonitorValueByItemId(itemId);
    }


    public void addItemSettingAttr(ServerMonitorValue serverMonitorValue) {
        serverMonitorValue.setStatusBegin(DateUtils.getCurrentFormatDateTime());
        serverMonitorValue.setStatus(ServerMonitorValue.HEALTH);
        serverMonitorValue.setAbnTimes(0);
        serverMonitorValueMapper.insertServerMonitorValue(serverMonitorValue);
    }

    public int updateItemSettingAttr(ServerMonitorValue serverMonitorValue) {
        return serverMonitorValueMapper.updateMonitorValue(serverMonitorValue);
    }

    public int deleteItemSettingAttr(Integer settingId) {
        return serverMonitorValueMapper.deleteMonitorValue(settingId);
    }

    @Transactional
    public void updateMonitorValueAbnormal(Integer itemId, List<ServerMonitorValue> monitorValueList) {
        for (ServerMonitorValue serverMonitorValue : monitorValueList) {
            updateMonitorValuesAbnormal(itemId, serverMonitorValue);
        }
    }

    @Transactional
    public void updateMonitorValueWithJudge(RichServerItem item, Map<String, Integer> serverResponse,
                                            List<ServerMonitorValue> monitorValueList) {
        for (ServerMonitorValue monitorValue : monitorValueList) {
            Integer responseValue = serverResponse.get(monitorValue.getAttributeName());
            if (responseValue == null) {
                logger.error("in typecode:" + monitorValue.getTypeCode() + " there is no return for check item :" + monitorValue.getAttributeName());
                updateMonitorValuesAbnormal(item.getId(), monitorValue);
                continue;
            }
            if (!CommonResponseChecker.isMethodIllegal(monitorValue.getJudgeMethod())) {
                logger.error("do not support" + monitorValue.getJudgeMethod() + " in type :" + monitorValue.getTypeCode());
                updateMonitorValuesAbnormal(item.getId(), monitorValue);
                continue;
            }
            boolean result = CommonResponseChecker.easyCheck(monitorValue.getJudgeMethod(), monitorValue.getHealthValue(), responseValue);
            if (!result) {
                updateMonitorValuesAbnormal(item.getId(), monitorValue);
            } else {
                updateMonitorValuesHealth(item.getId(), monitorValue);
            }
        }
    }


    @Transactional
    public void changeStatusForItem(RichServerItem item, Integer nowStatus) {
        String nowTime = DateUtils.getCurrentFormatDateTime();
        serverItemMapper.backStatusLog(item.getId(), nowTime);
        serverItemMapper.updateItemStatus(item.getId(), nowTime, nowStatus);
    }

    public ServerMonitorValue getServerMonitorValue(Integer itemId, String attributeName) {
        return serverMonitorValueMapper.getServerMonitorValue(itemId, attributeName);
    }


    public int updateMonitorValuesHealth(Integer itemId, ServerMonitorValue oldValue) {
        if (oldValue.getStatus() != ServerMonitorValue.HEALTH) {
            saveMonitorValueLog(itemId, oldValue.getAttributeName(), DateUtils.getCurrentFormatDateTime());
            return updateMonitorValuesToHealth(itemId, oldValue.getAttributeName());
        }
        return 0;
    }

    public int updateMonitorValuesToHealth(Integer itemId, String attributeName) {
        return serverMonitorValueMapper.updateMonitorValueToHealth(itemId, ServerMonitorValue.HEALTH,
                attributeName, DateUtils.getCurrentFormatDateTime());
    }

    public int updateMonitorValuesToAbnormal(Integer itemId, String attributeName) {
        return serverMonitorValueMapper.updateMonitorValuesToAbnormal(itemId, attributeName,
                ServerMonitorValue.ABNORMAL, DateUtils.getCurrentFormatDateTime());
    }

    public int updateMonitorValuesAbnormal(Integer itemId, String attributeName) {
        return serverMonitorValueMapper.updateMonitorValuesAbnormal(itemId, attributeName);
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public int updateMonitorValuesAbnormal(Integer itemId, ServerMonitorValue oldValue) {
        if (oldValue.getStatus() != ServerMonitorValue.ABNORMAL) {
            saveMonitorValueLog(itemId, oldValue.getAttributeName(), DateUtils.getCurrentFormatDateTime());
            return serverMonitorValueMapper.updateMonitorValuesToAbnormal(itemId, oldValue.getAttributeName(),
                    ServerMonitorValue.ABNORMAL, DateUtils.getCurrentFormatDateTime());
        } else
            return serverMonitorValueMapper.updateMonitorValuesAbnormal(itemId, oldValue.getAttributeName());
    }


    public void saveMonitorValueLog(Integer itemId, String attributeName, String statusEndTime) {
        serverMonitorValueMapper.saveMonitorValueLog(itemId, attributeName, statusEndTime);
    }
}
