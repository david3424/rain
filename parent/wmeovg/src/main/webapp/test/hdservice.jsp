<%@ page import="common.ActivityServiceInterface" %>
<%@ page import="com.wanmei.webservice.ServiceManage" %>
<%@ page import="common.UserInfo" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head><title>Simple jsp page</title>
  </head>
  <body>
    <div style="text-align: center;">

        <%
            ActivityServiceInterface as = ServiceManage.activityService;
            long rtn1 = as.verifyFactionMaster3("jiutiao2011",211,"冬天来了","9527",new UserInfo());
            long rtn2 = as.verifyFactionMaster3("rwceshi003",211,"rwceshi3","2级俱乐部",new UserInfo());
            long rtn3 = as.verifyFactionMaster3("rwceshi004",211,"rwceshi4","3级俱乐部",new UserInfo());
        %>
        返回：冬天来了:9527<%=rtn1%>
        返回：rwceshi3:2级俱乐部<%=rtn2%>
        返回：rwceshi4:3级俱乐部<%=rtn3%>
         >0  成功,  <br>
        -1.网络通信错误   <br>
        -2.帐号不存在    <br>
        -3.密码错误     <br>
        -4.角色不存在    <br>
        -5.帮派名错误    <br>
        -6.角色不是帮主   <br>
        -7.帮派等级没有达到二级 <br>
        -8.其他错误     <br>

    </div>
  </body>
</html>