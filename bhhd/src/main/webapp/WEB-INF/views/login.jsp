<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>完美世界页游平台</title>
<%
	String path = request.getContextPath();
//	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path +"/";
	String basePath =  path +"/";
%>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/libs/bootstrap-2.3.2/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/style.css">

</head>
<body>
<div id="header" class="navbar navbar-inverse navbar-fixed-top">
	<div class="navbar-inner">
		<div class="container">
			<a class="brand" href="<c:url value='' />">完美世界页游平台</a>
		</div>
	</div>
</div>
<div class="container" align="center">
	<div class="row">
		<div class="span4" style="height: 340px;">
			<br><br><br>
			<img src="<%=basePath%>img/bhphs.jpg" alt="背景图">
			<h2>页游管理平台</h2>
		</div>

		<div class="span4">
			<div class="" style="height: 300px;">
				<br><br><br>
				<div style="height: 80px;">
				<div class="error ${param.error == true ? '' : 'hide'} alert alert-error" style="width: 200px;">
					<strong>Login failed</strong><br>
					<c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
				</div>
				</div>
					<form action="<%=basePath%>j_spring_security_check"
						class="bs-docs-example" method="post">
						<div class="input-prepend">
							<span class="add-on"><em class="icon-user"></em> </span> <input
								type="text" class="span3" name="j_username"
								value="${sessionScope['SPRING_SECURITY_LAST_USERNAME']}"
								placeholder="username">
						</div>
						<br>

						<div class="input-prepend">
							<span class="add-on"><em class="icon-lock"></em> </span> <input
								type="password" class="span3" name="j_password"
								placeholder="password">
						</div>
						<br> <input type="submit"
							value="&nbsp;&nbsp;&nbsp;&nbsp;Login&nbsp;&nbsp;&nbsp;&nbsp;"
							class="btn btn-primary" style="margin-right: 154px;">
					</form>
				</div>
		</div>
	</div>

	<hr>
	<%@ include file="commons/footer.jsp"%>
</div>
</body>
</html>