<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path +"/";
%>
<div align="center">
<br><br><br><br>
<h1>500</h1>
<h3>亲爱的用户，很抱歉，服务器出错了！<br>
请点击<a href="<%=basePath%>">此处</a>返回主页面！</h3>
</div>
