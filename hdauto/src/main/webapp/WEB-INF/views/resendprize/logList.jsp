<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>日志查看</title>
</head>
<body>

<div class="container">
     <div class="hero-unit">
        <h3>表名：<c:if test="${not empty param.tablename}">${param.tablename}</c:if></h3>
        <p id="huodong_report"></p>
    </div>
    <c:if test="${not empty message}">
        <div id="message" class="alert alert-success">
            <button data-dismiss="alert" class="close">×</button>
                ${message}</div>
    </c:if>
    <div class="pagination-right">
        <tags:pagination page="${list}" paginationSize="5"/>
    </div>
    <div class="row">
        <div class="span9">
            <form class="form-search" action="#">
                <input type="hidden" name="datasource" value="${param.datasource}"/>
                <label>表名：</label><input type="text" name="tablename" value="${param.tablename}">
                <label>账号：</label> <input type="text" name="search_username" class="input-medium"
                                          value="${param.username}">
                <label>状态：</label>
                <select name="status">
                    <option value=""></option>
                    <option value="0">申请成功</option>
                    <option value="1">申请失败</option>
                    <option value="2">发送成功</option>
                    <option value="3">发送失败</option>
                </select>
                <button type="submit" class="btn">搜索</button>
            </form>
        </div>

        <%--排序取不到库名，有问题--%>
        <%--<tags:sort/>--%>
    </div>
    <table class="table table-bordered table-hover">
        <thead>
        <tr>
            <th>账号</th>
            <th>角色ID</th>
            <th>服务器</th>
            <th>奖品ID</th>
            <th>发送时间</th>
            <th>奖品表ID</th>
            <th>GID</th>
            <th>结果</th>
            <th>状态（0申请1发送）</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="map" items="${list}" varStatus="ttt">
            <tr>
                <td>${map.username}</td>
                <td>${map.roleid}</td>
                <td>${map.server}</td>
                <td>${map.prize}</td>
                <td>${map.createdate}</td>
                <td>${map.pid}</td>
                <td>${map.gid}</td>
                <td><tags:result result="${map.status}" flag="${map.flag}"/></td>
                <td>${map.flag}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="pagination-right">
        <tags:pagination page="${list}" paginationSize="5"/>
    </div>


</div>


<script type="text/javascript">
    $(function() {
       $('[name="status"]').val('${param.status}');
        $('.dropdown-toggle').dropdown();

        // 显示发送奖品情况报告
            $.getJSON('${ctx}/resend/report',
            {tablename:'${param.tablename}',datasource:'${param.datasource}'}, function(json) {
                var str = "";
                for (var i = 0; i < json.length; i++) {
                    var status = json[i].status;
                    var count = json[i].a;
                    switch (status) {
                        case 0:
                            str += "等待发送的数量(" + status + "):" + count; break;
                        case 1:
                            str += "发送成功的数量(" + status + "):" + count; break;
                        case 2:
                            str += "发送中的数量(" + status + "):" + count; break;
                        case 3:
                            str += "发送失败的数量(" + status + "):" + count; break;
                        case 4:
                            str += "申请失败的数量(" + status + "):" + count; break;
                        case 5:
                            str += "可能重复发奖的数量(" + status + "):" + count; break;
                        case 8:
                            str += "申请成功结果未知的数量(" + status + "):" + count; break;
                    }
                    str += "<br><br>";
                }
                if (json.length == 0)
                    str = "表中暂无发奖数据。";
                $('#huodong_report').html(str);
            });

        });

</script>
</body>
</html>