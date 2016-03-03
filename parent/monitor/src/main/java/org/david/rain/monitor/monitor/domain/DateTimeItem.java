package org.david.rain.monitor.monitor.domain;

/**
 * Created with IntelliJ IDEA.
 * User: zhangji
 * Date: 14-3-13
 * Time: 下午2:06
 * To change this template use File | Settings | File Templates.
 */
public class DateTimeItem {
    private Integer id;
    private String strategyName;
    private String memo;
    private String createTime;
    private Integer status;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStrategyName() {
        return strategyName;
    }

    public void setStrategyName(String strategyName) {
        this.strategyName = strategyName;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "DateTimeItem{" +
                "id=" + id +
                ", strategyName='" + strategyName + '\'' +
                ", memo='" + memo + '\'' +
                ", createTime='" + createTime + '\'' +
                ", status=" + status +
                '}';
    }
}
