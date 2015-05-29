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
    <title></title>
</head>
<body>

<div class="container">
    <div class="hero-unit">
        <h1>图片上传接口测试</h1>
    </div>

    <form id="send_form" action="${ctx}/imageservice/upload" class="form-horizontal" method="post" enctype="multipart/form-data">

        <div class="control-group">
            <label class="control-label" for="file">图片：</label>
            <div class="controls">
                <input class="span3" type="file" name="file"id="file"/>
            </div>

        <div class="form-actions">
            <button type="submit" id="btn_send">上传</button>
        </div>
    </form>
</div>
</div>

</body>
</html>