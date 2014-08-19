<%--
  Created by IntelliJ IDEA.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>

<h3>ehcache</h3>
<div id="prizecount"></div>
<!--数据显示开始-->
<div id="data"></div>
<!--数据显示结束-->
<div>
    <!--页码显示开始-->
    <div id="page"></div>
    <!--页码显示结束-->
</div>

<hr>
<h3>no cache</h3>

<div id="prizecount2"></div>
<!--数据显示开始-->
<div id="data2"></div>
<!--数据显示结束-->
<div>
    <!--页码显示开始-->
    <div id="page2"></div>
    <!--页码显示结束-->
</div>

<script type="text/javascript" src="/static/jquery/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="/static/jquery/plugin/jquery.tmpl.js"></script>
<script type="text/javascript" src="/static/jquery/plugin/jquery.pagination.js"></script>
<script type="text/javascript" src="/static/jquery/event/ajaxpage-jqplugin.js"></script>
<!--分页数据html模板开始-->
<script type="text/x-jquery-tmpl" id="tmpl">
    <ul>
        {{each(i,obj) records}}
        <li>
            <p>ID：{{= obj.id}}</p>

            <p>标题：{{= obj.title}}</p>

            <p>描述：{{= obj.description}}</p>
        </li>
        {{/each}}
    </ul>

</script>
<script type="text/javascript">
    var paginationOptions = {
        url: "/example/page/json",
        pageSize: 5,
        current_page: 0,
        data_area_id: "data",
        tmpl_script: "tmpl",
        page_area_id: "page",
        num_display_entries: 10,
        num_edge_entries: 0,
        prev_text: "上一页",
        next_text: "下一页",
        link_to: "javascript:void(0);",
        ellipse_text: ""
    };
    var paginationOptions2 = {
        url: "/example/page/json2",
        pageSize: 5,
        current_page: 0,
        data_area_id: "data2",
        tmpl_script: "tmpl",
        page_area_id: "page2",
        num_display_entries: 10,
        num_edge_entries: 0,
        prev_text: "上一页",
        next_text: "下一页",
        link_to: "javascript:void(0);",
        ellipse_text: ""
    };
    $(function () {
        $.hd.pagination(paginationOptions);
        $.hd.pagination(paginationOptions2);
    });
</script>
</body>
</html>