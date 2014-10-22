<%--
  Created by IntelliJ IDEA.
  User: czw
  Date: 13-7-24
  Time: 下午3:03
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
<div class="hero-unit">
    <h1>发奖总开关</h1>

    <p>status 0 为关闭，不支持单个开关的关闭，单个开关请控制定时器。</p>

</div>
<div class="container">
    <div>
        <c:if test="${status == 1}">
            <dl>
                <dd class="text-success">现在活动发奖总闸已开，点击关闭所有活动发奖</dd>
                <dt>
                    <button class="btn btn-warning" id="btn_close">关闭</button>
                </dt>
            </dl>
        </c:if>
        <c:if test="${status == 0}">
            <dl>
                <dd class="text-warning">现在活动发奖总闸关闭中，点击打开所有活动发奖</dd>
                <dt>
                    <button class="btn btn-success" id="btn_open">打开</button>
                </dt>
            </dl>
        </c:if>
    </div>
</div>
<script type="text/javascript">
    $(function () {
        $("#btn_close").click(function () {
            $.post("${ctx}/task/closeMainSwitch", {}, function (json) {
                if (json.success) {
                    alert("所有活动关闭成功");
                    location.reload();
                }
            }, "json");
        });
        $("#btn_open").click(function () {
            $.post("${ctx}/task/openMainSwitch", {}, function (json) {
                if (json.success) {
                    alert("所有活动打开成功");
                    location.reload();
                }
            }, "json");
        });
    });
</script>
</body>
</html>