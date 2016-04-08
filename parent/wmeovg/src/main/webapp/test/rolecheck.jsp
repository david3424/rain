<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="com.wanmei.webservice.ServiceManage"%>
<%@page import="common.UserInfo"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色验证测试</title>
</head>
<body>
	
	<form action="rolecheck.jsp" method="post">
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
		String server = request.getParameter("server");
		
		if(account != null && rolename != null && server != null){
			
			System.out.println("账号：" + account + "账号:" +  server + "角色名:" +  rolename);
			int rtn = ServiceManage.activityService.verifyRoleExists(account, Integer.parseInt(server), rolename, new UserInfo());
		
			out.write("return: " + rtn);
		}
	%>
	
</body>
</html>