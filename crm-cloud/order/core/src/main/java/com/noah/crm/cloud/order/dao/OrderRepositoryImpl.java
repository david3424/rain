package com.noah.crm.cloud.order.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 */
public class OrderRepositoryImpl implements OrderRepositoryCustom {

    @PersistenceContext
    private EntityManager em;


    @Override
    public EntityManager getEm() {
        return em;
    }
}
