package com.noah.crm.cloud.common.event.domain;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 */
@Entity
@DiscriminatorValue("REVOKE")
public class RevokeAskEventPublish extends EventPublish {

    @Column
    private Long askEventId;

    public Long getAskEventId() {
        return askEventId;
    }

    public void setAskEventId(Long askEventId) {
        this.askEventId = askEventId;
    }
}
