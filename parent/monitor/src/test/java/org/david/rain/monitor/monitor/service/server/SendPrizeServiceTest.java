package org.david.rain.monitor.monitor.service.server;

import org.david.rain.monitor.monitor.domain.SendPrize;
import org.david.rain.monitor.monitor.job.MonitorConst;
import org.david.rain.monitor.monitor.util.EasyPageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

/**
 * Created by david
 * * on 16/3/23.
 */

@RunWith(SpringJUnit4ClassRunner.class)
//@Transactional
//@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@ContextConfiguration("classpath:spring/applicationContext.xml")
@WebAppConfiguration
public class SendPrizeServiceTest {


    @Autowired
    SendPrizeService sendPrizeService;

/*    @Test
    public void testGetItemPageList() throws Exception {

        EasyPageInfo easyPageInfo = new EasyPageInfo(10, 1);
        sendPrizeService.getItemPageList(easyPageInfo, null);
    }*/

    @Test
    public void testAward() throws Exception {
        Thread.sleep(1000 * 200);
    }

    @Test
    public void testSaveSendPrize() throws Exception {
        SendPrize sendPrize = new SendPrize();
        sendPrize.setDatasource("event");
        sendPrize.setConsumeType(0);//是啥
        sendPrize.setSendInterface(0);//是啥
        sendPrize.setSendCheck(1); //用作用户ID
        sendPrize.setPrizememo("测试");
        sendPrize.setRoleIdType(0);//角色ID类型
        sendPrize.setSendType(1); //角色ID
        sendPrize.setTableName("lottery_demo_prize");
        sendPrize.setJobCron("0/10 * * * * ?");
        sendPrizeService.saveSendPrize(sendPrize);
        Thread.sleep(1000 * 20);
    }

    @Test
    public void testUpdateSendPrize() throws Exception {
        SendPrize sendPrize = new SendPrize();
        sendPrize.setId(1);
        sendPrize.setPrizememo("修改测试");
        sendPrize.setTableName("lottery_demo_prize");
        sendPrize.setJobCron("0/5 * * * * ?");
        sendPrizeService.updateSendPrize(sendPrize);
        Thread.sleep(1000 * 10);
    }

    @Test
    public void testPauseJob() throws Exception {
        SendPrize sendPrize = new SendPrize();
        sendPrize.setId(1);
        sendPrize.setStatus(MonitorConst.JobStatus.STOP.value);
        sendPrizeService.updateSendPrize(sendPrize);
        Thread.sleep(1000 * 10);
    }

    @Test
    public void testStartJob() throws Exception {
        SendPrize sendPrize = new SendPrize();
        sendPrize.setId(1);
        sendPrize.setStatus(MonitorConst.JobStatus.RUNNING.value);
        sendPrizeService.updateSendPrize(sendPrize);
        Thread.sleep(1000 * 1000);
    }

    @Test
    public void testDeleteSendPrize() throws Exception {
        SendPrize sendPrize = new SendPrize();
        sendPrize.setId(1);
        sendPrizeService.deleteSendPrize(sendPrize);
        Thread.sleep(1000 * 10);
    }

}