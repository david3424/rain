package org.david.rain.monitor.monitor.domain;

import org.david.rain.monitor.monitor.util.DateUtils;

import java.util.Date;

/**
 * Created by czw on 14-3-5.
 */
public class OscillationLog {
    protected Long id;
    protected Integer itemId;
    protected String attrName;
    protected Long itemTurns;
    protected Long total;
    protected Long currentNum;
    protected Integer phaseStrategyId;
    protected String phaseDetail;
    protected Integer times;
    protected String createTime;
    protected Long createTimeLong;
    protected Integer status;


    public static final int NORMAL = 1;
    public static final int EXCEPTION = 0;
    public static final int ERROR = -1;

    public OscillationLog() {

    }

    public OscillationLog(OscillationCheckSetting setting) {
        this.itemId = setting.getItemId();
        this.attrName = setting.getAttrName();
        this.phaseStrategyId = setting.getPhaseStrategy();
        this.createTimeLong = System.currentTimeMillis();
        this.createTime = DateUtils.formatDateTime(new Date(this.createTimeLong));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Long getItemTurns() {
        return itemTurns;
    }

    public void setItemTurns(Long itemTurns) {
        this.itemTurns = itemTurns;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getCurrentNum() {
        return currentNum;
    }

    public void setCurrentNum(Long currentNum) {
        this.currentNum = currentNum;
    }

    public Integer getPhaseStrategyId() {
        return phaseStrategyId;
    }

    public void setPhaseStrategyId(Integer phaseStrategyId) {
        this.phaseStrategyId = phaseStrategyId;
    }

    public String getPhaseDetail() {
        return phaseDetail;
    }

    public void setPhaseDetail(String phaseDetail) {
        this.phaseDetail = phaseDetail;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public String getCreateTime() {
        return createTime;
    }

    public Long getCreateTimeLong() {
        return createTimeLong;
    }

    public void setCreateTimeLong(Long createTimeLong) {
        this.createTimeLong = createTimeLong;
    }

    public void setCreateTime(String createTimes) {
        this.createTime = createTimes;
    }

    @Override
    public String toString() {
        return "OscillationLog{" +
                "id=" + id +
                ", itemId=" + itemId +
                ", attrName='" + attrName + '\'' +
                ", itemTurns=" + itemTurns +
                ", total=" + total +
                ", currentNum=" + currentNum +
                ", phaseStrategyId=" + phaseStrategyId +
                ", phaseDetail='" + phaseDetail + '\'' +
                ", times=" + times +
                ", createTime='" + createTime + '\'' +
                ", createTimeLong=" + createTimeLong +
                ", status=" + status +
                '}';
    }
}
