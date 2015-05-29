<%@ page import="com.david.web.wanmei.service.hdinterface.CombinedServiceInterface" %>
<%@ page import="common.HdUser" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page import="org.david.rain.web.service.hdinterface.wrapper.CombinedServiceWrapper" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>合服接口测试</title>
</head>
<body>
<%
    if (false) {
        response.sendRedirect("http://www.wanmei.com");
    } else {
        CombinedServiceWrapper combinedServiceInterface = (CombinedServiceWrapper) request.getAttribute("combinedServiceInterface");
%>
<form action="/combinedservicetest" method="post">
    合服服务器名查询searchServerName<br>
    游戏ID：<input type="text" name="searchServerName.gameid">&nbsp;&nbsp;&nbsp;&nbsp;
    服务器ID：<input type="text" name="searchServerName.server">&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit"><br>
</form>

<%
    String searchServerName_gameid = request.getParameter("searchServerName.gameid");
    String searchServerName_server = request.getParameter("searchServerName.server");
    if (StringUtils.isNumeric(searchServerName_gameid) && StringUtils.isNumeric(searchServerName_server)) {
        HdUser hdUser = new HdUser();
        hdUser.setGameid(Integer.parseInt(searchServerName_gameid));
        hdUser.setServer(Integer.parseInt(searchServerName_server));
        String name = combinedServiceInterface.searchServerName(hdUser);
%>
<div>
    服务器名 ：   <%=name%>
</div>
<%
} else {
%>
<div>error</div>
<%}%>
<%}%>
</body>
</html>