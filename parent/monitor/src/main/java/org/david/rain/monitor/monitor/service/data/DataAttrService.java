package org.david.rain.monitor.monitor.service.data;

import org.david.rain.monitor.monitor.domain.DataAttrLog;
import org.david.rain.monitor.monitor.domain.DataCheckLog;
import org.david.rain.monitor.monitor.persistence.DataAttrMapper;
import org.david.rain.monitor.monitor.domain.DataAttrSetting;
import org.david.rain.monitor.monitor.domain.DataCheckSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by czw on 14-2-24.
 */

@Service
public class DataAttrService {

    @Autowired
    private DataAttrMapper dataAttrMapper;


    public List<DataAttrSetting> queryDataAttrSettingByItemId(Integer itemId) {
        return dataAttrMapper.getDataAttrSettingByItemId(itemId);
    }

    public List<DataCheckSetting> queryDataCheckSettings(Integer itemId) {
        return dataAttrMapper.getDataCheckSetting(itemId);
    }

    @Transactional(rollbackFor = Exception.class)
    public int insertDataCheckerLog(List<DataCheckLog> logs) {
        for (DataCheckLog log : logs) {
            dataAttrMapper.insertDataCheckLog(log);
        }
        return logs.size();
    }

    public int insertDataAttrSetting(DataAttrSetting setting) {
        setting.setStatus(DataAttrSetting.USE);
        return dataAttrMapper.insertDataAttr(setting);
    }

    public int deleteDataAttrSetting(Integer itemId, String attrName) {
        return dataAttrMapper.deleteDataAttr(itemId, attrName);
    }

    public int insertDataCheckerLog(DataCheckLog log) {
        return dataAttrMapper.insertDataCheckLog(log);
    }

    @Transactional(rollbackFor = Exception.class)
    public int insertDataAttrLog(List<DataAttrLog> logs) {
        for (DataAttrLog log : logs) {
            dataAttrMapper.insertAttrLog(log);
        }
        return logs.size();
    }

}
