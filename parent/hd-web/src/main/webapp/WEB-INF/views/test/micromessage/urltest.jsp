<%--
  Created by IntelliJ IDEA.
  User: gameuser
  Date: 14-2-20
  Time: 上午9:43
  To change this template use File | Settings | File Templates.
--%>
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
        $(function(){
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
                wanmei.passport.login({session: 'USER',hdid:"micro_test"})
            }});
        }

        function logout() {
            wanmei.passport.logout({opacity: 60, session: 'USER'});
        }
    </script>
</head>
<body>
<h2>你好，欢迎光临寒舍。</h2>
<h3>${rank}</h3>
<a href="javascript:void(0)" id="login">登 录</a>
<div><a href="http://www.wanmei.com">完美世界</a></div>
</body>
</html>