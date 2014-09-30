<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="./include/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

<%@ include file="./include/reference.jsp"%>
<%
	int authorityGameListSize = authorityGameList.size();
	if(null == authorityGameList || authorityGameListSize < 1){ //没有访问权限
%>
	<%@ include file="/err/login_err.jsp"%>
<%		
	} //没有访问权限END 
	else if( authorityGameListSize == 1 ){ //只有一个访问权限，直接跳转到对应的游戏
%>
<head>
	<meta http-equiv="refresh" content="0; url=<c:url value='<%=basePath + "wanmei/" + authorityGameList.get(0)%>'/>">
</head>
<%
	}else{ //不止一个游戏的访问权限，进入到游戏选择页面
%>
<head>
<c:set  var="authorityGameList" value="<%=authorityGameList%>" scope="page"  />
<style type="text/css">
.div_center{
	background-color: #F1F1F1;
	vertical-align: middle;
    width: 50%;
	text-align: center;
    margin: 100px auto;
}

</style>

</head>
<body>
<div class="navbar navbar-inverse navbar-fixed-top">
    <div class="navbar-inner">
        <div class="container-fluid">
            <span class="brand" style="color: #fff">完美世界页游统计平台</span><!-- 这里的跳转地址在联运情况最好为当前地址 -->
            <div class="nav-collapse collapse">
				<ul class="navbar-text pull-right" style="margin: 10px">
					<li class="dropdown"><a href="#" role="button"
						class="dropdown-toggle" data-toggle="dropdown"><sec:authentication property="name" /><i class='icon-caret-down'></i></a>
						<ul class="dropdown-menu" role="menu">
							<li role="presentation"><a role="menuitem"  href="<%=basePath%>j_spring_security_logout">Logout</a></li>
						</ul>
					</li>
				</ul>
			</div>
        </div>
    </div>
</div>
<div class="div_center">
	<c:if test="${not empty authorityGameList}">
		<c:forEach items="${authorityGameList}" var="gm" varStatus="status">
				<c:if test="${gm == 'hzxf'}">
					<a href="<c:url value='<%=basePath + "wanmei/hzxf"%>'/>"><img alt="" src="<c:url value='/img/hzxf.jpg'/>"/></a>
				</c:if>
				<c:if test="${gm == 'bhphs'}">
					<a href="<c:url value='<%=basePath + "wanmei/bhphs"%>'/>"><img alt="" src="<c:url value='/img/bhphs.jpg'/>"/></a>
				</c:if>
				
				<c:if test="${gm == 'mslrly'}">
					<a href="<c:url value='<%=basePath + "wanmei/mslrly"%>'/>"><img alt="" src="<c:url value='/img/mslrly.jpg'/>"/></a>
				</c:if>
				
				<c:if test="${gm == 'touchly'}">
					<a href="<c:url value='<%=basePath + "wanmei/touchly"%>'/>"><img alt="" src="<c:url value='/img/touchly.jpg'/>"/></a>
				</c:if>
				<c:if test="${gm == 'yt'}">
					<a href="<c:url value='<%=basePath + "wanmei/yt"%>'/>"><img alt="" src="<c:url value='/img/yt.jpg'/>"/></a>
				</c:if>
				<c:if test="${gm == 'touch'}">
					<a href="<c:url value='<%=basePath + "wanmei/touch"%>'/>"><img alt="" src="<c:url value='/img/touch.jpg'/>"/></a>
				</c:if>
				<c:if test="${gm == 'tgmslr'}">
					<a href="<c:url value='<%=basePath + "wanmei/tgmslr"%>'/>"><img alt="" src="<c:url value='/img/tgmslr.jpg'/>"/></a>
				</c:if>
				<c:if test="${gm == 'bhphshw'}">
					<a href="<c:url value='<%=basePath + "wanmei/bhphshw"%>'/>"><img alt="" src="<c:url value='/img/bhphshw.jpg'/>"/></a>
				</c:if>
		</c:forEach>
	</c:if>	
</div>
<%@ include file="./commons/footer.jsp"%>
</body>

<%		
	}
%>
</html>



