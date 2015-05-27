<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<%@page import="com.wanmei.wmeovg.request.service.IPrizeService"%>
<%@page import="com.wanmei.wmeovg.request.service.PrizeServiceManager"%>
<%@page import="com.wanmei.wmeovg.request.entity.PrizeLog"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<form action="queryByGid.jsp" method="post">
<table>
	<tr>
		<td>流水号：</td>
		<td><input type="text" name="gid"
			value="01-20100816-58385121-b4de-4d3f-beb5-2fc4173b8b9d" size="50" /></td>
	</tr>
	<tr>
		<td><input type="submit" value="查询" /></td>
	</tr>
</table>
</form>


<%
	IPrizeService prizeService = PrizeServiceManager.prizeService;

	String gid = request.getParameter("gid");

	if (gid != null) {
		PrizeLog prizeLog = prizeService.queryByGid(gid);
		if(prizeLog!=null)
			out.write(prizeLog.toString());
		else
			out.write("流水号" + gid + " 不存在");
	}
%>

</body>
</html>