<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<jsp:include page="/WEB-INF/layouts/default.jsp"/>

<html>
<head>
	<title>客户端管理</title>
</head>

<body>
<br><br>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<div class="row">
		<div class="span4 offset7">
			<form class="form-search" action="#">
			 	<label>名称：</label> <input type="text" name="search_LIKE_title" class="input-medium" value="${param.search_LIKE_title}"> 
			    <button type="submit" class="btn">Search</button>
		    </form>
	    </div>
	    <tags:sort/>
	</div>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>客户端(appid)</th><th>ip地址</th><th>私钥</th><th>加入时间</th><th>授权</th><th>管理</th></tr></thead>
		<tbody>
		<c:forEach items="${clients}" var="client">
			<tr>
				<td><a href="${ctx}/client/update/${client.id}">${client.name}(${client.appid})</a></td>
                <td>${client.ip}</td>
                <td>${client.privatekey}</td>
                <td>${client.createtime}</td>
                <td>
                    <c:choose>
                    <c:when test="${client.status==0}">
                        <a class="btn-link" href="${ctx}/client/permit?id=${client.id}">点击拒绝</a>
                    </c:when>
                        <c:otherwise>
                            <a class="btn-danger" href="${ctx}/client/permit?id=${client.id}">点击允许</a>
                        </c:otherwise>
                    </c:choose>
                </td>
				<td> <a class="btn-danger" href="${ctx}/client/delete/${client.id}">删除</a></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<tags:pagination page="${clients}" paginationSize="5"/>

	<div><a class="btn" href="${ctx}/client/create">创建任务</a></div>
</body>
</html>
