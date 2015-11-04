package org.david.rain.common.components.lottery;


import org.david.rain.common.util.DateUtils;

import java.io.Serializable;

/**
 */
public abstract class AbstractPrize implements Serializable {


    public static AbstractPrize newNullPrize() {
        return new NullLotteryPrizeImpl();
    }

    public static final String DEFAULT_POOL = "defaultPool";
    protected Integer id;
    protected Integer pageId;
    protected Integer prize;
    protected String prizeName;
    protected String description;
    protected String prizePool = DEFAULT_POOL; //一个抽奖可能分不同的奖池，目前概率抽奖时不支持奖池的
    protected Integer defaultStatus;

    public void shortCutSetToEventPrize(Object o) throws Exception {
        o.getClass().getMethod("setPrize", Integer.class).invoke(o, getPrize());
        o.getClass().getMethod("setPrizeName", String.class).invoke(o, getPrizeName());
        o.getClass().getMethod("setStatus", Integer.class).invoke(o, getDefaultStatus());
        o.getClass().getMethod("setDate", String.class).invoke(o, DateUtils.getCurrentFormatDate());
        o.getClass().getMethod("setCreateTime", String.class).invoke(o, DateUtils.getCurrentFormatDateTime());
        o.getClass().getMethod("setPrizePool", String.class).invoke(o, getPrizePool());
    }

    public <T extends EventPrize> void shortCutSetToEventPrize(T eventPrize) {
        eventPrize.setPrize(this.getPrize());
        eventPrize.setPrizeName(this.getPrizeName());
        eventPrize.setStatus(this.getDefaultStatus());
        eventPrize.setCreateTime(DateUtils.getCurrentFormatDateTime());
        eventPrize.setPrizePool(this.getPrizePool());
        eventPrize.setDate(DateUtils.getCurrentFormatDate());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPageId() {
        return pageId;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }

    public Integer getPrize() {
        return prize;
    }

    public void setPrize(Integer prize) {
        this.prize = prize;
    }

    public String getPrizeName() {
        return prizeName;
    }

    public void setPrizeName(String prizeName) {
        this.prizeName = prizeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDefaultStatus() {
        return defaultStatus;
    }

    public void setDefaultStatus(Integer defaultStatus) {
        this.defaultStatus = defaultStatus;
    }

    public String getPrizePool() {
        return prizePool;
    }

    public void setPrizePool(String prizePool) {
        this.prizePool = prizePool;
    }

    @Override
    public String toString() {
        return "AbstractPrize{" +
                "id=" + id +
                ", pageId=" + pageId +
                ", prize=" + prize +
                ", prizeName='" + prizeName + '\'' +
                ", description='" + description + '\'' +
                ", prizePool='" + prizePool + '\'' +
                ", defaultStatus=" + defaultStatus +
                '}';
    }
}
