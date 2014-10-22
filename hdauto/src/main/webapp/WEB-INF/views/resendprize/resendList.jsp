<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<body>

<div class="container">
     <div class="hero-unit">
        <h1>补发数据列表管理</h1>

        <p>定时器可自定义添加，不用的定时器请暂停或者直接删除。corn表达式请移步到具体<abbr title="corn表达式说明">说明页</abbr>。</p>

        <p><a class="btn btn-primary btn-inverse">查看更多 &raquo;</a></p>
    </div>
    <c:if test="${not empty message}">
        <div id="message" class="alert alert-success">
            <button data-dismiss="alert" class="close">×</button>
                ${message}</div>
    </c:if>
    <div class="pagination-right">
        <tags:pagination page="${list}" paginationSize="5"/>
    </div>
    <%--<div class="row"><label>选择库：</label> <select name="tablename" id="tablename">
        <option value="kefu_sendprize_temp">通道0-默认（kefu_sendprize_temp）</option>
        <option value="kefu_sendprize_temp_1">通道1（kefu_sendprize_temp_1）</option>
        <option value="kefu_sendprize_temp_2">通道2（kefu_sendprize_temp_2）</option>
        <option value="kefu_sendprize_temp_3">通道3（kefu_sendprize_temp_3）</option>
        <option value="kefu_sendprize_temp_4">通道4（kefu_sendprize_temp_4）</option>
        <option value="kefu_sendprize_temp_5">通道5（kefu_sendprize_temp_5）</option>
    </select>
    </div>--%>
        <span class="">当前库：<c:if test="${not empty param.tablename}">${param.tablename}</c:if></span>
    <div class="dropdown">
        <a class="dropdown-toggle" data-toggle="dropdown" href="#">选择库:</a>
        <b class="caret"></b>
        <ul class="dropdown-menu" role="menu" aria-labelledby="dLabel">
            <li><a tabindex="-1" href="${ctx}/resend?tablename=kefu_sendprize_temp">通道0-默认</a></li>
            <li><a tabindex="-1" href="${ctx}/resend?tablename=kefu_sendprize_temp_1">通道1</a></li>
            <li><a tabindex="-1" href="${ctx}/resend?tablename=kefu_sendprize_temp_2">通道2</a></li>
            <li><a tabindex="-1" href="${ctx}/resend?tablename=kefu_sendprize_temp_3">通道3</a></li>
            <li><a tabindex="-1" href="${ctx}/resend?tablename=kefu_sendprize_temp_4">通道4</a></li>
        </ul>
    </div>
    <div class="row">
        <div class="offset6 span6">
            <form class="form-search" action="#">
                <input type="hidden" name="tablename" value="${param.tablename}">
                <label>账号：</label> <input type="text" name="search_username" class="input-medium"
                                          value="${param.username}">
                <label>状态：</label>
                <select name="search_status">
                    <option value=""></option>
                    <option value="0">待发送</option>
                    <option value="1">发送成功</option>
                    <option value="3">发送失败</option>
                    <option value="5">发送重复</option>
                    <option value="4">申请失败</option>
                    <option value="8">申请成功</option>
                    <option value="2">正在发送</option>
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
            <th>状态</th>
            <th>失败状态</th>
            <%--<th>类型</th>--%>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="map" items="${list}" varStatus="ttt">
            <tr>
                <td>${map.username}</td>
                <td>${map.roleid}</td>
                <td>${map.server}</td>
                <td>${map.prize}</td>
                <td>${map.status}</td>
                <td><tags:result result="${map.rid}" flag="1"/></td>
            </tr>
        </c:forEach>
        </tbody>
        <%--<caption style="color:blue;">补发数据表</caption>--%>
    </table>
    <div class="pagination-right">
        <tags:pagination page="${list}" paginationSize="5"/>
    </div>


</div>


<script type="text/javascript">
    $(function() {
        $('.dropdown-toggle').dropdown();
    });
</script>
</body>
</html>