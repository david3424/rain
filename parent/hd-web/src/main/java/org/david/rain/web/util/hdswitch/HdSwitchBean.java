package org.david.rain.web.util.hdswitch;

import java.io.Serializable;

/**
 */
public class HdSwitchBean implements Serializable {

    private static final long seriaVerisionUID = 13417890443353453l;


    public static final Integer SWITCH_OPEN_STATUS = 1;
    public static final Integer SWITCH_CLOSE_STATUS = 0;


    /**
     * 这里的value值有待规范
     */
    public static enum SwitchType {
        NORMAL(1),
        RECHARGE(2);

        public final int value;

        private SwitchType(int value) {
            this.value = value;
        }

    }

    private Integer id;
    private String dbName;
    private String hdName;
    private String tbName;
    private Integer open;
    private String createTime;
    private String ip;
    private String beginTime;//活动开始时间
    private String endTime;//活动截止时间
    private String scoreStart;//充值查询开始时间
    private String scoreEnd;//充值查询截止时间
    private String testStart;//测试充值开始时间
    private String testEnd;//测试充值结束时间
    private Integer type;//类型
    private Integer status;

    public HdSwitchBean() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getHdName() {
        return hdName;
    }

    public void setHdName(String hdName) {
        this.hdName = hdName;
    }

    public String getTbName() {
        return tbName;
    }

    public void setTbName(String tbName) {
        this.tbName = tbName;
    }

    public Integer getOpen() {
        return open;
    }

    public void setOpen(Integer open) {
        this.open = open;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getScoreStart() {
        return scoreStart;
    }

    public void setScoreStart(String scoreStart) {
        this.scoreStart = scoreStart;
    }

    public String getScoreEnd() {
        return scoreEnd;
    }

    public void setScoreEnd(String scoreEnd) {
        this.scoreEnd = scoreEnd;
    }

    public String getTestStart() {
        return testStart;
    }

    public void setTestStart(String testStart) {
        this.testStart = testStart;
    }

    public String getTestEnd() {
        return testEnd;
    }

    public void setTestEnd(String testEnd) {
        this.testEnd = testEnd;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "HdSwitchBean{" +
                "id=" + id +
                ", dbName='" + dbName + '\'' +
                ", hdName='" + hdName + '\'' +
                ", tbName='" + tbName + '\'' +
                ", open=" + open +
                ", createTime='" + createTime + '\'' +
                ", ip='" + ip + '\'' +
                ", beginTime='" + beginTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", scoreStart='" + scoreStart + '\'' +
                ", scoreEnd='" + scoreEnd + '\'' +
                ", testStart='" + testStart + '\'' +
                ", testEnd='" + testEnd + '\'' +
                ", type=" + type +
                ", status=" + status +
                '}';
    }
}
