<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<jsp:include page="/WEB-INF/layouts/default_easy.jsp"/>
<html>
    <head>
        <meta charset="utf-8">
        <title>Row Editing DataGrid -- jQUery EasyUI Demo</title>
        <script type="text/javascript">
            SyntaxHighlighter.config.tagName = "textarea";
            SyntaxHighlighter.all();
        </script>
        <script type="text/javascript">
            $(function(){
                $('#datagrid').datagrid({
                    columns:[[
                        {field: 'username', title: 'Name', width: 150, editor: 'text'},
                        {field: 'sex', title: 'Sex', width: 50, editor: 'text'},
                        {field: 'age', title: 'Age', width: 80, editor: 'numberspinner'},
                        {field: 'brithday', title: 'Birthday', width: 100, editor: 'my97'},
                        {field: 'registdate', title: 'Regist Date', width: 150, editor: 'datetimebox'},
                        {field: 'arrivaltime', title: 'Arrival Time (AT)', width: 110, editor: 'timespinner'},
                        {field: 'itemid', title: 'Product', width: 100, editor:{
                            type: 'combogrid',
                            options: {
                                columns:[[
                                    {checkbox: true},
                                    {field: 'itemid', width: 80, title: 'Item ID', sortable: true},
                                    {field: 'productname', width: 100, title: 'Product'},
                                    {field: 'listprice', width: 80, align: 'right', title: 'List Price', sortable: true},
                                    {field: 'unitcost', width: 80, align: 'right', title: 'Unit Cost'},
                                    {field: 'attr1', width: 250, title: 'Attribute'},
                                    {field: 'status', width: 60, align: 'center', title: 'Status', hidden: true}
                                ]],
                                singleSelect: true,
                                url: '/demo/datagrid/datagrid_data1.json',
                                idField: 'itemid',
                                textField: 'productname',
                                title: 'Product List'
                            }
                        }}
                    ]],
                    title: 'Row Editing DataGrid',
                    singleSelect: true,
                    rownumbers: true,
                    height: 250,
                    width: 800,
                    url: '/demo/datagrid/datagrid_data2.json',
                    toolbar:[{
                        text: 'save',
                        iconCls: 'icon-save',
                        handler: function(){
                            var editingRow = $('#datagrid').datagrid('getEditingRow');
                            if(editingRow){
                                var index = $('#datagrid').datagrid('getRowIndex', editingRow);
                                $('#datagrid').datagrid('endEdit', index);
                            }
                        }
                    },{
                        text: 'getChanges',
                        handler: function(){
                            alert($('#datagrid').datagrid('getChanges','updated'))
                        }
                    }],
                    customAttr: {
                        rowediting: true,
                        onConfirmEdit: function(rowIndex){
                            return confirm('提交修改？');
                        }
                    },
                    onClickRow: function(index){
                        var editingRow = $('#datagrid').datagrid('getEditingRow');
                        var editingRowIndex = $('#datagrid').datagrid('getRowIndex', editingRow);
                        if(!editingRow){
                            $('#datagrid').datagrid('selectRow', index)
                                    .datagrid('beginEdit', index);
                        }else{
                            $('#datagrid').datagrid('selectRow', editingRowIndex);
                        }
                    }
                })
                .datagrid('followCustomHandle');
            });

        </script>
    </head>
    <body>
        <!--<ul>-->
            <!--<li>Ext rowediting编辑风格</li>-->
            <!--<li>自定义Editor(my97、datetimebox、numberspinner、timespinner、combogrid)</li>-->
        <!--</ul>-->
        <!--<table id="datagrid"></table>-->




        <div class="easyui-tabs" data-options="fit: true, plain: true">
            <div data-options="title: 'Demo'">
                <h3>编辑模式</h3>
                <ul>
                    <li>Ext rowediting编辑风格</li>
                    <li>自定义Editor(my97、datetimebox、numberspinner、timespinner、combogrid)</li>
                    <li>onConfirmEdit事件演示</li>
                </ul>
                <table id="datagrid"></table>
            </div>
            <div data-options="title: 'Code'" style="font-size: 14px;">
                <p>javascript:</p>
                <textarea class="brush: js; highlight:[6,7,8,9,52,53,54,55,56,57,69]">
                        $(function(){
                            $('#datagrid').datagrid({
                                columns:[[
                                    {field: 'username', title: 'Name', width: 150, editor: 'text'},
                                    {field: 'sex', title: 'Sex', width: 50, editor: 'text'},
                                    {field: 'age', title: 'Age', width: 80, editor: 'numberspinner'},
                                    {field: 'brithday', title: 'Birthday', width: 100, editor: 'my97'},
                                    {field: 'registdate', title: 'Regist Date', width: 150, editor: 'datetimebox'},
                                    {field: 'arrivaltime', title: 'Arrival Time (AT)', width: 110, editor: 'timespinner'},
                                    {field: 'itemid', title: 'Product', width: 100, editor:{
                                        type: 'combogrid',
                                        options: {
                                            columns:[[
                                                {checkbox: true},
                                                {field: 'itemid', width: 80, title: 'Item ID', sortable: true},
                                                {field: 'productname', width: 100, title: 'Product'},
                                                {field: 'listprice', width: 80, align: 'right', title: 'List Price', sortable: true},
                                                {field: 'unitcost', width: 80, align: 'right', title: 'Unit Cost'},
                                                {field: 'attr1', width: 250, title: 'Attribute'},
                                                {field: 'status', width: 60, align: 'center', title: 'Status', hidden: true}
                                            ]],
                                            singleSelect: true,
                                            url: '../datagrid/datagrid_data1.json',
                                            idField: 'itemid',
                                            textField: 'productname',
                                            title: 'Product List'
                                        }
                                    }}
                                ]],
                                title: 'Row Editing DataGrid',
                                singleSelect: true,
                                rownumbers: true,
                                height: 250,
                                width: 800,
                                url: '../datagrid/datagrid_data2.json',
                                toolbar:[{
                                    text: 'save',
                                    iconCls: 'icon-save',
                                    handler: function(){
                                        var editingRow = $('#datagrid').datagrid('getEditingRow');
                                        if(editingRow){
                                            var index = $('#datagrid').datagrid('getRowIndex', editingRow);
                                            $('#datagrid').datagrid('endEdit', index);
                                        }
                                    }
                                },{
                                    text: 'getChanges',
                                    handler: function(){
                                        alert($('#datagrid').datagrid('getChanges','updated'))
                                    }
                                }],
                                customAttr: {
                                    rowediting: true,
                                    onConfirmEdit: function(rowIndex){
                                        return confirm('提交修改？');
                                    }
                                },
                                onClickRow: function(index){
                                    var editingRow = $('#datagrid').datagrid('getEditingRow');
                                    var editingRowIndex = $('#datagrid').datagrid('getRowIndex', editingRow);
                                    if(!editingRow){
                                        $('#datagrid').datagrid('selectRow', index)
                                                .datagrid('beginEdit', index);
                                    }else{
                                        $('#datagrid').datagrid('selectRow', editingRowIndex);
                                    }
                                }
                            })
                            .datagrid('followCustomHandle');
                        });
                </textarea>

                <p>html:</p>
                <textarea class="brush: html;">
                    <body>
                        <ul>
                            <li>Ext rowediting编辑风格</li>
                            <li>自定义Editor(my97、datetimebox、numberspinner、timespinner、combogrid)</li>
                        </ul>
                        <table id="datagrid"></table>
                    </body>
                </textarea>
            </div>
        </div>
    </body>
</html>