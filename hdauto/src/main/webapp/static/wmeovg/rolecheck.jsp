<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<%@page import="com.wanmei.wmeovg.request.service.IPrizeService"%>
<%@page import="com.wanmei.wmeovg.request.service.PrizeServiceManager"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色验证测试</title>
</head>
<body>
	
	<form action="rolecheck.jsp">
		<table>
			<tr>
				<td>账号</td>
				<td><input type="text" name="account"/></td>
			</tr>
			<tr>
				<td>角色名</td>
				<td><input type="text" name="rolename"/></td>
			</tr>
			<tr>
				<td>所在服务器</td>
				<td><input type="text" name="server"/></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit"  value="查询"/></td>
			</tr>
		</table>
	</form>
	
	<%
		String account = request.getParameter("account");
		String rolename = request.getParameter("rolename");
		Integer server = new Integer(request.getParameter("server"));
		
		
		if(account != null && rolename != null && server != null){
			IPrizeService prizeService = PrizeServiceManager.prizeService;
			int rtn = prizeService.verifyRoleExists(account, server, rolename);
		
			out.write("return: " + rtn);
		}
	%>
	
</body>
</html>