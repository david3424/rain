package org.david.rain.monitor.monitor.service.time;

import org.david.rain.monitor.monitor.domain.DataTimeSetting;
import org.david.rain.monitor.monitor.domain.DateTimeItem;
import org.david.rain.monitor.monitor.persistence.TimeItemMapper;
import org.david.rain.monitor.monitor.util.DateUtils;
import org.david.rain.monitor.monitor.util.EasyPageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhangji
 * Date: 14-3-13
 * Time: 下午2:17
 * To change this template use File | Settings | File Templates.
 */
@Service
public class TimeItemService {
    @Autowired
    TimeItemMapper timeItemMapper;

    public List<DateTimeItem> getTimeItems(EasyPageInfo pageInfo, DateTimeItem itemService) {
        return timeItemMapper.getAllListPage(pageInfo, itemService);
    }

    public List<DateTimeItem> getTimeStrategyList() {
        return timeItemMapper.getAllList();
    }

    public int updateTime(DateTimeItem checkSetting) {
        return timeItemMapper.updateTime(checkSetting);
    }

    public int saveTime(DateTimeItem checkSetting) {
        checkSetting.setCreateTime(DateUtils.getCurrentFormatDateTime());
        return timeItemMapper.saveTime(checkSetting);
    }


    public List<DataTimeSetting> getTimeAttrItems(EasyPageInfo pageInfo, Integer strategyId) {
        return timeItemMapper.getAllListAttrPage(pageInfo, strategyId);
    }

    public int updateTimeAttr(DataTimeSetting checkSetting) {
        return timeItemMapper.updateTimeAttr(checkSetting);
    }

    public int saveTimeAttr(DataTimeSetting checkSetting) {
        checkSetting.setCreateTime(DateUtils.getCurrentFormatDateTime());
        return timeItemMapper.saveTimeAttr(checkSetting);
    }

}
