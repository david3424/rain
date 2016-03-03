package org.david.rain.monitor.monitor.domain;

/**
 * Created by czw on 14-3-5.
 */
public class PhaseDetail {
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

    @Override
    public String toString() {
        return "PhaseDetail{" +
                "strategyId=" + strategyId +
                ", phaseName='" + phaseName + '\'' +
                ", memo='" + memo + '\'' +
                ", phaseStart='" + phaseStart + '\'' +
                ", phaseEnd='" + phaseEnd + '\'' +
                ", tendency=" + tendency +
                ", createTime='" + createTime + '\'' +
                ", status=" + status +
                '}';
    }
}
