package org.david.rain.springtest;

import org.david.rain.act.dao.Idao;
import org.david.rain.entity.AccountBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.context.support.ServletContextResource;

import javax.servlet.ServletContext;
import java.io.InputStream;

/**
 * Created by david on 2014/12/2.
 * 测试spring的资源接口
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@WebAppConfiguration
//@ContextConfiguration("classpath:spring/applicationContext.xml")
public class ResourceTest {

//    @Autowired
    ServletContext application;
    @Test
    public void testFileSource() throws Exception {
        String filePath = "E:\\codes\\rain\\activity\\src\\test\\resources\\conf\\1.txt";
        Resource res1 = new FileSystemResource(filePath);
        Resource res2 = new ClassPathResource("conf/1.txt");
        Resource res3 = new ServletContextResource(application,"/WEB-INF/classes/conf/1.txt");
        InputStream ins1 = res1.getInputStream();
        InputStream ins2 = res2.getInputStream();
        System.out.println("res1:"+res1.getFilename());
        System.out.println("res2:"+res2.getFilename());
        System.out.println("res3:"+res3.getFilename());

    }

    @Test
    public void testEncodeResource() throws Exception {
        Resource res = new ClassPathResource("conf/1.txt");
        EncodedResource encRes = new EncodedResource(res,"utf-8");
        String content = FileCopyUtils.copyToString(encRes.getReader());
        System.out.println(content);
    }

    @Test
    public void testResourceLoader() throws Exception {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource resources[] = resolver.getResources("classpath*:**/*.xml");
        for(Resource rsource:resources){
            System.out.println(rsource.getDescription());
            if(rsource.getDescription().contains("datasource")){
                BeanFactory beanFactory = new XmlBeanFactory(rsource);
                Idao commonWriterImp = beanFactory.getBean("commonWriterImp",Idao.class);
                System.out.println(commonWriterImp);
            }
        }
    }

    @Test
    public void testApplicationContext() throws Exception {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/**/application*.xml");
        Idao commonWriterImp = applicationContext.getBean("commonWriterImp",Idao.class);
        System.out.println(commonWriterImp);
    }


    /*factorybean 测试*/
    @Test
    public void testXmlFactoryBean() {

        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource resource = resolver.getResource("classpath:conf/beans.xml");
        BeanFactory beanFactory  = new XmlBeanFactory(resource);
        AccountBean accountBean = beanFactory.getBean("accountBean",AccountBean.class);
        System.out.println(accountBean);
    }
}
