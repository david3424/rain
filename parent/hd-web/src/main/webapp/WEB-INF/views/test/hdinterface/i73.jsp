<%@ page import="com.david.web.wanmei.common.util.ip.IpUtils" %>
<%@ page import="com.david.web.wanmei.service.hdinterface.I73ServiceInterface" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>173接口测试</title>
</head>
<body>
<%
    if (false) {
        response.sendRedirect("http://www.wanmei.com");
    } else {
        I73ServiceInterface i73ServiceInterface = (I73ServiceInterface) request.getAttribute("i73ServiceInterface");
%>
<form action="/173test" method="post">
    173登录login<br>
    用户名：<input type="text" name="login.username">&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit"><br>
</form>

<%
    String login_username = request.getParameter("login.username");
    if (StringUtils.isNotBlank(login_username)) {
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("login", login_username);
        Map<String, Object> json = i73ServiceInterface.login(map);
%>
<div>

    返回状态 ： <%=json.get("code")%> ---- 错误信息 :    <%=json.get("msg")%> ----- 用户ID：<%=json.get("userId")%>
</div>
<%
} else {
%>
<div>error</div>
<%}%>
<%}%>
</body>
</html>