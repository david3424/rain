package com.noah.crm.cloud.common.event.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 */
@Entity
@DiscriminatorValue("NOTIFY")
public class NotifyEventPublish extends EventPublish {

}
