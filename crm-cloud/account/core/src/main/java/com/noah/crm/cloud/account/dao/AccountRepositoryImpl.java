package com.noah.crm.cloud.account.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 */
public class AccountRepositoryImpl implements AccountRepositoryCustom {

    @PersistenceContext
    private EntityManager em;


    @Override
    public EntityManager getEm() {
        return em;
    }
}
