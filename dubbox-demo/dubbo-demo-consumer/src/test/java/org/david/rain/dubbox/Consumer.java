package org.david.rain.dubbox;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext.xml")
public class Consumer {

	/*public static void main(String[] args) {
		ApplicationContext context = new FileSystemXmlApplicationContext("E:\\codes\\DubboDemo\\znn-service-consumer\\WebRoot\\WEB-INF\\dubbo-consumer.xml");
		DemoService demoService = (DemoService)context.getBean("demoService");// 获取远程服务代理
	    String hello = demoService.sayHello("world");// 执行远程方法
	    System.out.println(hello);
	}*/

	@Autowired
	DemoService demoService;

	@Test
	public void testDubbox() throws Exception {

		demoService.sayHello();

	}
}
