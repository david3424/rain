
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


<script type="text/javascript" src="/static/jquery/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="/static/jquery/plugin/jquery.tmpl.js"></script>
<script type="text/javascript" src="/static/jquery/plugin/jquery.pagination.js"></script>
<script type="text/javascript" src="/static/jquery/event/ajaxpage-jqplugin.js"></script>
<!--分页数据html模板开始-->
<script type="text/x-jquery-tmpl" id="tmpl">
    <table cellpadding="0" cellspacing="0" align="center" class="table_list" style="width:100%" border="1">
        <tr align="center" style="background-color:antiquewhite;height:30">
            <td>奖品名称</td>
            <td>消耗积分</td>
            <td>角色名称</td>
            <td>服务器</td>
            <td>发送时间</td>
            <td>奖品状态</td>
        </tr>
        {{each(i,obj) records}}
        <tr>
            <td>{{= obj.prizeName}}</td>
            <td>{{= obj.score}}</td>
            <td>{{= obj.roleName}}</td>
            <td>{{= obj.serverName}}</td>
            <td>{{= obj.sendTime}}</td>
            <td>{{if obj.status =='9'}}未发送{{/if}}
                {{if obj.status =='0'}}等待发送{{/if}}
                {{if obj.status =='2'}}发送中{{/if}}
                {{if obj.status =='3'}}发送失败{{/if}}
                {{if obj.status =='4'}}发送失败，稍后为您补发{{/if}}
            </td>
        </tr>
        {{/each}}
    </table>

</script>
<script type="text/javascript">
    var paginationOptions = {
        url: "/test/rechargetest/list",
        pageSize: 5,
        current_page: 0,
        params:{key:new Date().getTime(),type:${type}},
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

    $(function () {
        $.hd.pagination(paginationOptions);
    });
</script>
</body>
</html>