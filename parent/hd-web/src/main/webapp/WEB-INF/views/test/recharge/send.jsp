<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head><title>发送奖品</title>
    <link href="${ctx}/static/styles/boxy.css" type="text/css" rel="stylesheet">
</head>

<body>

<br/>

<form name="submitForm" id="submitForm" action="${ctx}/test/rechargetest/send" method="post">
    <div class="txt3_box" style="margin:0">
        请选择礼品发放服务器：<input type="hidden" name="serverName" id="servername"/>
        <input type="hidden" name="server" id="server"/><select id="serverlist"></select> 请输入领奖游戏角色名称：<input type="text"
                                                                                                             name="roleName"
                                                                                                             maxlength="9">
    </div>
    <div id="prizecount"></div>
    <!--数据显示开始-->
    <div id="data"></div>
    <!--数据显示结束-->
    <div>
        <!--页码显示开始-->
        <div id="page"></div>
        <!--页码显示结束-->
    </div>
    <div class="btn_send" style="overflow:hidden;margin-left:0"><a href="javascript:void(0)" id="btn_submit">选中表格中已获得的奖品，点击这里发放</a>
    </div>
</form>
<script type="text/javascript" src="${ctx}/static/jquery/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/event/jquery.boxy.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/plugin/jquery.validate.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/plugin/jquery.placeholder.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/plugin/jquery.form2.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/event/serverlist.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/event/ajaxpage-jqplugin.js"></script>
<script type="text/javascript" src="${ctx}/extend/wmpassport2/wanmei.passport.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/event/serverlist.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/plugin/jquery.tmpl.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/plugin/jquery.pagination.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/event/ajaxpage-jqplugin.js"></script>

<!--分页数据html模板开始-->
<script type="text/x-jquery-tmpl" id="tmpl">
    <table cellpadding="0" cellspacing="0" align="center" class="table_list" style="width:100%" border="1">
        <tr align="center" style="background-color:antiquewhite;height:30">
            <td>
                <input type="checkbox" id="checkall"> 奖品名称
            </td>
            <td>消耗积分</td>
            <td>角色名称</td>
            <td>服务器</td>
            <td>兑换时间</td>
            <td>奖品状态</td>
        </tr>
        {{each(i,obj) records}}
        <tr align="center">
            <td><input type="checkbox" name="prizeid" value="{{= obj.id}}">{{= obj.prizeName}}
            </td>
            <td>{{= obj.score}}</td>
            <td>--</td>
            <td>--</td>
            <td>{{= obj.exchangeTime}}</td>
            <td> 未发送</td>
        </tr>
        {{/each}}
    </table>

</script>
<script type="text/javascript">
    var paginationOptions = {
        url: "/test/rechargetest/list",
        pageSize: 5,
        current_page: 0,
        params: {key: new Date().getTime(), type:${type}},
        data_area_id: "data",
        tmpl_script: "tmpl",
        page_area_id: "page",
        num_display_entries: 10,
        num_edge_entries: 0,
        prev_text: "上一页",
        next_text: "下一页",
        link_to: "javascript:void(0);",
        ellipse_text: "",
        afterLoad: initButton
    };

    $(function () {
        $.hd.pagination(paginationOptions);

    });

    function initButton() {
        $('#checkall').click(function () {
            alert($(this).attr("checked"));
            if ($(this).attr("checked"))
                $('input:checkbox').not($("#checkall")).attr("checked", "true");
            else
                $('input:checkbox').not($("#checkall")).removeAttr("checked");
        });
    }

    $(function () {
        $("#serverlist").serverlist({
            gamename: "mhzx2"
        });
        var options = {
            beforeSubmit: validate,
            success: processJson,
            iframe: true,
            dataType: 'json'
        };
        $('#submitForm').submit(function () {
            $(this).ajaxSubmit(options);
            return false;
        });

        $('#btn_submit').click(function () {
            $('#submitForm').submit();
        });
    });

    function validate(formData, jqForm, options) {

        var form = jqForm[0];
        if ($('input:checked').not($("#checkall")).size() == 0) {
            alert("请选择您要发送的奖品。");
            return false;
        }
        if (form.server.value == "") {
            alert("请选择您要发送的服务器。");
            return false;
        }
        if (form.roleName.value == "") {
            alert("请选择您要发送的角色名称。");
            return false;
        }
    }
    function processJson(data) {
        if (data.success) {
            $.simpleAlert({content: data.message, ok: function () {
                window.location.reload();
            }});
        } else {
            $.simpleAlert(data.message);
        }
    }
</script>
</body>
</html>