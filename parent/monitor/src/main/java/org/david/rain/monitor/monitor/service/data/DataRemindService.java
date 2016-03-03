package org.david.rain.monitor.monitor.service.data;

import org.david.rain.monitor.monitor.domain.RemindInfo;
import org.david.rain.monitor.monitor.job.MonitorConst;
import org.david.rain.monitor.monitor.persistence.DataRemindMapper;
import org.david.rain.monitor.monitor.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by czw on 14-3-12.
 */

@Service
public class DataRemindService {
    @Autowired
    DataRemindMapper dataRemindMapper;

    public void addRemind(RemindInfo remindInfo) {
        remindInfo.setStatus(0);
        remindInfo.setCreateTime(DateUtils.getCurrentFormatDateTime());
        dataRemindMapper.insertRemind(remindInfo);
    }

    public int updateRemind(RemindInfo remindInfo) {
        return dataRemindMapper.updateRemind(remindInfo);
    }

    public int deleteRemind(Integer id) {
        return dataRemindMapper.deleteRemind(id);
    }


    public List<RemindInfo> getRemindListByItemId(Integer itemId) {
        List<RemindInfo> returnV = dataRemindMapper.getRemindListByItem(itemId);
        for (RemindInfo remindInfo : returnV) {
            remindInfo.setTypeName(MonitorConst.REMIND_MAP.get(remindInfo.getRemindType()));
        }
        return returnV;
    }
}
