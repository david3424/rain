package org.david.rain.monitor.monitor.domain;

/**
 * Created by czw on 13-12-9.
 */
public class ServerItem {


    public static enum JobStatus {
        STOP(0),
        START(1);
        public final int value;

        private JobStatus(int value) {
            this.value = value;
        }

    }

    private Integer id;
    private String itemName;
    private String itemNameCh;
    private String itemUrl;
    private String returnType;
    private String changeTime;
    private String createTime;
    private Integer jobStatus;
    private String jobCron;
    private String username;//一开始是显示这个的，后来加上了creator
    private Integer userId;
    private User creator;
    private String chName;
    private Integer status;
    private String statusName;
    private String jobStatusName;

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

    public String getItemUrl() {
        return itemUrl;
    }

    public void setItemUrl(String itemUrl) {
        this.itemUrl = itemUrl;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public String getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(String changeTime) {
        this.changeTime = changeTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(Integer jobStatus) {
        this.jobStatus = jobStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public String getJobCron() {
        return jobCron;
    }

    public void setJobCron(String jobCron) {
        this.jobCron = jobCron;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getChName() {
        return chName;
    }

    public void setChName(String chName) {
        this.chName = chName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getJobStatusName() {
        return jobStatusName;
    }

    public void setJobStatusName(String jobStatusName) {
        this.jobStatusName = jobStatusName;
    }

    public String getItemNameCh() {
        return itemNameCh;
    }

    public void setItemNameCh(String itemNameCh) {
        this.itemNameCh = itemNameCh;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    @Override
    public String toString() {
        return "ServerItem{" +
                "id=" + id +
                ", itemName='" + itemName + '\'' +
                ", itemNameCh='" + itemNameCh + '\'' +
                ", itemUrl='" + itemUrl + '\'' +
                ", returnType='" + returnType + '\'' +
                ", changeTime='" + changeTime + '\'' +
                ", createTime='" + createTime + '\'' +
                ", jobStatus=" + jobStatus +
                ", jobCron='" + jobCron + '\'' +
                ", username='" + username + '\'' +
                ", userId=" + userId +
                ", creator=" + creator +
                ", chName='" + chName + '\'' +
                ", status=" + status +
                ", statusName='" + statusName + '\'' +
                ", jobStatusName='" + jobStatusName + '\'' +
                '}';
    }
}
