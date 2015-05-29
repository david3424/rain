package org.david.rain.web.init.servlet.filter;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * Date: 14-2-13
 * Time: 上午11:28
 * To change this template use File | Settings | File Templates.
 */
//@Component
public class CheckallSwitchFilter implements Filter {

    private static final String message_path = "/WEB-INF/views/error/close.jsp";
    //    @Autowired
    CheckSwitchUtil checkSwitchUtil;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        Integer status = -1;
        HttpServletRequest httprequest = (HttpServletRequest) request;
        HttpServletResponse httpresponse = (HttpServletResponse) response;
        try {
            status = checkSwitchUtil.getSwitchStatus();
        }catch (Exception e){
            e.printStackTrace();
            status = -1;
        }
        if(status == 0){ // 等于0  活动总开关关闭
//            pw.println("系统活动暂时维护中，请您稍后访问。");
            String path = httprequest.getServletPath();
            //排除 staic里面是图片 test是内部测试访问页
            Pattern pattern = Pattern.compile(".*passport.*"); //排除登录访问
            Matcher matcher = pattern.matcher(path);
            if(!StringUtils.contains(path,"/static/") && !StringUtils.contains(path,"/test/")
                    && !matcher.matches()){
                httprequest.getRequestDispatcher(message_path).forward(httprequest, httpresponse);//exclude static mertires
                return;
                //throw new FilterException("系统活动暂时维护中，请您稍后访问。");
            }
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub
    }


    @Override
    public void init(FilterConfig arg0) throws ServletException {
        checkSwitchUtil = (CheckSwitchUtil)getBean("checkSwitchUtil", arg0);
    }

    /**
     * 通过spring工具类获取，spring接管的实体bean
     *
     * @param beanName
     *            bean名称
     * @param config
     *            FilterConfig对象
     *
     * @return 实体bean
     */
    private Object getBean(String beanName, FilterConfig config) {
        WebApplicationContext wac = WebApplicationContextUtils
                .getRequiredWebApplicationContext(config.getServletContext());
        return wac.getBean(beanName);
    }



}
