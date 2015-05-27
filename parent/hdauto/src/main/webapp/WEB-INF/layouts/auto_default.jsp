<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
    <title>Auto发奖:<sitemesh:title/></title>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <meta http-equiv="Cache-Control" content="no-store"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Expires" content="0"/>

    <link type="image/x-icon" href="${ctx}/static/images/dollar.ico" rel="shortcut icon">
    <link href="${ctx}/static/bootstrap/2.1.1/css/bootstrap.min.css" type="text/css" rel="stylesheet"/>
    <link href="${ctx}/static/bootstrap/2.1.1/css/bootstrap-responsive.min.css" rel="stylesheet">
    <link href="${ctx}/static/jquery-validation/1.10.0/validate.css" type="text/css" rel="stylesheet"/>
    <link href="${ctx}/static/styles/default.css" type="text/css" rel="stylesheet"/>

    <script src="${ctx}/static/jquery/1.7.2/jquery.min.js" type="text/javascript"></script>
    <script src="${ctx}/components/jquery/jquery.form2.js" type="text/javascript"></script>
    <script src="${ctx}/static/jquery-validation/1.10.0/jquery.validate.min.js" type="text/javascript"></script>
    <script src="${ctx}/static/jquery-validation/1.10.0/messages_bs_zh.js" type="text/javascript"></script>
    <sitemesh:head/>
</head>

<body>
    <%@ include file="/WEB-INF/layouts/auto_header.jsp" %>
        <sitemesh:body/>
    <%@ include file="/WEB-INF/layouts/footer.jsp" %>
<script src="${ctx}/static/bootstrap/2.1.1/js/bootstrap.min.js" type="text/javascript"></script>
</body>
</html>