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
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <style type="text/css">
        .page{ text-align:center; color:#ff0000; padding:10px 0; }
        .page a,.page span{ display:inline-block; padding:4px 6px; margin:0 1px; text-align:center; color:#fff; background-color:#aa1f1f; }
        .page a.current,.page span.current{ color:#ffe244; font-weight:bold; background-color:#9c1201; }

        .pop_bg{display:none; }
        .close{ width:48px; height:43px; background:url(http://event51.wanmei.com/zhuxian/201401/cjevent/images/close.jpg) no-repeat;
            text-indent:-7700px; position:absolute; right:-43px; top:0; }
        .pop_bgxx{ border-collapse:collapse; border-spacing:0; width:240px; margin:0 auto; }
        .pop_bgxx td{ color:#fff; background:#aa1f1f; padding:5px 0; }
        .pop_bgxx td input{ width:200px; height:33px; border:1px solid #a5acb2; background:#fff; line-height:10px; color:#000; }
    </style>
    <link rel="stylesheet"	href="${ctx}/styles/jquery.mobile-1.1.0.min.css">
    <link href="${ctx}/static/styles/boxy.css" type="text/css" rel="stylesheet">
    <script type="text/javascript" src="${ctx}/static/jquery/jquery-1.8.2.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/jquery/event/jquery.boxy.js"></script>
    <script type="text/javascript" src="${ctx}/static/jquery/plugin/jquery.tmpl.js"></script>
    <script type="text/javascript" src="${ctx}/static/jquery/plugin/jquery.pagination.js"></script>
    <script type="text/javascript" src="${ctx}/static/jquery/event/ajaxpage-jqplugin.js"></script>
    <script	src="${ctx}/static/jquery/jquery.mobile-1.1.0.min.js"></script>
</head>
<body>

<div data-role="page" id="index" data-theme="b">
    <div data-theme="a" data-role="header" data-id="foo12" data-position="fixed">
        <h1>event开关控制</h1>
    </div>
    <div data-role="content">
        <table>
            <tr>
                <td>
                    <a href="http://img.event.wanmei.com/control/prize/switch" target="_blank" data-role="button">活动总开关控制</a>
                </td>
            </tr>
            <tr>
                <td>表名：<input name="tbname" id="tbname"></td>
                <td>
                    <button id="btn_search" type="button" style="width: 50px">查询</button>
                </td>
            </tr>
            <tr><td>
            <c:choose><c:when test="${updateStatus==true}">
                <a href="javascript:void(0)" id="on_open" data-role="button" data-inline="true">一键开启在线活动</a>
            </c:when><c:otherwise>
                <a href="javascript:void(0)" id="on_close" data-role="button" data-inline="true">一键关闭在线活动</a>
            </c:otherwise></c:choose>
            </td></tr>

        </table>
        <%--<div data-role="collapsible-set">--%>
        <%--<div data-role="collapsible">--%>
        <%--<h3>点击我 折叠1111</h3>--%>
        <%--<p>折叠内容</p>--%>
        <%--</div>--%>
        <%--<div data-role="collapsible">--%>
        <%--<h3>点击我 折叠2222</h3>--%>
        <%--<p>折叠内容</p>--%>
        <%--</div>--%>
        <%--</div>--%>
        <div id="data" class="ui-grid-b"></div>
        <div id="page" class="page"></div>
        <div class="pop_bg" id="pop_bg">
            <a class="close" href="javascript:;">关闭</a>
            <table class="pop_bgxx">
                <tr>
                    <td align="right">表名</td>
                    <td><input name="tbname" id="show_tbname"></td>
                </tr>
                <tr>
                    <td align="right">Begin</td>
                    <td><input name="begintime" id="begin_time"></td>
                </tr>
                <tr>
                    <td align="right">End</td>
                    <td><input name="begintime" id="end_time"></td>
                </tr>
            </table>
            <div class="page"></div>
        </div>
    </div>
    <div data-theme="a" data-role="footer" data-id="foo1" data-position="fixed">
        <div data-role="navbar">
            <ul>
                <li><a href="#index" data-role="button" data-rel="back">返 回</a></li>
            </ul>
        </div>
    </div>
</div>

<%--<div>--%>
<%--<table>--%>
<%--<tr>--%>
<%--<td>表名：</td>--%>
<%--<td><input name="tbname" id="tbname"></td>--%>
<%--<td>--%>
<%--<button id="btn_search" type="button">查询</button>--%>
<%--</td>--%>
<%--<td></td><td></td>--%>
<%--<td><a href="javascript:void(0)" id="on_close">一键关闭在线活动</a></td>--%>
<%--<td><a href="javascript:void(0)" id="on_open">一键开启在线活动</a></td>--%>
<%--</tr>--%>
<%--</table>--%>
<%--</div>--%>


<%--<div>--%>
<%--<div id="data"></div>--%>
<%--<div id="page"></div>--%>
<%--</div>--%>

<%--<script type="text/x-jquery-tmpl" id="tmpl">--%>
<%--<table>--%>
<%--<thead>--%>
<%--<tr>--%>
<%--<th>活动名字</th>--%>
<%--<th>表名</th>--%>
<%--<th>创建时间</th>--%>
<%--<th>当前状态</th>--%>
<%--<th>操作</th>--%>
<%--</tr>--%>
<%--</thead>--%>
<%--<tbody>--%>
<%--{{each(i,obj) records}}--%>
<%--<tr>--%>
<%--<td>{{= obj.hdname}}</td>--%>
<%--<td>{{= obj.tbname}}</td>--%>
<%--<td>{{= obj.createtime}}</td>--%>
<%--<td>{{if obj.open == 0}}关闭{{else}}<span style="color:green">开启中</span>{{/if}}</td>--%>
<%--<td><select name="flipswitch" id="flipswitch" data-role="slider">--%>
<%--{{if obj.open == 0}} <option value="off">关</option><option value="off">关</option></button>--%>
<%--{{else}}<option value="on">开</option>{{/if}}--%>
<%--</select>--%>
<%--</td>--%>
<%--</tr>--%>
<%--{{/each}}--%>
<%--</tbody>--%>
<%--</table>--%>
<%--</script>--%>

<script type="text/x-jquery-tmpl" id="tmpl">
    <div class="ui-block-a">
        活动名称
    </div>
    <div class="ui-block-b">
        当前状态
    </div>
    <div class="ui-block-c">
        操作
    </div>
    {{each(i,obj) records}}
    <div class="ui-block-a">
        <a href="javascript:void(0)" TBname="{{= obj.tbname}}" createtime="{{= obj.createtime}}"
           begintime="{{= obj.begintime}}" endtime="{{= obj.endtime}}">{{= obj.hdname}}</a>
    </div>
    <div class="ui-block-b">{{if obj.open == 0}}关闭{{else}}<span style="color:blue">开启中</span>{{/if}}</div>
    <div class="ui-block-c">
        {{if obj.open == 0}} <button open ="{{= obj.tbname}}" type="button">开启</button>
        {{else}}<button close ="{{= obj.tbname}}" type="button">关闭</button>{{/if}}
        </select>
    </div>
    {{/each}}
</script>

<script type="text/javascript">
    var paginationOptions = {
        url: "/test/page/switchlist",
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
        $("[TBname]").click(function () {
            var name = $(this).attr("TBname");
            var begin_time = $(this).attr("begintime");
            var end_time = $(this).attr("endtime");
            $('#show_tbname').val(name);
            $('#begin_time').val(begin_time);
            $('#end_time').val(end_time);
            $.simpleDialog({
                skin:false,
                content: '#pop_bg',
                id : 'pop_bg'
            });
        });
        $("[open]").click(function () {
            if(confirm("确定要执行该操作吗？")){
                var tbname = this.getAttribute("open");
                $.post("/test/switch/openOne", {tbname: tbname}, function (data) {
                    alert(data.message);
                    location.reload();
                }, "json");
            }

        });

        $("[close]").click(function () {
            if(confirm("确定要执行该操作吗？")){
                var tbname = this.getAttribute("close");
                $.post("/test/switch/closeOne", {tbname: tbname }, function (data) {
                    alert(data.message);
                    location.reload();
                }, "json");
            }
        });
    }

    $("#on_close").click(function () {
        if(confirm("确定要执行该操作吗？")){
            $.post("/test/switch/onclose", {}, function (data) {
                alert(data.message);
                location.reload();
            }, "json");
        }
    });

    $("#on_open").click(function () {
        if(confirm("确定要执行该操作吗？")){
            $.post("/test/switch/onopen", {}, function (data) {
                alert(data.message);
                location.reload();
            }, "json");
        }
    });

    $('.pop_bg .close').click(function(){
        $.simpleDialog.close('pop_bg');
    });
</script>
</body>
</html>
