<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="com.wanmei.webservice.ServiceManage"%>
<%@page import="common.UserInfo"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>AU接口测试</title>
</head>
<body>
<%
	System.out.println("--------------begin try login....-------------------------");
	out.println("--------------begin try login....-------------------------");
	
	String account="lvxuehu";
	String pass = "4252520";
	
	UserInfo userinfo = new UserInfo();
	userinfo.userName = account;
	userinfo.ip = request.getRemoteAddr();
	int result = 0;
	if(ServiceManage.activityService.tryLogin(account, pass, userinfo)){
		result = 1;
	}
	
	if(result==0){
	    out.print("登录失败！密码错误");
	}else if(result==1){
	    out.print("登录成功!");
	}else{
	    out.print("result is :"+result);
	}
	
	System.out.println("--------------begin try login end....-------------------------");
	out.println("--------------begin try login end....-------------------------");
%>
</body>
</html>