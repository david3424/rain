<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <shiro:user>
        <meta http-equiv="refresh" content="0; url=<c:url value='${ctx}/user/index'/> "/>
    </shiro:user>
    <title>登录页</title>
    <%@include file="default.jsp" %>
    <style type="text/css">
        {
            margin: 0
        ;
            padding: 0
        ;
        }
        body {
            background: #444 url(http://sandbox.runjs.cn/uploads/rs/418/nkls38xx/carbon_fibre_big.png)
        }

        .loginBox {
            width: 430px;
            height: 230px;
            padding: 0 20px;
            border: 1px solid #fff;
            color: #000;
            border-radius: 8px;
            background: white;
            box-shadow: 0 0 15px #222;
            background: -moz-linear-gradient(top, #fff, #efefef 8%);
            background: -webkit-gradient(linear, 0 0, 0 100%, from(#f6f6f6), to(#f4f4f4));
            font: 11px/1.5em 'Microsoft YaHei';
            position: absolute;
            left: 50%;
            top: 50%;
            margin-left: -210px;
            margin-top: -115px;
        }

        .loginBox h2 {
            height: 45px;
            font-size: 20px;
            font-weight: normal;
        }

        .loginBox .left {
            border-right: 1px solid #ccc;
            height: 100%;
            padding-right: 60px;
        }

    </style>
</head>
<body>
<%--<form id="loginForm" action="${ctx}/login" method="post" class="form-horizontal">
<%
String error = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
if(error != null){
%>
    <div class="alert alert-error input-medium controls">
        <button class="close" data-dismiss="alert">×</button>登录失败，请重试.
    </div>
<%
}
%>
    <div class="control-group">
        <label for="username" class="control-label">名称:</label>
        <div class="controls">
            <input type="text" id="username" name="username"  value="${username}" class="input-medium required"/>
        </div>
    </div>
    <div class="control-group">
        <label for="password" class="control-label">密码:</label>
        <div class="controls">
            <input type="password" id="password" name="password" class="input-medium required"/>
        </div>
    </div>

    <div class="control-group">
        <div class="controls">
            <label class="checkbox" for="rememberMe"><input type="checkbox" id="rememberMe" name="rememberMe"/> 记住我</label>
            <input id="submit_btn" class="btn btn-primary" type="submit" value="登录"/> <a class="btn" href="${ctx}/register">注册</a>
             <span class="help-block">(管理员: <b>admin/admin</b>, 普通用户: <b>user/user</b>)</span>
        </div>
    </div>
</form>

<script>
    $(document).ready(function() {
        $("#loginForm").validate();
    });
</script>--%>
<div class="container">
    <form id="loginForm" action="${ctx}/login" method="post" class="form-horizontal">
        <section class="loginBox row-fluid">
            <section class="span7 left">
                <h2>用户登录</h2>

                <p>
                    <input type="text" id="username" name="username" value="${username}" class="input-medium required"/>
                </p>

                <p>
                    <input type="password" id="password" name="password" class="input-medium required"
                           style="padding-top:0"/></p>

                <p>
                    <input type="text" name="captcha" class="input-small required"/>
                    <img id="rand_img" style="height: 20px;width:54px;" src="${ctx}/images/kaptcha.jpg"
                         onclick="$('#rand_img').attr('src', '${ctx}/images/kaptcha.jpg?r=' + Math.random());return fals0e;"/>
                </p>
                <section class="row-fluid">
                    <section class="span8 lh30"><label class="checkbox" for="rememberMe"><input type="checkbox"
                                                                                                id="rememberMe"
                                                                                                name="rememberMe"/> 记住我</label>
                    </section>
                    <section class="span1"><input id="submit_btn" class="btn btn-primary" type="submit" value="登录"/>
                    </section>
                </section>
            </section>
            <section class="span5 right">
                <h2>没有帐户？</h2>
                <section>
                    <p>请联系 david</p>
                    <%
                        String error = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
                        if (error != null) {
                            String expMsg;

                            if (error.contains("UnknownAccountException") ||
                                    error.contains("IncorrectCredentialsException")) {
                                expMsg = "错误的用户账号或密码！";
                            } else if (error.contains("IncorrectCaptchaException")) {
                                expMsg = "验证码错误！";
                            } else {
                                expMsg = "登录异常 :" + error;
                            }
                            out.print("<span style=\"color: #ff0000\"><h4>" + expMsg + "</h4></span>");
                        }
                    %>
                </section>
            </section>
        </section>
    </form>
    <!-- /loginBox -->
</div>
<!-- /container -->
<script>
    $(document).ready(function () {
        $("#loginForm").validate();
    });
</script>
</body>
</html>
