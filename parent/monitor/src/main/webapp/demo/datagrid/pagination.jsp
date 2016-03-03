<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <link type="text/css" rel="stylesheet" href="${ctx}/static/jquery-easyui-1.3.4/themes/default/easyui.css"/>
</head>
<body>
<div class="easyui-tabs" data-options="fit: true, plain: true">
    <div data-options="title: 'Demo'">
        <table id="datagrid"></table>
    </div>
</div>
<script type="text/javascript" src="${ctx}/static/jquery-easyui-1.3.4/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery-easyui-1.3.4/jquery.easyui.min.js"></script>
<script type="text/javascript">
    $(function () {
        $('#datagrid').datagrid({
            columns: [
                [
                    {field: 'ck', checkbox: true},
                    {field: 'id', width: 80, title: 'Task ID', sortable: true},
                    {field: 'title', width: 100, title: 'Task title', sortable: true},
                    {field: 'description', width: 80, align: 'right', title: 'description'},
                    {field: 'user_id', width: 40, align: 'center', title: 'user_id', hidden: true}
                ]
            ],
            pagination: true,
            pageSize: 2,
            pageList:[2,10,20,30],
            rownumbers: true,
            remoteSort: false,
            title: 'Task pageGrid',
            url: '/easy/data',
            height: 350,
            width: 700
        });
    });
</script>
</body>
</html>