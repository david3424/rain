package org.david.rain.common.components.lottery.probability;


import org.david.rain.common.components.lottery.AbstractPrize;

public class ProbabilityLotteryPrize extends AbstractPrize {
    protected String prizeTable;
    protected Integer totalLimit;
    protected Integer todayLimit;
    protected Integer stepLimit;
    protected Integer probability;

    public ProbabilityLotteryPrize() {
    }

    public String getPrizeTable() {
        return prizeTable;
    }

    public void setPrizeTable(String prizeTable) {
        this.prizeTable = prizeTable;
    }

    public Integer getTotalLimit() {
        return totalLimit;
    }

    public void setTotalLimit(Integer totalLimit) {
        this.totalLimit = totalLimit;
    }

    public Integer getTodayLimit() {
        return todayLimit;
    }

    public void setTodayLimit(Integer todayLimit) {
        this.todayLimit = todayLimit;
    }

    public Integer getStepLimit() {
        return stepLimit;
    }

    public void setStepLimit(Integer stepLimit) {
        this.stepLimit = stepLimit;
    }

    public Integer getProbability() {
        return probability;
    }

    public void setProbability(Integer probability) {
        this.probability = probability;
    }

    @Override
    public String toString() {
        return "LotteryPrize{" +
                "prizeTable='" + prizeTable + '\'' +
                ", totalLimit=" + totalLimit +
                ", todayLimit=" + todayLimit +
                ", stepLimit=" + stepLimit +
                ", probability=" + probability +
                "} " + super.toString();
    }
}
