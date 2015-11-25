<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="org.david.rain.common.util.ip.IpUtils" %>
<%@ page import="java.util.Date" %>
<html>
<head><title>Cluster USER</title></head>
<body>
<%
    if (false) {
        response.sendRedirect("http://www.pppppp.com");
    } else {
        session.setAttribute("now", new Date());
        out.println("<br> SESSION ID:" + session.getId() + "<br>");
        out.println("<br> Host info:" + request.getLocalAddr() + ":" + request.getLocalPort() + "<br>");
        out.println("<br> SESSION U:" + session.getAttribute("USER") + "<br>");
        Object o = session.getAttribute("now");
        out.println("<br>now=" + o.toString());
        out.println("yourIp is :" + IpUtils.gerRealIp(request));
    }
%>
</body>
</html>
