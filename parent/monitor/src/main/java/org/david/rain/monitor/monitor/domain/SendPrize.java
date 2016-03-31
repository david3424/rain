package org.david.rain.monitor.monitor.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by david
 * * on 13-12-9.
 */

@Table(name = "send_prize_properties")
public class SendPrize {

    public enum JobStatus {
        STOP(0),
        START(1);
        public final int value;

        JobStatus(int value) {
            this.value = value;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String tableName;
    private String datasource;
    private Integer roleIdType;
    private Integer sendType;
    private Integer consumeType;
    private Integer sendInterface;
    private Integer sendCheck;
    private String createTime;
    private String jobCron;
    private Date updateTime;
    private String prizememo;

    private Integer status;

    public String getJobCron() {
        return jobCron;
    }

    public void setJobCron(String jobCron) {
        this.jobCron = jobCron;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getPrizememo() {
        return prizememo;
    }

    public void setPrizememo(String prizememo) {
        this.prizememo = prizememo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDatasource() {
        return datasource;
    }

    public void setDatasource(String datasource) {
        this.datasource = datasource;
    }

    public Integer getRoleIdType() {
        return roleIdType;
    }

    public void setRoleIdType(Integer roleIdType) {
        this.roleIdType = roleIdType;
    }

    public Integer getSendType() {
        return sendType;
    }

    public void setSendType(Integer sendType) {
        this.sendType = sendType;
    }

    public Integer getConsumeType() {
        return consumeType;
    }

    public void setConsumeType(Integer consumeType) {
        this.consumeType = consumeType;
    }

    public Integer getSendInterface() {
        return sendInterface;
    }

    public void setSendInterface(Integer sendInterface) {
        this.sendInterface = sendInterface;
    }

    public Integer getSendCheck() {
        return sendCheck;
    }

    public void setSendCheck(Integer sendCheck) {
        this.sendCheck = sendCheck;
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
        return "SendPrize{" +
                "id=" + id +
                ", tableName='" + tableName + '\'' +
                ", datasource='" + datasource + '\'' +
                ", roleIdType=" + roleIdType +
                ", sendType=" + sendType +
                ", consumeType=" + consumeType +
                ", sendInterface=" + sendInterface +
                ", sendCheck=" + sendCheck +
                ", createTime='" + createTime + '\'' +
                ", jobCron='" + jobCron + '\'' +
                ", updateTime=" + updateTime +
                ", prizememo='" + prizememo + '\'' +
                ", status=" + status +
                '}';
    }
}
