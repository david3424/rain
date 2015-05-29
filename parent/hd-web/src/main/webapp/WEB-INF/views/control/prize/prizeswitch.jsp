<%--
  Created by IntelliJ IDEA.
  User: czw
  Date: 14-1-15
  Time: 上午11:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title></title>
    <script type="text/javascript" src="${ctx}/static/jquery/jquery-1.8.2.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/jquery/plugin/jquery.tmpl.js"></script>
    <script type="text/javascript" src="${ctx}/static/jquery/plugin/jquery.pagination.js"></script>
    <script type="text/javascript" src="${ctx}/static/jquery/event/ajaxpage-jqplugin.js"></script>
</head>
<body>
<div>
    <table>
        <tr>
            <td>
                <button id="open_all">开启所有</button>
            </td>
            <td>
                <button id="close_all">关闭所有</button>
            </td>
        </tr>
    </table>
</div>

<div>
    <table>
        <tr>
            <td>表名：</td>
            <td><input name="prizeTable" id="prizeTable"></td>
            <td>
                <button id="btn_search" type="button">查询</button>
            </td>
        </tr>
    </table>
</div>

<div>
    <div id="data"></div>
    <div id="page"></div>
</div>

<script type="text/x-jquery-tmpl" id="tmpl">
    <table>
        <thead>
        <tr>
            <th>奖品表名称</th>
             <th>创建时间</th>
             <th>当前状态</th>
             <th>操作</th>
        </tr>
        </thead>
        <tbody>
        {{each(i,obj) records}}
        <tr>
            <td>{{= obj.table_name}}</td>
            <td>{{= obj.create_time}}</td>
            <td>{{if obj.status == 0}}停止中{{/if}}{{if obj.status == 1}}开启中{{/if}}</td>
            <td>{{if obj.status == 0}}<button open ="{{= obj.table_name}}">开启</button>{{/if}}
            {{if obj.status == 1}}<button close ="{{= obj.table_name}}">关闭</button>{{/if}}
            </td>
        </tr>
        {{/each}}
        </tbody>
    </table>
</script>

<script type="text/javascript">
    var paginationOptions = {
        url: "/control/prize/job/list",
        pageSize: 10,
        current_page: 0,
        data_area_id: "data",
        tmpl_script: "tmpl",
        page_area_id: "page",
        num_display_entries: 6,
        num_edge_entries: 0,
        prev_text: "上一页",
        next_text: "下一页",
        link_to: "javascript:void(0);",
        ellipse_text: "",
        afterLoad: function () {
            initPageClick();
        }
    };
    $.hd.pagination(paginationOptions);


    $("#btn_search").click(function () {
        paginationOptions.params = {prizeTable: $("#prizeTable").val()};
        $.hd.pagination(paginationOptions);
    });

    function initPageClick() {
        $("[open]").click(function () {
            var table_name = this.getAttribute("open");
            $.post("/control/prize/job/openOne", {prizeTable: table_name}, function (data) {
                alert(data.message);
                location.reload();
            }, "json");
        });

        $("[close]").click(function () {
            var table_name = this.getAttribute("close");
            $.post("/control/prize/job/closeOne", {prizeTable: table_name }, function (data) {
                alert(data.message);
                location.reload();
            }, "json");
        });

    }

    $("#open_all").click(function () {
        $.post("/control/prize/job/openAll", {}, function (data) {
            alert(data.message);
            location.reload();
        }, "json");
    });

    $("#close_all").click(function () {
        $.post("/control/prize/job/closeAll", {}, function (data) {
            alert(data.message);
            location.reload();
        }, "json");
    });
</script>
</body>
</html>
