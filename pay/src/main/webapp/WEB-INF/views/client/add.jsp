<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<jsp:include page="/WEB-INF/layouts/default.jsp"/>
<html>
<head>
    <title>客户端管理</title>

</head>

<body>
<c:if test="${not empty message}">
		<div id="message" ><button>×</button>${message}</div>
	</c:if>
<form id="inputForm" action="${ctx}/client/${action}" method="post" >
    <input type="text" size="20" name="title">
    <input type="submit" value="insert">
</form>
</body>
</html>
