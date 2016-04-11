package org.david.rain.wmproxy.module.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.david.rain.wmeovg.request.util.DateUtil;
import org.david.rain.wmproxy.common.util.IpUtils;
import org.david.rain.wmproxy.module.sys.entity.Log;
import org.david.rain.wmproxy.module.sys.entity.User;
import org.david.rain.wmproxy.module.sys.manager.LogMng;
import org.david.rain.wmproxy.module.sys.manager.UserMng;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


import java.io.IOException;
import java.security.Principal;
import java.security.cert.X509Certificate;
import java.util.Date;

/**
 * 登陆过滤器
 * <p/>
 * 判断权限，记录User
 */
public class LoginFilter implements Filter {

    protected Logger log = LoggerFactory.getLogger(getClass());

    private static final String USER_BEAN_NAME = "userMngImpl";
    private static final String LOG_BEAN_NAME = "logMngImpl";
    private UserMng userMng;
    private LogMng logMng;

//	private static final String CERTIFICATE_KEY = "javax.servlet.request.X509Certificate";

    public void init(FilterConfig filterConfig) throws ServletException {
        WebApplicationContext wac = WebApplicationContextUtils
                .getRequiredWebApplicationContext(filterConfig
                        .getServletContext());
        userMng = wac.getBean(USER_BEAN_NAME, UserMng.class);
        logMng = wac.getBean(LOG_BEAN_NAME, LogMng.class);
    }

    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

		/*
         * ======================================================================
		 * 排除发奖请求 、ws接口请求 和 action请求(action系统已配置拦截器，用于ajax访问限制)
		 */
        String requestURI = request.getRequestURI();
        String ctxPath = request.getContextPath();
        String requestServlet = "/servlet/prizerequest";
        String wsServlet = "/hessian/prizeService";

        String path = requestURI.substring(ctxPath.length());
        if (path.equals(requestServlet) || path.equals(wsServlet)
                || requestURI.endsWith(".action")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        /*
		 * =====================================================================
		 */

        String sessionUser = (String) request.getSession().getAttribute( User.LOGIN_NAME_KEY);
        if (sessionUser == null) {

            String loginName = null;
            loginName = "admin";

            User user = userMng.getByLoginName(loginName);

            if (user == null) {

                log.info("用户({})登录失败：没有权限", loginName);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                        "==========您没有权限访问此页面==========");
                return;
            } else {
                String name = user.getName();
                log.info("用户{}({})登录成功", name, loginName);
                Log log = new Log();
                log.setUser(user);
                log.setContent("登录成功");
                log.setIp(IpUtils.gerRealIp(request));
                log.setCreatetime(DateUtil
                        .format(new Date(), DateUtil.DATETIME));

                logMng.save(log);

                request.getSession().setAttribute(User.LOGIN_NAME_KEY,
                        loginName);
                response.sendRedirect(request.getContextPath() + "/index.html");
                filterChain.doFilter(request, response);
                return;
            }
        } 
        /*else {
            if (certs == null || certs.length == 0) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                        "==========请检查您的Key是否有效==========");
                return;
            } else if (request.getSession().getAttribute(User.LOGIN_NAME_KEY) == null) {
                // 不合法；
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                        "==========您没有权限访问此页面==========");
                return;
            }
        }*/

        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void destroy() {
    }

}
