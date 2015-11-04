package org.david.rain.common.components.lottery.probability;


/**
 * 抽奖基础信息
 */
public class LotteryBaseInfo {
    private Integer id;
    private String prizetable;
    private Integer accuracy;
    private String description;
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPrizetable() {
        return prizetable;
    }

    public void setPrizetable(String prizetable) {
        this.prizetable = prizetable;
    }

    public Integer getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Integer accuracy) {
        this.accuracy = accuracy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("LotteryBaseInfo");
        sb.append("{id=").append(id);
        sb.append(", prizetable='").append(prizetable).append('\'');
        sb.append(", accuracy='").append(accuracy).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", status='").append(status).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
