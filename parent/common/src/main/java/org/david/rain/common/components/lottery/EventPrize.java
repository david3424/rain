package org.david.rain.common.components.lottery;

/**
 * 活动里面的奖品可以继承这个，可以规范化字段，该类的字段可以多加一点
 */
public class EventPrize {
    protected String username;
    protected Integer prize;
    protected String prizeName;
    protected String prizePool = AbstractPrize.DEFAULT_POOL;  // destine 抽奖需要用这个分奖池
    protected String date; // 概率抽奖需要用这个字段统计日抽奖量
    protected String createTime;
    protected Integer status;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getPrizePool() {
        return prizePool;
    }

    public void setPrizePool(String prizePool) {
        this.prizePool = prizePool;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "EventPrize{" +
                "username='" + username + '\'' +
                ", prize=" + prize +
                ", prizeName='" + prizeName + '\'' +
                ", prizePool='" + prizePool + '\'' +
                ", date='" + date + '\'' +
                ", createTime='" + createTime + '\'' +
                ", status=" + status +
                '}';
    }
}
