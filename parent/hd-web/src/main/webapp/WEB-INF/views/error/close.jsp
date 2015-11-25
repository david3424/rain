<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.slf4j.Logger,org.slf4j.LoggerFactory" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<%
    Throwable ex = null;
    String show_msg = "";
    if (exception != null) {
        ex = exception;
        if (request.getAttribute("javax.servlet.error.exception") != null)  {
            ex = (Throwable) request.getAttribute("javax.servlet.error.exception");
            show_msg = ex.getMessage();
        }
        //记录日志
        Logger logger = LoggerFactory.getLogger("close.jsp");
        logger.error(ex.getMessage(), ex);
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="refresh" content="5;url=http://www.pppppp.com">
    <title>活动暂时关闭</title>
    <script type="text/javascript" src="${ctx}/static/jquery/jquery-1.8.2.min.js"></script>
    <script type="text/javascript">

        function countdown(secs){
            document.getElementById("jump").innerHTML=secs;
            if(--secs>0){
                setTimeout("countdown("+secs+")",1000);
            }
        }
        countdown(5);
    </script>
</head>

<body onload="countdown(5)">
<div style="text-align: center">
    <img src="${ctx}/static/images/error.jpg" alt="close" />

    <h3><span style="color:#ff0000" id="jump">5</span>秒钟后跳转到完美官网页面。</h3>
</div>


</body>
</html>
