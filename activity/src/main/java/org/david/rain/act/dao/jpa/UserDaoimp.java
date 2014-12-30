package org.david.rain.act.dao.jpa;

import org.david.rain.act.entity.TaskJpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by david on 2014/12/29.
 * 使用jpa
 */
public class UserDaoimp  {

    @PersistenceContext
    private EntityManager em;


    public void save(TaskJpa accountInfo) {

        em.persist(accountInfo);
    }
}
