package org.david.rain.common.servlet;

/**
 * Created by mac on 15-5-12.
 */


import org.david.rain.act.dao.Idao;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "MyFirstServlet", urlPatterns = {"/MyFirstServlet"},
        initParams = {@WebInitParam(name="callback", value="org.david.rain.act.dao.dbutils.CommonWriterImp"),
                @WebInitParam(name="param2", value="value2")}
)
public class MyFirstServlet extends HttpServlet {

    private static final long serialVersionUID = -1915463532411657451L;
    private Idao idao;
    
    public void init(ServletConfig config) {

        String callback = config.getInitParameter("callback");
        Object callbackObject = null;

        if (null == idao) {

            try {
                Class callbackClass = Class.forName(callback);
                callbackObject = callbackClass.newInstance();//反射实现接口对应类实例化
            } catch (Exception e) {
                System.err.println("虚拟物品兑换系统回调接口实例化失败：类" + callback
                        + "不存在或不能被实例化");
            }
            if (!(callbackObject instanceof Idao)) {

                System.err.println("虚拟物品兑换回调接口实例化失败：没有继承com.pppppp.wmeovg.callback.servlet.ICallbackService接口");
            }

            idao = (Idao) callbackObject;
            try {
                idao.queryScalar("");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException
    {
        String xxx = getServletConfig().getInitParameter("xxx");
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/servlet/AuthImageServlet");
        rd.forward(request,response);
        //Do some work
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        Cookie []cookie_rs = request.getCookies();
        Cookie cookie = new Cookie("sessionId","123456789");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(-30);
        response.addCookie(cookie);
        
        response.sendRedirect("/servlet/AuthImageServlet");
        //Do some other work
    }
}
