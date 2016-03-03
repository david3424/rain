package org.david.rain.monitor.monitor.service.data;

import org.david.rain.monitor.monitor.domain.DataItem;
import org.david.rain.monitor.monitor.job.DataItemJobService;
import org.david.rain.monitor.monitor.job.MonitorConst;
import org.david.rain.monitor.monitor.persistence.DataItemMapper;
import org.david.rain.monitor.monitor.util.DateUtils;
import org.david.rain.monitor.monitor.util.EasyPageInfo;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by czw on 14-2-24.
 */
@Service
public class DataItemService {

    @Autowired
    DataItemMapper dataItemMapper;

    @Autowired
    DataItemJobService jobService;

    public List<DataItem> getItemPageList(EasyPageInfo pageInfo, DataItem dataItem) {
        List<DataItem> list = dataItemMapper.getAllListPage(pageInfo, dataItem);
        for (DataItem item : list) {
            item.setStatusName(MonitorConst.INFO_LEVEL_MAP.get(item.getStatus()));
            item.setJobStatusName(MonitorConst.JOB_STATUS_MAP.get(item.getJobStatus()));
        }
        return list;
    }


    public List<DataItem> getExceptionList() {
        return dataItemMapper.getExceptionList();
    }

    public List<DataItem> getDateItemList() {
        return dataItemMapper.getDataItemList();
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveDataItem(DataItem item) throws Exception {
        item.setCreateTime(DateUtils.getCurrentFormatDateTime());
        item.setChangeTime(item.getCreateTime());
        item.setStatus(DataItem.STATUS_FINE);
        item.setJobStatus(MonitorConst.JobStatus.RUNNING.value);//默认监控开启状态
        dataItemMapper.insertDataItem(item);
        jobService.addItemJobAndStart(item);
    }


    @Transactional(rollbackFor = Exception.class)
    public void updateDataItem(DataItem item) throws Exception {
        dataItemMapper.updateItemInfo(item);
        jobService.updateJob(item);
    }

    /**
     * 更新一个服务器监控的配置
     *
     * @param serverItem
     * @return 如果失败就返回0 ，成功就返回1
     * @throws org.quartz.SchedulerException JOb 相关操作异常，直接抛出，update也会被回滚
     */
    @Transactional(rollbackFor = Exception.class)
    public int updateServerItem(DataItem serverItem) throws SchedulerException {
        int re1 = dataItemMapper.updateItemInfo(serverItem);
        if (re1 > 0) {
            jobService.updateJob(serverItem);
        }
        return re1;
    }


    @Transactional(rollbackFor = Exception.class)
    public int deleteDataItem(Integer itemId) {
        int re = dataItemMapper.deleteItem(itemId);
        boolean jobSign = jobService.deleteItemJobByItemId(itemId);
        return re;
    }

    public List<DataItem> getAllDataItem() {
        return dataItemMapper.selectAllDataItem();
    }

    public DataItem getDataItemById(Integer itemId) {
        return dataItemMapper.queryDataItemById(itemId);
    }

    public DataItem getDateItemByName(String itemName) {
        return dataItemMapper.queryDataItemByName(itemName);
    }

    public int updateTurns(Integer itemId, Long turns) {
        return dataItemMapper.turnTurns(itemId, turns);
    }

    public int updateStatus(Integer itemId, Integer statusNow, Integer statusBefore) {
        return dataItemMapper.updateItemStatus(itemId, statusNow, statusBefore);
    }

    public int pauseJob(Integer itemId) {
        return dataItemMapper.updateJobStatus(itemId, MonitorConst.JobStatus.STOP.value, MonitorConst.JobStatus.RUNNING.value);
    }

    public int startJob(Integer itemId) {
        return dataItemMapper.updateJobStatus(itemId, MonitorConst.JobStatus.RUNNING.value, MonitorConst.JobStatus.STOP.value);
    }


}
