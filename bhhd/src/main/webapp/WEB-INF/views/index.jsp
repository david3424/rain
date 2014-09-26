<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>页游统计平台</title>

<%@ include file="./include/reference.jsp"%>

</head>
<body>
<%@ include file="./commons/header.jsp"%>
<div class="container-fluid">
<%
	//判断当前访问的游戏是否在权限之内
	boolean isHave = false;
	if(null != currentGameShort ){
		for(String gm : authorityGameList){
			if(currentGameShort.equals(gm)){
				isHave = true;
				break;
			}
		}	
	}
	if(isHave){
%>
	
		<div class="row-fluid">
			<div id="menu" class="span2">
				<%@ include file="./commons/menu.jsp"%>
			</div>
			<div class="span10">
				<div id="content" class="container-fluid">
					<div class="hero-unit">
						<h1>欢迎访问完美页游管理平台</h1>

						<p style="margin-top: 20px">
							为了用户的安全性及更好的用户体验，请使用<strong>火狐</strong>浏览器。
						</p>
						<p>
							<a href="<%=basePath%>browser/Firefox-Setup-21.0-CHS.exe" class="btn btn-success btn-large">下载火狐浏览器 &raquo;</a>
						</p>
					</div>
				</div>
				<!-- content end -->
			</div>
		</div>
		
<%
	}else{	
%>
	<%@ include file="/err/login_err.jsp"%>
<%
	}
%>

	<%@ include file="./commons/footer.jsp"%>
</div>
</body>
</html>