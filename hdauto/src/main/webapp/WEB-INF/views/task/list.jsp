<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>调度任务列表</title>
</head>
<body>

<div class="container">
    <div class="hero-unit">
        <h1>定时器列表管理</h1>

        <p>定时器可自定义添加，不用的定时器请暂停或者直接删除。corn表达式请移步到具体<abbr title="corn表达式说明">说明页</abbr>。</p>

        <p><a class="btn btn-primary btn-inverse">查看更多 &raquo;</a></p>
    </div>
    <c:if test="${not empty message}">
        <div id="message" class="alert alert-success">
            <button data-dismiss="alert" class="close">×</button>
                ${message}</div>
    </c:if>
    <div class="row">
        <div class="span4"><a href="${ctx}/task/create" class="btn btn-large btn-primary ">添加新定时器</a></div>
        <div class="span6">
            <form class="form-search" action="#" method="GET">
                <label>名称：</label> <input type="text" name="search_trigger_name" class="input-medium"
                                          value="${param.search_trigger_name}">
                <label>状态：</label>
                <select id="search_trigger_state" name="search_trigger_state">
                    <option value=""></option>
                    <option value="PAUSED">暂停的</option>
                    <option value="ACQUIRED">运行的</option>
                </select>
                <input type="hidden" name="search_is_kefu" value="${param.search_is_kefu}"/>
                <button type="submit" class="btn">搜索</button>
            </form>
        </div>

        <tags:sort/>
    </div>
    <table class="table table-bordered table-hover">
        <thead>
        <tr>
            <th>名称</th>
            <th>分组</th>
            <th>下次执行时间</th>
            <%--<th>上次执行时间</th>--%>
            <%--<th>优先级</th>--%>
            <th>状态</th>
            <%--<th>类型</th>--%>
            <th>创建时间</th>
            <%--<th>结束时间</th>--%>
            <th>动作命令</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="map" items="${list}" varStatus="ttt">
            <tr class="ttt">
                <td>${map.trigger_name}</td>
                <td>
                    <c:set value="${fn:split(map.trigger_group,'|')}" var="groups"/>
                    <c:choose>
                        <c:when test="${fn:length(groups)>1?groups[1]==1:groups[0]==1}">
                            ${groups[0]}|非神魔
                        </c:when>
                        <c:when test="${fn:length(groups)>1?groups[1]==2:groups[0]==2}">
                            ${groups[0]}|神魔
                        </c:when>
                        <c:when test="${groups[1]==3}">
                            ${groups[0]}|团购
                        </c:when>
                        <c:otherwise> ${groups[0]}|新团购</c:otherwise>
                    </c:choose>
                </td>
                <td><tags:util date="${map.next_fire_time}"/></td>
                    <%--<td><tags:util date="${map.prev_fire_time}"/></td>--%>
                    <%--<td>${map.priority}</td>--%>
                <td class="text-info">
                    <c:choose>
                        <c:when test="${map.statu=='PAUSED'}">
                            暂停中
                        </c:when>
                        <c:otherwise>
                            运行中
                        </c:otherwise>
                    </c:choose>

                </td>
                    <%--<td>${map.trigger_type}</td>--%>
                <td><tags:util date="${map.start_time}"/></td>
                    <%--<td>${map.end_time}</td>--%>
                <td>
                    <a href="javascript:void(0);" class="btn btn-warning" id="pause_${ttt.count}"
                       url="${ctx}/task/pause/${map.trigger_name}@${map.trigger_group}@${map.statu}">
                        <c:choose>
                            <c:when test="${map.statu=='PAUSED'}">
                                恢复
                            </c:when>
                            <c:otherwise>
                                暂停
                            </c:otherwise>
                        </c:choose>
                    </a>
                    <a class="btn btn-danger"
                       href="${ctx}/task/delete/${map.trigger_name}@${map.trigger_group}"
                       delete="${map.trigger_name}">删除</a>
                    <a class="btn btn-inverse" href="javascript:void(0);"
                       id="resend_${ttt.count}"
                       url="${ctx}/resend/resendall?tablename=${map.trigger_name}&datasource=${groups[0]}">补发所有</a>
                    <a class="btn btn-inverse" href="javascript:void(0);"
                       id="resend_${ttt.count}"
                       url="${ctx}/resend/resendUnknown?tablename=${map.trigger_name}&datasource=${groups[0]}">补发8</a>
                    <a class="btn btn-info"
                       href="${ctx}/resend/logs?tablename=${map.trigger_name}&datasource=${groups[0]}">查看</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
        <caption style="color:blue;">状态跟踪表</caption>
    </table>
    <div class="pagination-right">
        <tags:pagination page="${list}" paginationSize="10"/>
    </div>

</div>


<script type="text/javascript">
    $(function () {

        $('a[id^="pause"]').click(function () {

            var button = $(this);
            var buttonName = $.trim(button.html());
            var status;
            if (buttonName == '暂停') {
                buttonName = '恢复';
                status = '暂停中';
            } else {
                buttonName = '暂停';
                status = '运行中';
                if(!confirm("确定要开启发奖?"))
                {
                    return false;
                }
            }
            $.get(button.attr('url'), {}, function (result) {
                if (result == "true") {
                    button.html($.trim(buttonName));
                    button.parent().parent().find('.text-info').html(status);
                    var msg = '<div id="message" class="alert alert-success">';
                    $('div.hero-unit').after(msg + "操作成功</div>");
                    $('#message').fadeOut(2000);
                } else {
                    var msg = '<div id="message" class="alert alert-error">';
                    $('div.hero-unit').after(msg + buttonName + "失败</div>");
                    $('#message').fadeOut(4000);
                }
            });
        });

        $('a[id^="resend"]').click(function () {
            var button1 = $(this);
            $.ajax({
                type: "GET",
                url: button1.attr('url'),
                beforeSend: function () {
                    var msg = '<div id="message" class="alert alert-info">';
                    $('div.hero-unit').after(msg + "正在努力，请稍候</div>");
                },
                success: function (result) {

                    if (result == 1) {
//                   var msg = '<div id="message" class="alert alert-success">';
                        $('#message').removeClass("alert-info").addClass("alert-success").html("补发成功");
//                    $('#message').fadeOut(4000);
                    } else {
                        $('#message').removeClass("alert-info").addClass("alert-error").html("补发失败");
//                    $('#message').fadeOut(4000);
                    }
                }
            })
        });


        $("#search_trigger_state>option").each(function () {
            if ($(this).val() == "${param.search_trigger_state}")
                $(this).attr("selected", "selected");
        });


        $("[delete]").click(function () {
            if (confirm("确定要删除【" + $(this).attr("delete") + "】")) {
                return true;
            }
            return false;
        });

        $('.dropdown-toggle').dropdown();

    });

</script>
</body>
</html>