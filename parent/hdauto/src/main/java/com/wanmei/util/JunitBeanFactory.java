package com.david.web.wanmei.util;

import org.springframework.context.ApplicationContext;

public class JunitBeanFactory {
	static {
		String[] locations = {"applicationContext.xml"};
		//ctx = new ClassPathXmlApplicationContext(locations);
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
