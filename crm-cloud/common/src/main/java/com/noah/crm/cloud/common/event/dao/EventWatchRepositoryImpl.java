package com.noah.crm.cloud.common.event.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 */
public class EventWatchRepositoryImpl implements EventWatchRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public EntityManager getEm() {
        return em;
    }

}
