package org.david.rain.web.service.demo;

import common.UserInfo;
import org.david.rain.common.components.lottery.AbstractPrize;
import org.david.rain.common.components.lottery.LotteryService;
import org.david.rain.common.components.lottery.NullLotteryPrize;
import org.david.rain.common.repository.Idao;
import org.david.rain.common.util.DateUtils;
import org.david.rain.web.controller.lottery.LotteryDemoService;
import org.david.rain.web.controller.lottery.LotteryPrize;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

/**
 * Created by user on 2015/10/20.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml"})
public class LotteryTest {

    /**
     * 抽奖逻辑实现，若未中奖，两个阳光普照奖随机选一个发给玩家。
     */
    @Autowired
    @Qualifier("probabilityLotteryService")
    private LotteryService lotteryService;

    @Autowired
    private LotteryDemoService lotteryDemoService;

    @Qualifier("writeDaoImp")
    @Autowired
    Idao dao;


    @Test
    public void testLottery() throws Exception {
        String username = "david3425";
        UserInfo userInfo = lotteryDemoService.getUserInfo(username);
        assertThat(userInfo).isNotNull();
        AbstractPrize abstractPrize = lotteryService.lottery("lottery_demo_prize");
        System.out.println("lottery result in  testCase:" + abstractPrize);
        LotteryPrize prize;
        if (abstractPrize instanceof NullLotteryPrize) {
            prize = lotteryDemoService.getSunshinePrize(abstractPrize, username, userInfo);
        } else {
            prize = lotteryDemoService.setCommonValue(abstractPrize, username, userInfo);
        }
        System.out.println(lotteryDemoService.savePrize(prize));
    }


    @Test
    public void testSignIn() throws Exception {
        System.out.println(lotteryDemoService.signIn("david3424", DateUtils.getCurrentFormatDate()));
    }

    @Test
    public void testTransaction() throws Exception {

        dao.update("update hd_test_log set ip = ? where id = 515 ", "127.0.0.1");

    }
}
