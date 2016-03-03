<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
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
            margin-top: 40px;
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
            <%
	String error = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
	if(error != null){
	%>
        <%--<div class="alert alert-error input-medium controls">--%>
        <%--<button class="close" data-dismiss="alert">×</button>--%>
        <%--登录失败，请重试.--%>
        <%--</div>--%>
            <%
                    }
                %>

        <section class="loginBox row-fluid">
            <section class="span7 left">
                <h2>用户登录</h2>

                <p>
                    <%--<label for="username" class="control-label">名称:</label>--%>
                    <input type="text" id="username" name="username" value="${username}" class="input-medium required"/>
                </p>

                <p>
                    <%--<label for="password" class="control-label">密码:</label>--%>
                    <input type="password" id="password" name="password" class="input-medium required"/></p>
                <section class="row-fluid">
                    <section class="span8 lh30"><label class="checkbox" for="rememberMe"><input type="checkbox"
                                                                                                id="rememberMe"
                                                                                                name="rememberMe"/> 记住我</label>
                        <%-- <span class="help-block">(管理员: <b>admin/admin</b>, 普通用户: <b>user/user</b>)</span>--%>
                    </section>
                    <section class="span1"><input id="submit_btn" class="btn btn-primary" type="submit" value="登录"/>
                    </section>
                </section>
            </section>
            <section class="span5 right">
                <h2>没有帐户？</h2>
                <section>
                    <p>请联系 张继</p>

                    <%
                        if(error != null){
                    %>
                    <br/>
                    <span style="color: #ff0000"><h4>登录失败，请重试。</h4></span>
                    <%
                        }
                    %>
                    <%--<p><a class="btn" href="${ctx}/register">注册</a></p>--%>
                </section>
            </section>
        </section>
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
