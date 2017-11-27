package com.noah.crm.cloud.common.event.domain;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 */
@Entity
@DiscriminatorValue("ASKRESP")
public class AskResponseEventPublish extends EventPublish {

    @Column
    private boolean success;

    @Column
    private Long askEventId;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Long getAskEventId() {
        return askEventId;
    }

    public void setAskEventId(Long askEventId) {
        this.askEventId = askEventId;
    }
}
