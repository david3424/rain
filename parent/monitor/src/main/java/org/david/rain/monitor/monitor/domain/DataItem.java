package org.david.rain.monitor.monitor.domain;

/**
 * Created by czw on 14-2-21.
 */
public class DataItem {
    private Integer id;
    private String itemName;
    private String itemNameCh;
    private String dataSource;
    private String beginTime;
    private String endTime;
    private String changeTime;
    private Integer jobStatus;
    private String jobStatusName;
    private String jobCron;
    private Integer userId;
    private User user;
    private String memo;
    private Long turns;
    private String createTime;
    private Integer status;
    private String statusName;

    public static final int STATUS_ERROR = 2;
    public static final int STATUS_FINE = 1;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemNameCh() {
        return itemNameCh;
    }

    public void setItemNameCh(String itemNameCh) {
        this.itemNameCh = itemNameCh;
    }


    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getChangeTime() {
        return changeTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setChangeTime(String changeTime) {
        this.changeTime = changeTime;
    }

    public Integer getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(Integer jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getJobCron() {
        return jobCron;
    }

    public void setJobCron(String jobCron) {
        this.jobCron = jobCron;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Long getTurns() {
        return turns;
    }

    public void setTurns(Long turns) {
        this.turns = turns;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public String getJobStatusName() {
        return jobStatusName;
    }

    public void setJobStatusName(String jobStatusName) {
        this.jobStatusName = jobStatusName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    @Override
    public String toString() {
        return "DataItem{" +
                "dataSource='" + dataSource + '\'' +
                ", beginTime='" + beginTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", changeTime='" + changeTime + '\'' +
                ", jobStatus=" + jobStatus +
                ", jobStatusName='" + jobStatusName + '\'' +
                ", jobCron='" + jobCron + '\'' +
                ", userId=" + userId +
                ", user=" + user +
                ", memo='" + memo + '\'' +
                ", turns=" + turns +
                ", createTime='" + createTime + '\'' +
                ", status=" + status +
                ", statusName='" + statusName + '\'' +
                ", itemNameCh='" + itemNameCh + '\'' +
                ", itemName='" + itemName + '\'' +
                ", id=" + id +
                '}';
    }
}
