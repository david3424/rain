package org.david.rain.common.components.lottery.probability;


import org.david.rain.common.components.lottery.AbstractPrize;
import org.david.rain.common.components.lottery.LotteryService;
import org.david.rain.common.components.lottery.NullLotteryPrize;
import org.david.rain.common.components.lottery.NullLotteryPrizeImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 */

public class ProbabilityLotteryService implements LotteryService {

    ProbabilityInteface probabilityInteface;
    private final Logger logger = LoggerFactory.getLogger(ProbabilityLotteryService.class);

    public ProbabilityLotteryService() {
    }

    public ProbabilityLotteryService(ProbabilityInteface probabilityInteface) {
        this.probabilityInteface = probabilityInteface;
    }

    public boolean checkConfigLimit(ProbabilityLotteryPrize prize) throws Exception {
        if (prize instanceof NullLotteryPrize) {
            return true;
        }
        int todayCount = probabilityInteface.getTodayPrizeSendedCount(prize);
        logger.debug("todaycouhnt==" + todayCount);
        if (todayCount >= prize.getTodayLimit()) {
            return false;
        }
        int allCount = probabilityInteface.getPrizeSendedCount(prize);
        logger.debug("allCount==" + allCount);
        return allCount < prize.getStepLimit() && allCount < prize.getTotalLimit();
    }


    @Override
    public AbstractPrize lottery(String lotteryKey) throws Exception {
        //根据表名(key)提取奖品列表 和 抽奖精度(为方便个性化)
        ProbabilityLotteryPrize prize = ProbabilityAlgorithm.drawPrize(probabilityInteface.prizeInfoConfig(lotteryKey), probabilityInteface.lotteryAccuracy(lotteryKey));
        logger.debug("prizeis null or not:" + prize);
        if (prize == null) {//返回阳光普照
            logger.debug("返回阳光普照");
            return new NullLotteryPrizeImpl();
        }
        if (!checkConfigLimit(prize)) { //虽然抽中，数据达上限，依然阳光普照
            logger.debug("虽然抽中，数据达上限，依然阳光普照");
            return new NullLotteryPrizeImpl();
        }
        return prize;
    }

    @Override
    public AbstractPrize lottery(String lotteryKey, String lotteryPool) throws Exception {
        throw new UnsupportedOperationException("probability-lottery do not support lottery in lottery-pool");
    }
}
