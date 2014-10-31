package org.david.rain.test;

import org.david.rain.dao.MenuTypeDao;
import org.david.rain.model.MenuType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;

/**
 * Created by david on 2014/10/30.
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:config/spring-application.xml"})
public class MenuTypeTest {
    @Resource
    MenuTypeDao menuTypeDao;


    @Test
    public void daoTest(){
        System.out.println(menuTypeDao.addMenuType(new MenuType("测试菜单", 30, "测试用的菜单")));
    }

    @Test
    public void daoListTest(){
        System.out.println(menuTypeDao.getMenuTypeList(0,30));
    }
}
