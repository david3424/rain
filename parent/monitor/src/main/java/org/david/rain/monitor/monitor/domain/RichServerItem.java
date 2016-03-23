package org.david.rain.monitor.monitor.domain;

/**
 * Created by david
 * * on 13-12-25.
 */
public class RichServerItem {
    private Integer id;
    private String itemName;
    private String itemNameCh;
    private String itemUrl;
    private String returnType;

    private String changeTime;
    private String createTime;
    private Integer jobStatus;
    private User creator;
    private String jobCron;
    private String memo;
    private Integer status;

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

    public String getJobCron() {
        return jobCron;
    }

    public void setJobCron(String jobCron) {
        this.jobCron = jobCron;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getItemNameCh() {
        return itemNameCh;
    }

    public void setItemNameCh(String itemNameCh) {
        this.itemNameCh = itemNameCh;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public String toString() {
        return "RichServerItem{" +
                "id=" + id +
                ", itemName='" + itemName + '\'' +
                ", itemNameCh='" + itemNameCh + '\'' +
                ", itemUrl='" + itemUrl + '\'' +
                ", returnType='" + returnType + '\'' +
                ", changeTime='" + changeTime + '\'' +
                ", createTime='" + createTime + '\'' +
                ", jobStatus=" + jobStatus +
                ", creator=" + creator +
                ", jobCron='" + jobCron + '\'' +
                ", memo='" + memo + '\'' +
                ", status=" + status +
                '}';
    }
}
