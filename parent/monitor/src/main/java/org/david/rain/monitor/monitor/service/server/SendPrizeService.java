package org.david.rain.monitor.monitor.service.server;

import org.david.rain.monitor.monitor.domain.SendPrize;
import org.david.rain.monitor.monitor.job.SendPrizeJobService;
import org.david.rain.monitor.monitor.persistence.SendPrizeMapper;
import org.david.rain.monitor.monitor.util.DateUtils;
import org.david.rain.monitor.monitor.util.EasyPageInfo;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by david
 * * on 13-12-13.
 */
@Service
public class SendPrizeService {

    Logger logger = LoggerFactory.getLogger(SendPrizeService.class);

    @Autowired
    SendPrizeMapper sendPrizeMapper;


    @Autowired
    SendPrizeJobService sendPrizeJobService;


    /**
     * @param pageInfo 分页用
     * @return 分页list
     */
    public List<SendPrize> getItemPageList(EasyPageInfo pageInfo, SendPrize sendPrize) {
        return sendPrizeMapper.getAllListPage(pageInfo, sendPrize);
    }


    @Transactional(rollbackFor = Exception.class)
    public void saveSendPrize(SendPrize sendPrize) throws Exception {
        sendPrize.setCreateTime(DateUtils.getCurrentFormatDateTime());
        sendPrize.setStatus(SendPrize.JobStatus.STOP.value);
        int re = sendPrizeMapper.insert(sendPrize);
        logger.info("insert result ID:{}", re);
        sendPrizeJobService.addsendPrizeJobAndStart(sendPrize);
    }

    public SendPrize getSendPrizeById(int sendPrizeId) {

        return sendPrizeMapper.selectByPrimaryKey(sendPrizeId);
    }


    /**
     * 更新一个发奖的配置
     *
     * @param sendPrize
     * @return 如果失败就返回0 ，成功就返回1
     * @throws SchedulerException JOb 相关操作异常，直接抛出，update也会被回滚
     *                            *
     */

    @Transactional(rollbackFor = Exception.class)
    public int updateSendPrize(SendPrize sendPrize) throws SchedulerException {
        int re1 = sendPrizeMapper.updateByPrimaryKeySelective(sendPrize);
        if (re1 > 0) {
            sendPrizeJobService.updateJob(sendPrize);
        }
        return re1;
    }

    public int deleteSendPrize(SendPrize sendPrize) {
        int re = sendPrizeMapper.delete(sendPrize);
        sendPrizeJobService.deleteSendPrizeJob(sendPrize);
        return re;
    }


   /* public List<SendPrize> getAllItem() {
        return sendPrizeMapper.getAllList();
    }*/

    public int pauseJob(SendPrize sendPrize) {
        return sendPrizeMapper.updateByPrimaryKeySelective(sendPrize);
    }

    public int startJob(SendPrize sendPrize) {
        return sendPrizeMapper.updateByPrimaryKeySelective(sendPrize);
    }

    public List<SendPrize> getAllSendPrize() {
        return sendPrizeMapper.selectAll();
    }
}
