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
        <h1>短信接口测试</h1>

        <p>填写您的电话号码，密码找人问问，就可以发短信了。</p>

    </div>

    <c:if test="${not empty message}">
        <div id="message" class="alert alert-success">
            <button data-dismiss="alert" class="close">×</button>
                ${message}</div>
    </c:if>

    <form id="send_form" action="${ctx}/test/message/send" class="form-horizontal" method="post">

        <div class="control-group">
            <label class="control-label" for="input_phone">电话号码</label>

            <div class="controls">
                <input class="span3" type="text" name="phone" maxlength="11" id="input_phone"/>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label" for="input_password">密码</label>

            <div class="controls">
                <input class="span2" id="input_password" name="password" type="password"/>
            </div>
        </div>

        <div class="form-actions">
            <button type="submit" id="btn_send">发送短信</button>
        </div>
    </form>
</div>
<script type="text/javascript">
    $(function () {
        $("#send_form").validate({
            rules: {phone: {required: true, minlength: 11}, password: "required"},
            message: {phone: {required: "请输入手机号码！", minlength: "手机号码需要11位！"}, password: "请输入密码！"},
            submitHandler: function (form) {
               form.submit();
            }});
    });
</script>
</body>
</html>