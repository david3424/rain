package com.noah.crm.cloud.coupon.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author xdw9486
 */
public class CouponRepositoryImpl implements CouponRepositoryCustom {

    @PersistenceContext
    private EntityManager em;


    @Override
    public EntityManager getEm() {
        return em;
    }
}
