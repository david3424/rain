package org.david.rain.monitor.monitor.service.server;

import org.david.rain.monitor.monitor.domain.RichServerItem;
import org.david.rain.monitor.monitor.domain.ServerItem;
import org.david.rain.monitor.monitor.domain.TypeSetting;
import org.david.rain.monitor.monitor.job.MonitorConst;
import org.david.rain.monitor.monitor.job.ServerItemJobService;
import org.david.rain.monitor.monitor.persistence.ServerItemMapper;
import org.david.rain.monitor.monitor.persistence.ServerMonitorValueMapper;
import org.david.rain.monitor.monitor.persistence.TypeSettingMapper;
import org.david.rain.monitor.monitor.util.DateUtils;
import org.david.rain.monitor.monitor.util.EasyPageInfo;
import org.david.rain.monitor.monitor.domain.ServerMonitorValue;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by czw on 13-12-13.
 */
@Service
public class ItemService {
    @Autowired
    ServerItemMapper serverItemMapper;


    @Autowired
    ServerItemJobService serverItemJobService;

    @Autowired
    ServerMonitorValueMapper monitorValueMapper;//因为没有xml报错，忽视


    @Autowired
    TypeSettingMapper typeSettingMapper;


    /**
     * 服务器监控主页分页显示使用，由于对easyui的json怎么处理还不熟悉，现在显示的中文都在这里显示，方便
     *
     * @param pageInfo
     * @param serverItem
     * @return
     */
    public List<ServerItem> getItemPageList(EasyPageInfo pageInfo, ServerItem serverItem) {
        List<ServerItem> list = serverItemMapper.getAllListPage(pageInfo, serverItem);
        for (ServerItem item : list) {
            item.setStatusName(MonitorConst.INFO_LEVEL_MAP.get(item.getStatus()));
            item.setJobStatusName(MonitorConst.JOB_STATUS_MAP.get(item.getJobStatus()));
        }
        return list;
    }


    public List<ServerItem> getExceptionList() {
        return serverItemMapper.getExceptionList();
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveServerItem(ServerItem serverItem) throws Exception {
        serverItem.setCreateTime(DateUtils.getCurrentFormatDateTime());
        serverItem.setChangeTime(DateUtils.getCurrentFormatDateTime());
        serverItem.setJobStatus(ServerItem.JobStatus.STOP.value);
        serverItem.setStatus(MonitorConst.InfoLevel.HEALTH.value);
        saveNewServerItemToDB(serverItem);
        serverItemJobService.addItemJobAndStart(serverItem);
    }

    private void saveNewServerItemToDB(ServerItem serverItem) {
        serverItemMapper.saveItem(serverItem);
        List<TypeSetting> settingList = typeSettingMapper.getTypeSettingsByTypeCode(serverItem.getReturnType());

        for (TypeSetting setting : settingList) {
            monitorValueMapper.insertServerMonitorValueBySetting(serverItem.getId(), setting,
                    DateUtils.getCurrentFormatDateTime(), ServerMonitorValue.HEALTH);
        }
    }


    /**
     * 更新一个服务器监控的配置
     *
     * @param serverItem
     * @return 如果失败就返回0 ，成功就返回1
     * @throws SchedulerException JOb 相关操作异常，直接抛出，update也会被回滚
     */
    @Transactional(rollbackFor = Exception.class)
    public int updateServerItem(ServerItem serverItem) throws SchedulerException {
        int re1 = serverItemMapper.updateItem(serverItem);
        if (re1 > 0) {
            serverItemJobService.updateJob(serverItem);
        }
        return re1;
    }

    public int deleteServerItem(int id) {
        int re = serverItemMapper.deleteItem(id);
        serverItemJobService.deleteItemJobByItemId(id);
        return re;
    }


    public RichServerItem getRichServerItemById(int id) {
        return serverItemMapper.getRichServerItemById(id);
    }

    public List<ServerItem> getAllItem() {
        return serverItemMapper.getAllList();
    }

    public int pauseJob(Integer itemId) {
        return serverItemMapper.updateJobStatus(itemId, MonitorConst.JobStatus.STOP.value, MonitorConst.JobStatus.RUNNING.value);
    }

    public int startJob(Integer itemId) {
        return serverItemMapper.updateJobStatus(itemId, MonitorConst.JobStatus.RUNNING.value, MonitorConst.JobStatus.STOP.value);
    }

}
