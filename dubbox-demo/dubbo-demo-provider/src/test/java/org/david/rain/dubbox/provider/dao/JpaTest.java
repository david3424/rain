package org.david.rain.dubbox.provider.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by david on 2014/12/29.
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager1", defaultRollback = true)
@ContextConfiguration("classpath:spring/applicationContext.xml")
public class JpaTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    UserDao userDao;

    @Test
    public void testSave() throws Exception {

//        em.persist(new TaskJpa("测试","描述",11l));
        System.out.println(userDao.findybyId(11l));
    }
}
