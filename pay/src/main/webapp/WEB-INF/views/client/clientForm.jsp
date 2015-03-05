<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%--<jsp:include page="../../layouts/default.jsp"/>--%>
<jsp:include page="/WEB-INF/layouts/default.jsp"/>
<html>
<head>
    <title>添加客户端</title>

    <script>
        $(document).ready(function () {
            //聚焦第一个输入框
            $("#client_name").focus();
            //为inputForm注册validate函数
            $("#inputForm").validate();
        });

    </script>
</head>

<body>
<form id="inputForm" action="${ctx}/client/${action}" method="post" class="form-horizontal">
    <input type="hidden" name="id" value="${client.id}"/>
    <fieldset>
        <legend>
            <small>添加客户端</small>
        </legend>
        <div class="control-group">
            <label for="client_name" class="control-label">客户端名称:</label>

            <div class="controls">
                <input type="text" id="client_name" name="name" value="${client.name}" class="input-large required"
                       minlength="3"/>
            </div>
        </div>
        <div class="control-group">
            <label for="client_ip" class="control-label">客户端ip:</label>

            <div class="controls">
                <input type="text" id="client_ip" name="ip" value="${client.ip}" class="input-large required"
                       minlength="3"/>
            </div>
        </div>
        <div class="control-group">
            <label for="notes" class="control-label">描述:</label>

            <div class="controls">
                <textarea id="notes" name="notes" class="input-large">${client.notes}</textarea>
            </div>
        </div>
        <c:if test="${client.id!=null}">
        <div class="control-group">
            <label for="privatekey" class="control-label">私钥:</label>
            <div class="controls">
                <span id="privatekey">${client.privatekey}</span>
            </div>
        </div>
        </c:if>
        <div class="form-actions">
            <input id="submit_btn" class="btn btn-primary" type="submit" value="提交"/>&nbsp;
            <input id="cancel_btn" class="btn" type="button" value="返回" onclick="history.back()"/>
        </div>
    </fieldset>
</form>
</body>
</html>
