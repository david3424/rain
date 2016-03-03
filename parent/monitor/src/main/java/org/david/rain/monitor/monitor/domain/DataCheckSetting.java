package org.david.rain.monitor.monitor.domain;

/**
 * Created by czw on 14-2-24.
 */
public class DataCheckSetting {

    public static enum Status {
        NORMAL(1),    //规则满足，正常状态。
        EXCEPTION(2);//规则不满足，错误状态

        public final int value;

        private Status(int value) {
            this.value = value;
        }
    }


    private Integer itemId;
    private String checkerName;
    private String chName;
    private String memo;
    private String expression;
    private Integer abnLevel;
    private String createTime;
    private Integer status;

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getCheckerName() {
        return checkerName;
    }

    public void setCheckerName(String checkerName) {
        this.checkerName = checkerName;
    }

    public String getChName() {
        return chName;
    }

    public void setChName(String chName) {
        this.chName = chName;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public Integer getAbnLevel() {
        return abnLevel;
    }

    public void setAbnLevel(Integer abnLevel) {
        this.abnLevel = abnLevel;
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
