package org.david.rain.wmproxy.module.interceptor;


import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.david.rain.wmproxy.module.sys.entity.User;

@SuppressWarnings("serial")
public class CheckLoginInterceptor extends AbstractInterceptor{
	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
//		String sessionUser = (String) request.getSession().getAttribute(
//				User.LOGIN_NAME_KEY);
//		System.out.println("***********" + sessionUser);
//        if (sessionUser == null) {
//	        
//	        request.setAttribute("msg","您没有登录或登录超时。");
//            return "impermit";
//        }
        
        String sessionUser = (String) request.getSession().getAttribute(
				User.LOGIN_NAME_KEY);
        
        HttpServletResponse response = ServletActionContext.getResponse();
		if (sessionUser == null) {
			String ajaxHeader = request.getHeader("x-requested-with");
			if(ajaxHeader != null && ajaxHeader.equalsIgnoreCase("XMLHttpRequest")){
				PrintWriter out = response.getWriter();
				response.setHeader("sessionstatus", "timeout");
				out.write("{sessionstatus:'timeout'}");
				return null;
			}else{
				response.sendRedirect(request.getContextPath());
				return null;
			}
		}
		
        return actionInvocation.invoke();
	}
	
}
