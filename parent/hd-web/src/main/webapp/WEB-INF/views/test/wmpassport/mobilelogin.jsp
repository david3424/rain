<%@ page import="com.david.web.wanmei.util.hdswitch.HdSwitchUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 13-9-17
  Time: 下午3:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

</head>
<body>
<div class="log">
    <c:choose>
        <c:when test="${empty sessionScope.USER.account}">
            <a hidefocus="true" class="login" href="javascript:void(0)" id="login" width="100%" height="20%">登录</a>
        </c:when>
        <c:otherwise>
            <p>欢迎您，${sessionScope.USER.account} ，账号id：${sessionScope.USER.userId}，173id：${sessionScope.USER.userid173}！<a hidefocus="true" class="logout" href="javascript:void(0)"
                                                   id="logout">登出</a></p>
        </c:otherwise>
    </c:choose>
    <div>


    </div>
</div>
</body>
<script type="text/javascript" src="${ctx}/static/jquery/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/event/jquery.boxy.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/plugin/jquery.validate.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/plugin/jquery.placeholder.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/plugin/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/extend/wmpassport2mobile/mobile.wanmei.passport.js"></script>
<script type="text/javascript">
    var username = "${sessionScope.USER.account}";
    function login() {
        mobile.wanmei.passport.islogin({session: 'USER', _false: function () {
            mobile.wanmei.passport.login({session: 'USER', hdid: "world2_login_status"});
        }});
    }
    function logout() {
        mobile.wanmei.passport.logout({session: 'USER'});

    }

    $(function () {
        bindLoginLogout();
    })
    //绑定抽奖按钮
    function bindLoginLogout() {
        $("#login").click(function () {
            login();
        });
        $("#logout").click(function () {
            logout();
        });
    }
</script>
</html>