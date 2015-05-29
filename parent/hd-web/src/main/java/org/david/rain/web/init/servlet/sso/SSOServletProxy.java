package org.david.rain.web.init.servlet.sso;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import java.io.IOException;

/**
 * 实现代理类，用于Servlet转为Spring管理的Servlet Bean.实现测试、正式登录区别处理
 * Date: 13-12-7
 * Time: 下午1:19
 * To change this template use File | Settings | File Templates.
 */
@SuppressWarnings("serial")
public class SSOServletProxy extends GenericServlet {

    private Servlet proxy;

    @Override
    public void init() throws ServletException {
        super.init();

        //初始化Spring容器
        WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        this.proxy = (Servlet) wac.getBean(getServletName());
        proxy.init(getServletConfig());
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        proxy.service(servletRequest, servletResponse);//代理Servlet
    }
}
