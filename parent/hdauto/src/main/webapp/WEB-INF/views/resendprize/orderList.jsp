<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>补发订单列表</title>
</head>
<body>

<div class="container">
    <div class="hero-unit">
        <h1>补发订单列表</h1>

        <p>定时器可自定义添加，不用的定时器请暂停或者直接删除。corn表达式请移步到具体<abbr title="corn表达式说明">说明页</abbr>。</p>

        <p><a class="btn btn-primary btn-inverse">查看更多 &raquo;</a></p>
    </div>
    <c:if test="${not empty message}">
        <div id="message" class="alert alert-success">
            <button data-dismiss="alert" class="close">×</button>
                ${message}</div>
    </c:if>

    <a href="${ctx}/resend/add" class="btn btn-large btn-primary ">添加新补发任务</a>

    <div class="pagination-right">
        <tags:pagination page="${list}" paginationSize="5"/>
    </div>
    <div class="row">
        <div class="offset6 span6">
            <form class="form-search" action="#">
                <%--<label>账号：</label> <input type="text" name="search_username" class="input-medium"
                                          value="${param.username}">
                <label>状态：</label>
                <select name="search_status">
                    <option value=""></option>
                    <option value="0">待发送</option>
                    <option value="1">发送成功</option>
                    <option value="3">发送失败</option>
                </select>
                <button type="submit" class="btn">搜索</button>--%>
            </form>
        </div>

    </div>
    <table class="table table-bordered table-hover">
        <thead>
        <tr>
            <th>名称</th>
            <th>所属游戏</th>
            <th>描述</th>
            <%--<th>发奖方式</th>--%>
            <th>数量</th>
            <th>创建时间</th>
            <th>备份表</th>
            <th>操作内容</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="map" items="${list}" varStatus="ttt">
            <tr>
                <td>${map.actname}</td>
                <td>${map.gamename}</td>
                <td>${map.description}</td>
                    <%--<td>${map.ordertype}</td>--%>
                <td>${map.totalnum}</td>
                <td title="${map.createtime}">${fn:length(map.createtime)>10?fn:substring(map.createtime,0 ,10 ):map.createtime}</td>
                <td>
                    <c:choose>
                        <c:when test="${map.status==2}">
                          <a class="btn-block" href="${ctx}/resend/?tablename=${map.tablename}">${map.tablename}</a>
                        </c:when>
                        <c:otherwise>
                             ${map.baktablename}
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${map.status==1}">
                            <a class="btn btn-danger" url="${ctx}/resend/del?orderid=${map.id}"
                               del="del"
                               href="javascript:void(0);">删除</a>
                            <a class="btn btn-block" href="${ctx}/resend/upload?orderid=${map.id}">导入</a>
                        </c:when>
                        <c:when test="${map.status==2}">
                            <a target="_blank" class="btn btn-info"
                               href="${ctx}/task?search_trigger_name=${map.tablename}">进入</a>
                            <a class="btn btn-success" url="${ctx}/resend/complete?orderid=${map.id}"
                               id="complete${map.id}"
                               href="javascript:void(0);">完成</a>
                            <a class="btn btn-danger" url="${ctx}/resend/del?orderid=${map.id}"
                               del="del"
                               href="javascript:void(0);">删除</a>
                            <%--<a class="btn btn-warning" href="${ctx}/resend/upload?orderid=${map.id}">继续导入</a>--%>
                        </c:when>
                        <c:otherwise>
                            已完成发奖
                        </c:otherwise>
                    </c:choose>

                </td>
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
        $('[del]').click(function() {
            if (!confirm("确定删除？")) {
                return;
            }
            var button = $(this);
            $.get(button.attr('url'), {}, function(result) {
                window._originalAlert(result > 0 ? "删除成功" : "删除失败");//高级删除不能阻塞刷新
                location.reload();
            });
        });

        $('a[id^="complete"]').click(function() {
            var button = $(this);
            $.get(button.attr('url'), {}, function(result) {
                var msg;
                switch (result) {
                    case -1:msg = "orderid有误";break;
                    case -2:msg = "有0/2值，不能完成";break;
                    case 0:msg = "操作成功";location.reload();break;
                }
                alert(msg);
            });
        });
    });
</script>
</body>
</html>