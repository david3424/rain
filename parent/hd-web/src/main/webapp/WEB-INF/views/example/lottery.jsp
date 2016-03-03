<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>样例页面</title>
</head>
<body>
<h3 class="page-header">特此申明，由于你意外看到了本页面，不要惊喜，这只是一个样例，不是你真中奖了，额，还是祝你好运</h3>

<div class="container">
    <button id="lottery" type="button" class="btn btn-success">假抽奖(概率)</button>
</div>

<div class="container">
    <button id="destine" type="button" class="btn btn-success">假抽奖(排奖)</button>
</div>

<script type="text/javascript" src="/static/jquery/jquery-1.8.2.min.js"></script>
<script type="text/javascript">
    $("#lottery").click(function () {
        var _url = "/example/lottery/page/lottery/probability";
        $.post(_url, {}, function (json) {
            alert(json.message);
        }, "json");
    });

    $("#destine").click(function () {
        var _url = "/example/lottery/page/lottery/destine";
        $.post(_url, {}, function (json) {
            alert(json.message);
        }, "json");
    });
</script>
</body>
</html>
