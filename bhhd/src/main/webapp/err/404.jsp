<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path +"/";
%>
<div align="center">
<br><br><br><br>
<h1>404</h1>
<h3>亲爱的用户，找不到该页面！<br>
请点击<a href="<%=basePath%>">此处</a>返回主页面！</h3>
</div>
