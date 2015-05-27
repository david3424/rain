<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.List"%>
<%@page import="com.wanmei.wmeovg.request.service.IPrizeService"%>
<%@page import="com.wanmei.wmeovg.request.service.PrizeServiceManager"%>
<%@page import="com.wanmei.wmeovg.request.entity.PrizeLog"%>
<%@page import="java.util.Iterator"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<form action="queryByAccount.jsp" method="post">
<table>
	<tr>
		<td>账号：</td>
		<td><input type="text" name="account" size="10" /></td>
	</tr>
	<tr>
		<td><input type="submit" value="查询" /></td>
	</tr>
</table>
</form>


<%
	IPrizeService prizeService = PrizeServiceManager.prizeService;

	String account = request.getParameter("account");
	if (account != null) {
		List list = prizeService.queryByAccount(account);
		Iterator it = list.iterator();
		while (it.hasNext()) {
			out.print("<br/>" + ((PrizeLog)it.next()).getGid());
		}

	}
%>

</body>
</html>