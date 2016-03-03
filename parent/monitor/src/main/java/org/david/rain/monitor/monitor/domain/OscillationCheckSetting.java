package org.david.rain.monitor.monitor.domain;

import java.util.List;

/**
 * Created by czw on 14-3-5.
 */
public class OscillationCheckSetting {


    public static final int STATUS_ERROR = -1;
    public static final int STATUS_NORMAL = 1;
    public static final int STATUS_EXCEPTION = 0;

    private Integer itemId;
    private String attrName;
    private String oscillation;
    private String memo;
    private Integer checkStrategy;
    private Integer phaseStrategy;

    private DateTimeItem dateTimeItem;

    private List<PhaseDetail> phaseDetailList;

    private Integer timeStep;
    private Integer abnLevel;
    private Integer status;

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getOscillation() {
        return oscillation;
    }

    public void setOscillation(String oscillation) {
        this.oscillation = oscillation;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }


    public List<PhaseDetail> getPhaseDetailList() {
        return phaseDetailList;
    }

    public void setPhaseDetailList(List<PhaseDetail> phaseDetailList) {
        this.phaseDetailList = phaseDetailList;
    }

    public Integer getCheckStrategy() {
        return checkStrategy;
    }

    public void setCheckStrategy(Integer checkStrategy) {
        this.checkStrategy = checkStrategy;
    }

    public Integer getPhaseStrategy() {
        return phaseStrategy;
    }

    public void setPhaseStrategy(Integer phaseStrategy) {
        this.phaseStrategy = phaseStrategy;
    }

    public Integer getTimeStep() {
        return timeStep;
    }

    public void setTimeStep(Integer timeStep) {
        this.timeStep = timeStep;
    }

    public Integer getAbnLevel() {
        return abnLevel;
    }

    public void setAbnLevel(Integer abnLevel) {
        this.abnLevel = abnLevel;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public DateTimeItem getDateTimeItem() {
        return dateTimeItem;
    }

    public void setDateTimeItem(DateTimeItem dateTimeItem) {
        this.dateTimeItem = dateTimeItem;
    }

    @Override
    public String toString() {
        return "OscillationCheckSetting{" +
                "itemId=" + itemId +
                ", attrName='" + attrName + '\'' +
                ", oscillation='" + oscillation + '\'' +
                ", memo='" + memo + '\'' +
                ", checkStrategy=" + checkStrategy +
                ", phaseStrategy=" + phaseStrategy +
                ", dateTimeItem=" + dateTimeItem +
                ", phaseDetailList=" + phaseDetailList +
                ", timeStep=" + timeStep +
                ", abnLevel=" + abnLevel +
                ", status=" + status +
                '}';
    }
}
