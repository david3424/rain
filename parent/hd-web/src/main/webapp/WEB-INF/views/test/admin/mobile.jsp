<%--
  Created by IntelliJ IDEA.
  User: gameuser
  Date: 14-3-26
  Time: 上午10:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>jquery mobile</title>
    <link rel="stylesheet"	href="${ctx}/styles/jquery.mobile-1.1.0.min.css">
    <script type="text/javascript" src="${ctx}/static/jquery/jquery-1.8.2.min.js"></script>
    <script	src="${ctx}/static/jquery/jquery.mobile-1.1.0.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/jquery/plugin/jquery.tmpl.js"></script>
    <script type="text/javascript" src="${ctx}/static/jquery/plugin/jquery.pagination.js"></script>
    <script type="text/javascript" src="${ctx}/static/jquery/event/ajaxpage-jqplugin.js"></script>
</head>
<body>
<div data-role="page" id="index" >
    <div data-theme="a" data-role="header" data-id="foo12" data-position="fixed">
        <h1>event活动开关</h1>
    </div>
    <div data-role="content">
        <div id="data"></div>
        <div id="page"></div>
    </div>
    <div data-role="footer">
        <h2>活动页脚</h2>
    </div>
</div>

<script type="text/x-jquery-tmpl" id="tmpl">
    <ul data-role="listview">
        {{each(i,obj) records}}
        <li data-role="list-divider"><a href="#">{{= obj.hdname}}</a>
        <li>表名：{{= obj.tbname}}</li>
        <li>创建时间：{{= obj.createtime}}</li>
        <li>
            当前状态：{{if obj.open == 0}}关闭{{else}}<span style="color:green">开启中</span>{{/if}}
        </li>
        <li>
            <select name="flipswitch" data-role="slider">{{if obj.open == 0}}<option value="off">关</option><option value="on">开</option>
                {{else}}<option value="on">开</option><option value="off">关</option>
                {{/if}}</select>
        </li>
        {{/each}}
    </ul>
</script>

<script type="text/javascript">
    var ctx = "${ctx}";
    alert(ctx+"=========ctx");
    var paginationOptions = {
        url: "http://event.wanmei.com/test/page/switchlist",
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
        paginationOptions.params = {tbname: $("#tbname").val()};
        $.hd.pagination(paginationOptions);
    });

    function initPageClick() {
        $("[open]").click(function () {
            if(confirm("确定要执行该操作吗？")){
                var tbname = this.getAttribute("open");
                $.post("http://event.wanmei.com/test/switch/openOne", {tbname: tbname}, function (data) {
                    alert(data.message);
                    location.reload();
                }, "json");
            }

        });

        $("[close]").click(function () {
            if(confirm("确定要执行该操作吗？")){
                var tbname = this.getAttribute("close");
                $.post("http://event.wanmei.com/test/switch/closeOne", {tbname: tbname }, function (data) {
                    alert(data.message);
                    location.reload();
                }, "json");
            }
        });
    }

    $("#on_close").click(function () {
        if(confirm("确定要执行该操作吗？")){
            $.post("http://event.wanmei.com/test/switch/onclose", {}, function (data) {
                alert(data.message);
                location.reload();
            }, "json");
        }
    });

    $("#on_open").click(function () {
        if(confirm("确定要执行该操作吗？")){
            $.post("http://event.wanmei.com/test/switch/onopen", {}, function (data) {
                alert(data.message);
                location.reload();
            }, "json");
        }
    });
</script>
</body>
</html>