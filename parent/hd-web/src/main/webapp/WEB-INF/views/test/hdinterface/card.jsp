<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.david.web.wanmei.service.hdinterface.ShortMessageInterface" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page import="com.david.web.wanmei.service.hdinterface.wrapper.CardServiceWrapper" %>
<%@ page import="common.HdUser" %>
<html>
<head>
    <title>取卡测试</title>
</head>
<body>
<%
    if (false) {
        response.sendRedirect("http://www.wanmei.com");
    } else {
        CardServiceWrapper cardServiceWrapper = (CardServiceWrapper) request.getAttribute("cardServiceInterface");
%>
<form action="/cardservicetest" method="post">
    查询卡状态queryCardStatus<br>
    账号：<input type="text" name="queryCardStatus.username">&nbsp;&nbsp;&nbsp;&nbsp;
    账号ID：<input type="text" name="queryCardStatus.userid">&nbsp;&nbsp;&nbsp;&nbsp;
    卡号：<input type="text" name="queryCardStatus.sn">&nbsp;&nbsp;&nbsp;&nbsp;
    活动开关：<input type="text" name="queryCardStatus.hdid">&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit"><br>
</form>

<%
    String queryCardStatus_username = request.getParameter("queryCardStatus.username");
    String queryCardStatus_userid = request.getParameter("queryCardStatus.userid");
    String queryCardStatus_sn = request.getParameter("queryCardStatus.sn");
    String queryCardStatus_hdid = request.getParameter("queryCardStatus.hdid");
    if (StringUtils.isNotBlank(queryCardStatus_username) && StringUtils.isNotBlank(queryCardStatus_userid)
            && StringUtils.isNotBlank(queryCardStatus_sn) && StringUtils.isNotBlank(queryCardStatus_hdid)) {
        HdUser hdUser = new HdUser();
        hdUser.setUsername(queryCardStatus_username);
        hdUser.setUserid(queryCardStatus_userid);
        hdUser.setSn(queryCardStatus_sn);
        hdUser.setHdid(queryCardStatus_hdid);
        hdUser.setIp("127.0.0.1");
        Integer status = cardServiceWrapper.queryCardStatus(hdUser);
%>
<div>
    状态：<%=status%>
</div>
<%
} else {
%>
<div>error</div>
<%}%>

<br><br>

<form action="/cardservicetest" method="post">
    取卡getCard<br>
    账号：<input type="text" name="getCard.username">&nbsp;&nbsp;&nbsp;&nbsp;
    账号ID：<input type="text" name="getCard.userid">&nbsp;&nbsp;&nbsp;&nbsp;
    类型(填数字，1=激活码，2=新手卡，3=媒体卡，4=特权卡)：<input type="text" name="getCard.type">&nbsp;&nbsp;&nbsp;&nbsp;
    活动开关：<input type="text" name="getCard.hdid">&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit"><br>
</form>

<%
    String getCard_username = request.getParameter("getCard.username");
    String getCard_userid = request.getParameter("getCard.userid");
    String getCard_type = request.getParameter("getCard.type");
    String getCard_hdid = request.getParameter("getCard.hdid");
    if (StringUtils.isNotBlank(getCard_username) && StringUtils.isNotBlank(getCard_userid)
            && StringUtils.isNumeric(getCard_type) && StringUtils.isNotBlank(getCard_hdid)) {
        HdUser hdUser = new HdUser();
        hdUser.setUsername(getCard_username);
        hdUser.setUserid(getCard_userid);
        hdUser.setType(Integer.parseInt(getCard_type));
        hdUser.setHdid(getCard_hdid);
        hdUser.setIp("127.0.0.1");
        String status = cardServiceWrapper.getCard(hdUser);
%>
<div>
    状态：<%=status%>
</div>
<%
} else {
%>
<div>error</div>
<%}%>

<%}%>
</body>
</html>