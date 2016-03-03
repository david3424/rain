package org.david.rain.monitor.monitor.domain;

import org.david.rain.monitor.monitor.util.DateUtils;

/**
 * Created by czw on 13-12-30.
 */
public class ServerMonitorValue {

    public static int HEALTH = 1;
    public static int ABNORMAL = 2;


    private Integer id;

    private Integer itemId;
    private String typeCode;
    private String attributeName;
    private String judgeMethod;
    private Integer healthValue;
    private Integer abnTimes;
    private Integer abnLevel;
    private String statusBegin;
    private Integer status;


    public ServerMonitorValue() {

    }

    public ServerMonitorValue(RichServerItem serverItem, TypeSetting typeSetting) {
        this.itemId = serverItem.getId();
        this.attributeName = typeSetting.getAttributeName();
        this.abnTimes = 0;
        this.abnLevel = typeSetting.getDefaultLevel();
        this.statusBegin = DateUtils.getCurrentFormatDateTime();
        this.status = HEALTH; // 这个暂时没有用
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getAbnTimes() {
        return abnTimes;
    }

    public void setAbnTimes(Integer abnTimes) {
        this.abnTimes = abnTimes;
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

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getStatusBegin() {
        return statusBegin;
    }

    public void setStatusBegin(String statusBegin) {
        this.statusBegin = statusBegin;
    }

    public Integer getHealthValue() {
        return healthValue;
    }

    public void setHealthValue(Integer healthValue) {
        this.healthValue = healthValue;
    }

    public String getJudgeMethod() {
        return judgeMethod;
    }

    public void setJudgeMethod(String judgeMethod) {
        this.judgeMethod = judgeMethod;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ServerMonitorValue{" +
                "id=" + id +
                ", itemId=" + itemId +
                ", typeCode='" + typeCode + '\'' +
                ", attributeName='" + attributeName + '\'' +
                ", judgeMethod='" + judgeMethod + '\'' +
                ", healthValue=" + healthValue +
                ", abnTimes=" + abnTimes +
                ", abnLevel=" + abnLevel +
                ", statusBegin='" + statusBegin + '\'' +
                ", status=" + status +
                '}';
    }
}
