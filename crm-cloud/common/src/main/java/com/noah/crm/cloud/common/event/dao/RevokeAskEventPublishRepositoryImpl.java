package com.noah.crm.cloud.common.event.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 */
public class RevokeAskEventPublishRepositoryImpl implements RevokeAskEventPublishRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public EntityManager getEm() {
        return em;
    }

}
