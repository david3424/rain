package org.david.rain.common.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JunitBeanFactory {
	static {
		String[] locations = {"applicationContext.xml"};
		ctx = new ClassPathXmlApplicationContext(locations);
	}
	
	private JunitBeanFactory(){}
	public static ApplicationContext ctx;
	
	public static Object getBean(String bean){
		return ctx.getBean(bean);
	}
	
	public static ApplicationContext getContext(){
		return ctx;
	}
}
