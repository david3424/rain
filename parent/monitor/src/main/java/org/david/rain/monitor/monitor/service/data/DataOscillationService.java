package org.david.rain.monitor.monitor.service.data;

import org.david.rain.monitor.monitor.domain.OscillationCheckSetting;
import org.david.rain.monitor.monitor.domain.OscillationLog;
import org.david.rain.monitor.monitor.domain.PhaseDetail;
import org.david.rain.monitor.monitor.persistence.OscillationMapper;
import org.david.rain.monitor.monitor.util.DateUtils;
import org.david.rain.monitor.monitor.util.EasyPageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by czw on 14-3-5.
 */

@Service
public class DataOscillationService {

    static Logger logger = LoggerFactory.getLogger(DataOscillationService.class);

    @Autowired
    private OscillationMapper oscillationMapper;

    public List<OscillationCheckSetting> queryOscillationList(Integer itemId) {
        return oscillationMapper.querySettingList(itemId);
    }

    /**
     * 这里可以做的复杂点，感觉反正不是很准，我先就这么做了，以后想做准的，就分析log吧，在这边再加一套定时器就ok，难受
     *
     * @param oscillationCheckSetting
     * @return
     */
    public String caclPhaseDetail(OscillationCheckSetting oscillationCheckSetting) {
        List<PhaseDetail> details = oscillationCheckSetting.getPhaseDetailList();
        String now = DateUtils.getCurrentFormatDateTime();
        String time = now.substring(11);
        logger.warn("time:" + time);
        for (PhaseDetail detail : details) {
            logger.warn(detail.getPhaseName() + " begin:" + detail.getPhaseStart() + " end:" + detail.getPhaseEnd());
            if (time.compareTo(detail.getPhaseEnd()) < 0 && time.compareTo(detail.getPhaseStart()) >= 0) {
                return detail.getPhaseName();
            }
        }
        throw new RuntimeException("配置有问题，不可能不属于一个区");
    }

    public void addOscillationLog(OscillationLog log) {
        oscillationMapper.addOscillationLog(log);
    }

    public OscillationLog getLatestLog(Integer itemId, String attrName) {
        return oscillationMapper.getLatestLog(itemId, attrName);
    }

    public List<OscillationLog> getLatestNErrorLog(Integer n) {
        return oscillationMapper.getLastNErrorLog(n);
    }

    public List<OscillationLog> getLatestNLogByItemId(Integer itemId, String attrName, Long times, Integer n, Integer an) {
        return oscillationMapper.getLastNLogByItemId(itemId, attrName,times, n,an);
    }

    public void addOscillation(OscillationCheckSetting setting) {
        setting.setCheckStrategy(1);
        setting.setStatus(OscillationCheckSetting.STATUS_NORMAL);
        oscillationMapper.addOscillation(setting);
    }

    /**
     * 查询本时段最近两个正常值，并且总体上要求连续
     * 例如，p1 = 0-8  p2 = 9-23 9点不接昨天的23点了
     *
     * @param itemId
     * @param attrName
     * @return
     */
    public List<OscillationLog> getLatestTwoLog(Integer itemId, String attrName, String phaseDetail, Integer lowTimes) {
        return oscillationMapper.getLatestTwoLog(itemId, attrName, phaseDetail, lowTimes);
    }

    public List<OscillationLog> getFisrtTwoNormalLog(Integer itemId, String attrName) {
        return oscillationMapper.getFirstTwoNormalLog(itemId, attrName);
    }

    public List<OscillationLog> getAllListPageLog(EasyPageInfo page, OscillationLog oscillationLog) {
        return oscillationMapper.getAllListPageLog(page, oscillationLog);
    }

    public List<OscillationLog> getSearchListPage(EasyPageInfo page, String attrName) {
        return oscillationMapper.getSearchListPage(page, attrName);
    }

    public int updateOscillationAttrStatus(Integer itemId, String attrName, Integer status) {
        return oscillationMapper.updateOscillationStatus(itemId, attrName, status);
    }

}
