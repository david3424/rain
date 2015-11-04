package org.david.rain.common.components.lottery.probability;

import java.util.List;

/**
 *
 */
public interface ProbabilityInteface {
    public int lotteryAccuracy(String prizetable) throws Exception;

    public List<ProbabilityLotteryPrize> prizeInfoConfig(String prizetable) throws Exception;

    public int getTodayPrizeSendedCount(ProbabilityLotteryPrize prize) throws Exception;

    public int getPrizeSendedCount(ProbabilityLotteryPrize prize) throws Exception;
}
