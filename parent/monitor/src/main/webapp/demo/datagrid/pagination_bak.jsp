<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<jsp:include page="/WEB-INF/layouts/default_easy.jsp"/>
<html>
<head>
<script type="text/javascript">
   /* SyntaxHighlighter.config.tagName = "textarea";
    SyntaxHighlighter.all();*/
</script>
<script type="text/javascript">
    $(function(){
        $('#datagrid').datagrid({
            columns:[[
                {field: 'ck', checkbox: true},
                {field: 'itemid', width: 80, title: 'Item ID', sortable: true},
                {field: 'productid', width: 100, title: 'Product ID'},
                {field: 'listprice', width: 80, align: 'right', title: 'List Price', sortable: true},
                {field: 'unitcost', width: 80, align: 'right', title: 'Unit Cost'},
                {field: 'attr1', width: 250, title: 'Attribute'},
                {field: 'status', width: 60, align: 'center', title: 'Status', hidden: true}
            ]],
            singleSelect: true,
            rownumbers: true,
            remoteSort: false,
            title: 'Basic DataGrid',
            url: '/demo/datagrid/datagrid_data1.json',
            height: 250,
            width: 700,
            pagination: true,
            customAttr:{
                pagination:{
                    showRefresh: false,
                    displayMsg: 'hello world! HAHA~'
                },
                tooltip: {
                    enable: true
                }
            }
        })
                .datagrid('followCustomHandle');

        $('#btn').click(function(){
            $('#datagrid').datagrid('setPagination',{showRefresh: true})
        })
    });
</script>
</head>
    <body>
        <div class="easyui-tabs" data-options="fit: true, plain: true">
            <div data-options="title: 'Demo'">
                <h3>pagination 设置演示</h3>
                <input type="button" id="btn" value="显示刷新按钮"><br>
                <table id="datagrid"></table>
            </div>
            <div data-options="title: 'Code'" style="font-size: 14px;">
                <p>javascript:</p>
                <textarea class="brush: js; highlight:[20,21,22,23,24,25,26,27,28,30,33]">
                        $(function(){
                            $('#datagrid').datagrid({
                                columns:[[
                                    {field: 'ck', checkbox: true},
                                    {field: 'itemid', width: 80, title: 'Item ID', sortable: true},
                                    {field: 'productid', width: 100, title: 'Product ID'},
                                    {field: 'listprice', width: 80, align: 'right', title: 'List Price', sortable: true},
                                    {field: 'unitcost', width: 80, align: 'right', title: 'Unit Cost'},
                                    {field: 'attr1', width: 250, title: 'Attribute'},
                                    {field: 'status', width: 60, align: 'center', title: 'Status', hidden: true}
                                ]],
                                singleSelect: true,
                                rownumbers: true,
                                remoteSort: false,
                                title: 'Basic DataGrid',
                                url: '../datagrid/datagrid_data1.json',
                                height: 250,
                                width: 700,
                                pagination: true,
                                customAttr:{
                                    pagination:{
                                        showRefresh: false,
                                        displayMsg: 'hello world! HAHA~'
                                    },
                                    tooltip: {
                                        enable: true
                                    }
                                }
                            })
                            .datagrid('followCustomHandle');

                            $('#btn').click(function(){
                                $('#datagrid').datagrid('setPagination',{showRefresh: true})
                            })
                        });
                </textarea>

                <p>html:</p>
                <textarea class="brush: html;">
                    <body>
                        <h3>pagination 设置演示</h3>
                        <input type="button" id="btn" value="显示刷新按钮"><br>
                        <table id="datagrid"></table>
                    </body>
                </textarea>
            </div>
        </div>
    </body>
</html>