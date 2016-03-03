package org.david.rain.monitor.monitor.service.server;

import org.david.rain.monitor.monitor.domain.RemindInfo;
import org.david.rain.monitor.monitor.job.MonitorConst;
import org.david.rain.monitor.monitor.persistence.ServerRemindMapper;
import org.david.rain.monitor.monitor.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by czw on 14-1-6.
 */
@Service
public class RemindService {

    @Autowired
    ServerRemindMapper serverRemindMapper;

    public void addRemind(RemindInfo remindInfo) {
        remindInfo.setStatus(0);
        remindInfo.setCreateTime(DateUtils.getCurrentFormatDateTime());
        serverRemindMapper.insertRemind(remindInfo);
    }

    public int updateRemind(RemindInfo remindInfo) {
        return serverRemindMapper.updateRemind(remindInfo);
    }

    public int deleteRemind(Integer id) {
        return serverRemindMapper.deleteRemind(id);
    }


    public List<RemindInfo> getRemindListByItemId(Integer itemId) {
        List<RemindInfo> returnV = serverRemindMapper.getRemindListByItem(itemId);
        for (RemindInfo remindInfo : returnV) {
            remindInfo.setTypeName(MonitorConst.REMIND_MAP.get(remindInfo.getRemindType()));
        }
        return returnV;
    }
}
