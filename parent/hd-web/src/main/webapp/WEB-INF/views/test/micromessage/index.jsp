<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>微信公众信息展示</title>
    <script type="text/javascript" src="${ctx}/static/jquery/jquery-1.8.2.min.js"></script>
    <script type="text/javascript" src="${ctx}/extend/wmpassport2/wanmei.passport.js"></script>
    <script type="text/javascript">
        $(function () {
            initLoginLogout();
        });
        function initLoginLogout() {
            $('#login').click(function () {
                login();
            });
            $('#logout').click(function () {
                logout();
            });
        }

        function login() {
            wanmei.passport.islogin({session: 'USER', _true: function () {
            }, _false: function () {
                wanmei.passport.login({session: 'USER', hdid: "micro_test"})
            }});
        }

        function logout() {
            wanmei.passport.logout({opacity: 60, session: 'USER'});
        }
    </script>
</head>
<body>
<h2>你好，欢迎光临寒舍。你的openId是${param.openId}</h2>

<p>
    账号：<input type="text" id="username"><span id="username_error"></span>
</p>

<p>
    密码：<input type="password" id="passport"><span id="passport_error"></span>
</p>

<p><input type="button" id="submit" value="提交"></p>

<div id="msg"></div>
<div><a href="http://www.wanmei.com">完美世界</a></div>
</body>
<script type="text/javascript">
    $(function () {
        var $username = $("#username");
        var $password = $("#password");
        var url = '${ctx}/micro/test/binding';
        $("#submit").click(function () {
            $.getJSON(url, {username: $username.val(), openId: '${param.openId}', r: Math.random()}, function (data) {
                if (data.success) {
                    $username.val("");
                    $password.val("");
                }
                $("#msg").text(data.message);
            });
        });
    });
</script>
</html>