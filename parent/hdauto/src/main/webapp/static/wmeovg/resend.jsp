<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="com.david.web.pppppp.wmeovg.request.service.IPrizeService"%>
<%@page import="com.david.web.pppppp.wmeovg.request.service.PrizeServiceManager"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<form action="resend.jsp" method="post">
<table>
	<tr>
		<td>流水号</td>
		<td><input type='text' name='gid' value="" /></td>
	</tr>
	<tr>
		<td><input type="submit" value="提交" /></td>
	</tr>
</table>
</form>

<%
	String gid = request.getParameter("gid");
	IPrizeService prizeService = PrizeServiceManager.prizeService;
	int rtn = prizeService.resend(gid);
	
	out.write("重发请求：" + rtn);
%>
</body>
</html>