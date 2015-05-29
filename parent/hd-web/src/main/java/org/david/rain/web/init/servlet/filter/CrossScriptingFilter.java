package org.david.rain.web.init.servlet.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by david on 13-11-14.
 * xss filter
 */
public class CrossScriptingFilter implements Filter{

    /* (non-Javadoc)
    	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
    	 */
    	@Override
    	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
    		CrossScriptingRequestWrapper xssRequest = new CrossScriptingRequestWrapper((HttpServletRequest) request);
    		filterChain.doFilter(xssRequest, response);
    	}

    	@Override
    	public void destroy() {
    		// TODO Auto-generated method stub
    	}


    	@Override
    	public void init(FilterConfig arg0) throws ServletException {
    		// TODO Auto-generated method stub
    	}
}
