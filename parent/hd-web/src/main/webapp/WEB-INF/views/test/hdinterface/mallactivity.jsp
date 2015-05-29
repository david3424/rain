<%@ page import="com.david.web.wanmei.service.hdinterface.wrapper.MallActivityServiceWrapper" %>
<%@ page import="common.HdUser" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>角色VIP验证</title>
</head>
<body>
<%
    if (false) {
        response.sendRedirect("http://www.wanmei.com");
    } else {
        MallActivityServiceWrapper mallActivityServiceWrapper = (MallActivityServiceWrapper) request.getAttribute("mallActivityServiceInterface");
%>
<form action="/mallactivitytest" method="post">
    查询用户最近一次的升级时间getLastRankUpTime<br>
    用户ID：<input type="text" name="getLastRankUpTime.userid">&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit"><br>
</form>

<%
    String getLastRankUpTime_userid = request.getParameter("getLastRankUpTime.userid");
    if (StringUtils.isNumeric(getLastRankUpTime_userid)) {
        HdUser hdUser = new HdUser();
        hdUser.setUserid(getLastRankUpTime_userid);
        Date date = mallActivityServiceWrapper.getLastRankUpTime(hdUser);
%>
<div>
    若用户不存在或没有升级过，返回 null：<%=date%>
</div>
<%
} else {
%>
<div>error</div>
<%}%>

<br><br>
<%}%>
</body>
</html>