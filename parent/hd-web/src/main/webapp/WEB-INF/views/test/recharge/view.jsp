<%@ page import="com.david.web.world2.sso.authcode.AuthInfo" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head><title>查看已兑换奖品状态</title></head>
<%
    AuthInfo user = (AuthInfo) session.getAttribute("USER");
    if (user == null) {
        response.sendRedirect("/test/rechargetest/index");
    }
%>
<body>
<c:choose>
    <c:when test="${empty sessionScope.USER.account}">
        <input type="button" value="登录"
               onclick="wanmei.passport.login({opacity:60,url:'${ctx}/test/rechargetest/login',session:'USER',hdid:'mhzx_rechargetest_prize'});">
    </c:when>
    <c:otherwise>
        <input type="button" value="退出"
               onclick="wanmei.passport.logout({opacity:60, session:'USER'});">
        <br><br>
        当前总积分：${tbScore} 已使用积分:${usedScore}
        剩余积分：${tbScore-usedScore}
    </c:otherwise>
</c:choose>

查看已兑换奖品状态 <a href="${ctx}/test/rechargetest/index">返回活动首页</a>
<br><br>
<a href="javascript:void(0);" list="1">未发送</a>
<a href="javascript:void(0);" list="2">已发送</a>
<a href="javascript:void(0);" list="0">全部奖品</a>
<br><br>
<hr/>
<iframe name="listiframe" id="listiframe" src="${ctx}/test/rechargetest/toList?type=0" width="100%" height="500"
        frameborder="0"></iframe>
</body>
<script type="text/javascript" src="${ctx}/static/jquery/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/event/jquery.boxy.js"></script>
<script type="text/javascript" src="${ctx}/extend/wmpassport2/wanmei.passport.js"></script>

<script type="text/javascript">
    $(function () {
        _initButton();
    })

    var _initButton = function () {
        $("[list]").click(function () {
            var $obj = $(this);
            var url = "${ctx}/test/rechargetest/toList?type=" + $obj.attr("list");
            $("#listiframe").attr("src", url);
        });
        return false;
    };


</script>
</html>