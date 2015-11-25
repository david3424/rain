package com.david.web.pppppp.init.servlet.filter;

import com.david.web.pppppp.common.SessionConst;
import com.david.web.pppppp.entity.User;
import com.david.web.pppppp.service.account.AccountService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.security.cert.X509Certificate;

/**
 * Created with IntelliJ IDEA.
 * User: czw
 * Date: 13-10-21
 * Time: 下午3:34
 * To change this template use File | Settings | File Templates.
 */
public class LoginFilter extends PathMatchingFilter {
    private static final String CERTIFICATE_KEY = "javax.servlet.request.X509Certificate";

    @Autowired
    @Qualifier("accountService")
    private AccountService accountService;

    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        System.out.println("in login filter");
        X509Certificate certs[] = (X509Certificate[]) request.getAttribute(CERTIFICATE_KEY);
        String sUser = null;
        HttpServletRequest hrequest = (HttpServletRequest) request;
        HttpServletResponse hresponse = (HttpServletResponse) response;
        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated() && certs != null && certs.length > 0) {
            X509Certificate certs1 = certs[0];

            Principal principal = certs1.getSubjectDN();
            String sName = principal.getName();

            int index = sName.indexOf("CN=");
            int index2 = sName.indexOf(",", index);
            if (index >= 0 && index2 > index + 3)
                sUser = sName.substring(index + 3, index2);
            else if (index >= 0 && index2 == -1)
                sUser = sName.substring(index + 3);

            if (sUser != null && sUser.length() > 0) {
                sUser = sUser.trim();
            }

            try {
                User user = accountService.findUserByLoginName(sUser);
                if (user == null) {
                    hresponse.sendRedirect("/static/info/nouserinfo.jsp");
                    return false;
                }
                UsernamePasswordToken token = new UsernamePasswordToken(user.getLogin_name(), "admin");
                SecurityUtils.getSubject().login(token);
                subject.getSession().setAttribute(SessionConst.LOGIN_SESSION_NAME, user);
                System.out.println("login by keyloginfilter user:" + user.getLogin_name());
                //session.setAttribute(SessionConst.LOGIN_SESSION_NAME,user);
            } catch (Exception e) {
                e.printStackTrace();
                hrequest.setAttribute("message", "权限解析异常，请联系活动开发组。");
                hresponse.sendRedirect("/static/info/commonerror.jsp");
                return false;
            }
        } else {
            if (certs == null || certs.length == 0) {
                hresponse.sendRedirect("/static/info/wrongkey.jsp");
                return false;
            } else {
                User user = (User) subject.getSession().getAttribute(SessionConst.LOGIN_SESSION_NAME);
                if(user != null)
                {
                    System.out.println("already login :" + user);
                }
                else
                {
                    System.out.println("shiro session do not key by the session user but isAuthenticated");
                }
            }
        }
        return true;
    }
}
