package org.david.rain.monitor.monitor.service.data;

import org.david.rain.monitor.monitor.domain.DataCheckSetting;
import org.david.rain.monitor.monitor.persistence.DataCheckMapper;
import org.david.rain.monitor.monitor.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by czw on 14-2-28.
 */
@Service
public class DataCheckService {
    @Autowired
    DataCheckMapper dataCheckMapper;


    public List<DataCheckSetting> getDataCheckSettings(Integer itemid) {
        return dataCheckMapper.getCheckerByItem(itemid);
    }

    public int updateRule(DataCheckSetting checkSetting) {
        return dataCheckMapper.updateRule(checkSetting);
    }

    public int updateRuleStatus(DataCheckSetting checkSetting, int nowStatus) {
        return dataCheckMapper.updateRuleStatus(checkSetting, nowStatus);
    }

    public void saveRule(DataCheckSetting checkSetting) {
        checkSetting.setCreateTime(DateUtils.getCurrentFormatDateTime());
        dataCheckMapper.saveRule(checkSetting);
    }

    public int deleteRule(Integer itemId, String checkerName) {
        return dataCheckMapper.deleteRule(itemId, checkerName);
    }

    public List<DataCheckSetting> queryDataCheckSettingByItemId(Integer itemId) {
        return dataCheckMapper.getDataCheckSettingByItemId(itemId);
    }

}
