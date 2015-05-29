<%--
  Created by IntelliJ IDEA.
  User: czw
  Date: 13-5-28
  Time: 下午3:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <title>测试服务器图片上传</title>
</head>
<body>


<div class="container">

    <div class="hero-unit">
        <h1>测试环境图片上传</h1>
    </div>
    <ul>
        <li>
            <span>图片的路径：${imgUrl}</span>
        </li>
        <li>
            <span style="color: red">上传图片：</span>
            <img alt="你的图片" src="${imgUrl}" />
        </li>
    </ul>

</div>

</body>
</html>