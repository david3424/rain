package org.david.rain.monitor.monitor.domain;

/**
 * Created with IntelliJ IDEA.
 * User: zhangji
 * Date: 14-3-13
 * Time: 下午4:16
 * To change this template use File | Settings | File Templates.
 */
public class DataTimeSetting {
    private Integer strategyId;
    private String phaseName;
    private String memo;
    private String phaseStart;
    private String phaseEnd;
    private Integer tendency;
    private String createTime;
    private Integer status;

    public Integer getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Integer strategyId) {
        this.strategyId = strategyId;
    }

    public String getPhaseName() {
        return phaseName;
    }

    public void setPhaseName(String phaseName) {
        this.phaseName = phaseName;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getPhaseStart() {
        return phaseStart;
    }

    public void setPhaseStart(String phaseStart) {
        this.phaseStart = phaseStart;
    }

    public String getPhaseEnd() {
        return phaseEnd;
    }

    public void setPhaseEnd(String phaseEnd) {
        this.phaseEnd = phaseEnd;
    }

    public Integer getTendency() {
        return tendency;
    }

    public void setTendency(Integer tendency) {
        this.tendency = tendency;
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
}
