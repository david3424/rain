package com.noah.crm.cloud.common.event.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by liubin on 2016/3/29.
 */
public class NotifyEventPublishRepositoryImpl implements NotifyEventPublishRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public EntityManager getEm() {
        return em;
    }

}
