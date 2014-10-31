package bean;

import org.david.rain.beta.dao.annotation.Field;
import org.david.rain.beta.dao.annotation.NoUpdate;
import org.david.rain.beta.dao.annotation.PK;
import org.david.rain.beta.dao.annotation.Table;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by david on 2014/10/30.
 *
 */
@Table("t_log")
public class WorkLog {

    //id
    @PK
    @Field("id")
    private String logId ;
    //日志日期
    @NoUpdate
    private Date logDate; //log_date
    //所属项目
    private String projectId;
    //工作类型
    private String    jobTypeId;
    //日志内容
    private String    content;
    //工作时长
    private double    workTime;
    //填写时间
    private Timestamp fillTime;
    //日志填写人
    @NoUpdate
    private String employeeId;
    //状态
    @NoUpdate
    private String archivingState;

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getJobTypeId() {
        return jobTypeId;
    }

    public void setJobTypeId(String jobTypeId) {
        this.jobTypeId = jobTypeId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double getWorkTime() {
        return workTime;
    }

    public void setWorkTime(double workTime) {
        this.workTime = workTime;
    }

    public Timestamp getFillTime() {
        return fillTime;
    }

    public void setFillTime(Timestamp fillTime) {
        this.fillTime = fillTime;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getArchivingState() {
        return archivingState;
    }

    public void setArchivingState(String archivingState) {
        this.archivingState = archivingState;
    }

    public WorkLog(String logId, String projectId, Date logDate, String employeeId, String archivingState) {
        this.logId = logId;
        this.projectId = projectId;
        this.logDate = logDate;
        this.employeeId = employeeId;
        this.archivingState = archivingState;
    }

    @Override
    public String toString() {
        return "WorkLog{" +
                "logId='" + logId + '\'' +
                ", logDate=" + logDate +
                ", projectId='" + projectId + '\'' +
                ", jobTypeId='" + jobTypeId + '\'' +
                ", content='" + content + '\'' +
                ", workTime=" + workTime +
                ", fillTime=" + fillTime +
                ", employeeId='" + employeeId + '\'' +
                ", archivingState='" + archivingState + '\'' +
                '}';
    }
}