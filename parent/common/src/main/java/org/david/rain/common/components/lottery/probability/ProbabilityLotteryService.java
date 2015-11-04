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
        logger.debug("todaycouhnt=="+todayCount);
        if (todayCount >= prize.getTodayLimit()) {
            return false;
        }
        int allCount = probabilityInteface.getPrizeSendedCount(prize);
        logger.debug("allCount=="+allCount);
        return allCount < prize.getStepLimit() && allCount < prize.getTotalLimit();
    }


    @Override
    public AbstractPrize lottery(String lotteryKey) throws Exception {
        ProbabilityLotteryPrize prize = ProbabilityAlgorithm.drawPrize(probabilityInteface.prizeInfoConfig(lotteryKey), probabilityInteface.lotteryAccuracy(lotteryKey));
        logger.debug("prizeis null or not:"+prize);
        if (prize == null) {
            return new NullLotteryPrizeImpl();
        }
        if (!checkConfigLimit(prize)) {
            logger.debug("!checkConfigLimit(prize");
            return new NullLotteryPrizeImpl();
        }
        return prize;
    }

    @Override
    public AbstractPrize lottery(String lotteryKey, String lotteryPool) throws Exception {
        throw new UnsupportedOperationException("probability-lottery do not support lottery in lottery-pool");
    }
}
