<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<% response.addHeader("__forbidden", "true"); %>
<div align="center">
<br><br><br><br>
<h3>亲爱的用户<sec:authentication property="name"/>，您目前没有权限访问此页面！<br>
请点击<a href="<c:url value='/'/>">此处</a>返回主页面！</h3>
</div>
