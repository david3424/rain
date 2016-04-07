package org.david.rain.wmproxy.module.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class SysInitServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected Logger log = LoggerFactory.getLogger(getClass());
	
	//public static WebApplicationContext WAC = null;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		/*
		WAC = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
		
		System.out.println("init sys WAC = " + WAC + "========");
		*/
	}
	
}
