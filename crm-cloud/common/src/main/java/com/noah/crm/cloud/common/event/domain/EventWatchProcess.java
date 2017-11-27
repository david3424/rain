package com.noah.crm.cloud.common.event.domain;


import com.noah.crm.cloud.common.domain.AbstractAuditEntity;
import com.noah.crm.cloud.common.event.constant.ProcessStatus;

import javax.persistence.*;

/**
 */
@Entity
@Table(name = "event_watch_process")
public class EventWatchProcess extends AbstractAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String failureInfo;

    @Column
    @Enumerated(EnumType.STRING)
    private ProcessStatus status = ProcessStatus.NEW;

    @Column
    private Long watchId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFailureInfo() {
        return failureInfo;
    }

    public void setFailureInfo(String failureInfo) {
        this.failureInfo = failureInfo;
    }

    public ProcessStatus getStatus() {
        return status;
    }

    public void setStatus(ProcessStatus status) {
        this.status = status;
    }

    public Long getWatchId() {
        return watchId;
    }

    public void setWatchId(Long watchId) {
        this.watchId = watchId;
    }


    @Override
    public String toString() {
        return "EventWatchProcess{" +
                "watchId=" + watchId +
                ", status=" + status +
                ", failureInfo='" + failureInfo + '\'' +
                ", id=" + id +
                "} " + super.toString();
    }
}
