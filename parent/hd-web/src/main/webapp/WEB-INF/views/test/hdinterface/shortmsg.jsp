<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.david.web.wanmei.service.hdinterface.ShortMessageInterface" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<html>
<head>
    <title>短信测试</title>
</head>
<body>
<%
    if (false) {
        response.sendRedirect("http://www.wanmei.com");
    } else {
        ShortMessageInterface shortMessageInterface = (ShortMessageInterface) request.getAttribute("shortMessageInterface");
%>
<form action="/shortmsgtest" method="post">
    短信发送sendMessage<br>
    活动简称：<input type="text" name="sendMessage.huodongId">&nbsp;&nbsp;&nbsp;&nbsp;
    手机号码：<input type="text" name="sendMessage.phone">&nbsp;&nbsp;&nbsp;&nbsp;
    短信内容：<input type="text" name="sendMessage.content">&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit"><br>
</form>

<%
    String sendMessage_huodongId = request.getParameter("sendMessage.huodongId");
    String sendMessage_phone = request.getParameter("sendMessage.phone");
    String sendMessage_content = request.getParameter("sendMessage.content");
    if (StringUtils.isNotBlank(sendMessage_huodongId) && StringUtils.isNotBlank(sendMessage_phone) && StringUtils.isNotBlank(sendMessage_content)) {
        boolean flag = shortMessageInterface.sendMessageWithType(sendMessage_huodongId, sendMessage_phone, sendMessage_content);
%>
<div>
    是否成功：<%=flag%>
</div>
<%
} else {
%>
<div>error</div>
<%}%>

<%}%>
</body>
</html>