<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.wanmei.wmeovg.request.service.IPrizeService"%>
<%@page import="com.wanmei.wmeovg.request.service.PrizeServiceManager"%>
<%@page import="com.wanmei.wmeovg.request.entity.PrizeLog"%>
<%@page import="java.util.Iterator"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<form action="queryByRequestTime.jsp" method="post">
<table>
	<tr>
		<td>开始时间</td>
		<td><input type="text" name="start" value="2010-08-15 17:15:40"
			size="20" /></td>
	</tr>
	<tr>
		<td>截止时间</td>
		<td><input type="text" name="end" value="2010-08-16 17:15:40"
			size="20" /></td>
	</tr>
	<tr>
		<td><input type="submit" value="查询" /></td>
	</tr>
</table>
</form>


<%
	IPrizeService prizeService = PrizeServiceManager.prizeService;

	String start = request.getParameter("start");
	String end = request.getParameter("end");

	if (start != null && end != null) {
		SimpleDateFormat sdf = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");

		List list = prizeService.queryByRequestTime(sdf
				.parse(start), sdf.parse(end));

		Iterator it = list.iterator();
		while(it.hasNext()){
			out.print("<br/>" + ((PrizeLog)it.next()).getGid());
		}
	}
%>

</body>
</html>