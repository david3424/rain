package org.david.rain.common.components.lottery;

/**
 */
public interface LotteryService {
    AbstractPrize lottery(String lotteryKey) throws Exception;

    AbstractPrize lottery(String lotteryKey, String lotteryPool) throws Exception;
}
