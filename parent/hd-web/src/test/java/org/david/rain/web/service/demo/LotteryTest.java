package org.david.rain.web.service.demo;

import common.UserInfo;
import org.david.rain.common.components.lottery.AbstractPrize;
import org.david.rain.common.components.lottery.LotteryService;
import org.david.rain.common.components.lottery.NullLotteryPrize;
import org.david.rain.web.controller.lottery.LotteryDemoService;
import org.david.rain.web.controller.lottery.LotteryPrize;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by user on 2015/10/20.
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
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

    @Test
    public void testLottery() throws Exception {
        String username = "david3424";
        UserInfo userInfo = lotteryDemoService.getUserInfo(username);
        AbstractPrize abstractPrize = lotteryService.lottery("lottery_demo_prize");
        System.out.println("lottery result in  testCase:" + abstractPrize);
        LotteryPrize prize;
        if (abstractPrize instanceof NullLotteryPrize) {
            prize = lotteryDemoService.getSunshinePrize(abstractPrize, username, userInfo);
        } else {
            prize = lotteryDemoService.setCommonValue(abstractPrize, username, userInfo);
        }
        System.out.println(lotteryDemoService.savePrizeAndWriteLog(prize, abstractPrize.getPageId()));
    }


    @Test
    public void testSignIn() throws Exception {
        System.out.println(lotteryDemoService.signIn("david3424","2015-10-21"));
    }
}
