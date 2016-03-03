package org.david.rain.monitor.monitor.domain;

/**
 * Created by czw on 13-12-9.
 */

public class TypeSetting {
    private Integer id;
    private String typeCode;
    private String attributeName;
    private Integer healthValue;
    private String judgeMethod;
    private Integer defaultLevel;
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
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

    public Integer getDefaultLevel() {
        return defaultLevel;
    }

    public void setDefaultLevel(Integer defaultLevel) {
        this.defaultLevel = defaultLevel;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TypeSetting{" +
                "id=" + id +
                ", typeCode='" + typeCode + '\'' +
                ", attributeName='" + attributeName + '\'' +
                ", healthValue=" + healthValue +
                ", judgeMethod='" + judgeMethod + '\'' +
                ", defaultLevel=" + defaultLevel +
                ", status=" + status +
                '}';
    }
}
