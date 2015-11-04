package org.david.rain.common.components.lottery;

/**
 */
public class NullLotteryPrizeImpl extends AbstractPrize implements NullLotteryPrize {
    public NullLotteryPrizeImpl() {
        super.setPrize(NullLotteryPrize.NOT_PRIZE_ID);
        super.setPrizeName(NullLotteryPrize.NOT_PRIZE_NAME);
        super.setPageId(NullLotteryPrize.NOT_PRIZE_PAGE_ID);
    }
}
