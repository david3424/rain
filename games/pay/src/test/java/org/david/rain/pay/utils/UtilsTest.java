package org.david.rain.pay.utils;

import org.david.rain.games.pay.utils.HttpUtil;
import org.david.rain.games.pay.utils.PropertiesUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by mac on 15-10-21.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext.xml")
public class UtilsTest {

    @Autowired
    HttpUtil httpUtil;
    
    @Test
    public void testPropertiesUtils() throws Exception {

        Properties p = PropertiesUtil.getProperties("/jdbc.properties");
        System.out.println(p.getProperty("jdbc.username"));
    }


    @Test
    public void testReadProperties() throws Exception {
        Properties p = new Properties();
        String filePath = "/jdbc.properties" ;
        filePath = PropertiesUtil.class.getResource("/").getPath() + filePath;
        System.out.println(filePath);
        InputStream in = new BufferedInputStream(new FileInputStream(filePath));//这里需要全路径，测试Junit读取的是target/test-classes，所以必须有测试的resources才行
//  好使~      InputStream ins = PropertiesUtil.class.getResourceAsStream("/jdbc.properties");
        p.load(in);
        System.out.println(p.getProperty("jdbc.username"));
    }


    @Test
    public void testRootPath() throws Exception {

        System.out.println(httpUtil.getRoot());
    }
}
