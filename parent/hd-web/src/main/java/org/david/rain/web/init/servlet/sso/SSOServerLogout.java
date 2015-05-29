package org.david.rain.web.init.servlet.sso;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: ssw
 * Date: 13-11-13
 * Time: 上午10:55
 */
@SuppressWarnings("serial")
@Component()
@Scope("prototype")
public class SSOServerLogout extends HttpServlet implements Servlet {

    private static final long serialVersionUID = 0xd2fdbd8db2865b0eL;

    public SSOServerLogout() {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      		request.getSession().invalidate();
        String location =request.getParameter("serviceurl");
        response.sendRedirect(location);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }


}