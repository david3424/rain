package org.david.rain.wmproxy.module.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@Lazy(false)
public class CommonApplicationContextUtil implements ApplicationContextAware {

	private static ApplicationContext context;//声明一个静态变量保存 
	
	@SuppressWarnings("static-access")
	public void setApplicationContext(ApplicationContext contex)
			throws BeansException {
		System.out.println("ApplicationContext init..." + contex);
		this.context=contex; 
	}
	public static ApplicationContext getContext(){ 
	  return context; 
	} 
}
