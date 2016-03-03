<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../top.jsp"%>
<div class="easyui-layout" style="width:800px;height:600px;">
<div data-options="region:'west',split:true,title:'返回值类型'" style="width:200px;padding:10px;">
    <table id="type_grid" class="easyui-datagrid" url="/server/response/type/list" fitColumns="true" singleSelect="true"
           toolbar="#type_tool_bar" rownumbers="true">
        <thead>
        <tr>
            <th data-options="field:'typeCode'" width="60">编码</th>
        </tr>
        </thead>
    </table>
<shiro:hasPermission name="user:edit">
<div id="type_tool_bar">
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true"
           onclick="pNewType();">新增</a>
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-no" plain="true"
           onclick="pDeleteType();">删除</a>
    </div>
    </shiro:hasPermission>
</div>
<div data-options="region:'center',title:'具体返回值模板属性设定'" style="min-height:400px;padding:10px">
    <table id="type_settings" class="easyui-datagrid" fitColumns="true" singleSelect="true"
           toolbar="#type_setting_bar" url="/server/response/type/setting/list" rownumbers="true" idField="id">
        <thead>
        <tr>
            <th field="typeCode">类型</th>
            <th field="attributeName">属性名</th>
            <th field="healthValue">健康值</th>
            <th field="judgeMethod">判断方式</th>
            <th field="defaultLevel">预警级别</th>
        </tr>
        </thead>
    </table>
<shiro:hasPermission name="user:edit">
<div id="type_setting_bar">
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newSetting();">新增</a>
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true"
           onclick="updateSetting();">修改</a>
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-no" plain="true"
           onclick="deleteSetting();">删除</a>
    </div>
    </shiro:hasPermission>
</div>

<div id="add_type_dialog" class="easyui-dialog" style="width: 200px;height: 180px;" closed="true"
     buttons="#add_type_buttons">
    <form method="post" id="form_add_type" action="${ctx}/server/response/type/add">
        <div class="fitem">
            <label>编码</label>
            <input name="typeCode" class="easyui-validatebox" required="true"/>
        </div>
    </form>
</div>
<div id="add_type_buttons">
    <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="pSaveType();">保存</a>
    <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cancel" plain="true"
       onclick="cancelSaveType();">取消</a>
</div>

<div id="type_setting_dialog" class="easyui-dialog" style="width: 400px;height: 300px;" closed="true"
     buttons="#add_setting_buttons">
    <form method="post" id="form_add_setting" action="${ctx}/server/response/type/add">
        <input type="hidden" name="id" id="setting_id">
        <input type="hidden" name="typeCode" id="type_code">

        <div class="fitem">
            <label>属性名</label>
            <input name="attributeName" class="easyui-validatebox" required="true"/>
        </div>
        <div class="fitem">
            <label>健康值</label>
            <input name="healthValue" class="easyui-validatebox" required="true"/>
        </div>
        <div class="fitem">
            <label>判断方式</label>
            <input name="judgeMethod" class="easyui-validatebox" required="true"/>
        </div>
        <div class="fitem">
            <label>预警级别</label>
            <select name="defaultLevel">
                <option value="4">错误</option>
                <option value="3">警告</option>
            </select>
        </div>
    </form>
</div>
<div id="add_setting_buttons">
    <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-ok" plain="true"
       onclick="pSaveSetting();">保存</a>
    <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cancel" plain="true"
       onclick="cancelSaveSetting();">取消</a>
</div>

</div>
<script>
    var settingUpdateUrl = "";

    var nowCode = "";

    var pNewType = function () {
        $("#add_type_dialog").dialog("open").dialog("setTitle", "新增服务器返回类型");
    };

    var pSaveType = function () {
        $("#form_add_type").form("submit", {onSubmit: function () {
            return $(this).form("validate");
        },
            success: function (result) {
                result = eval('(' + result + ')');
                alert(result.message);
                if (result.success) {
                    $("#add_type_dialog").dialog("close");
                    $("#type_grid").datagrid("reload");
                }

            }
        });
    };

    var pSaveSetting = function () {
        $("#form_add_setting").form("submit", {
            url: settingUpdateUrl, onSubmit: function () {
                return $(this).form("validate");
            },
            success: function (result) {
                result = eval('(' + result + ')');
                alert(result.message);
                if (result.success) {
                    $("#type_setting_dialog").dialog("close");
                    $("#type_settings").datagrid("reload");
                }

            }
        });
    };

    var pDeleteType = function () {
        var row = $("#type_grid").datagrid("getSelected");
        if (row == undefined) {
            alert("请选择一行后删除。");
            return;
        }
        if (confirm("确定要删除类型：[" + row.typeCode + "]？删除时候同时删除该类型下面的设置！")) {
            var _url = "/server/response/type/delete";
            $.post(_url, {typeCode: row.typeCode}, function (result) {
                alert(result.message);
                if (result.success) {
                    $("#type_grid").datagrid("reload");
                    $("#type_settings").datagrid("reload");
                }
            }, "json");
        }
    };

    var deleteSetting = function () {
        var row = $("#type_settings").datagrid("getSelected");
        if (row == undefined) {
            alert("请选择一行后删除。");
            return;
        }

        if (confirm("确定要删除类型属性：[" + row.attributeName + "]？")) {
            var _url = "/server/response/type/setting/delete";
            $.post(_url, {id: row.id}, function (result) {
                alert(result.message);
                if (result.success) {
                    $("#type_settings").datagrid("reload");
                }
            }, "json");
        }

    };

    var cancelSaveType = function () {
        $("#add_type_dialog").dialog("close");
    };

    var cancelSaveSetting = function () {
        $("#type_setting_dialog").dialog("close");
    };

    var newSetting = function () {
        if (nowCode == "") {
            alert("请选择一个返回类型再添加。");
            return;
        }
        settingUpdateUrl = "/server/response/type/setting/add";
        $("#type_setting_dialog").dialog("open").dialog("setTitle", "新增服务器返回类型");
    };

    var updateSetting = function () {
        var row = $("#type_settings").datagrid("getSelected");
        if (row == undefined) {
            alert("请选择一行后修改。");
            return;
        }
        settingUpdateUrl = "/server/response/type/setting/update";
        $("#form_add_setting").form("load", row);

        //$("#setting_id").val(row.id);

        $("#type_setting_dialog").dialog("open").dialog("setTitle", "新增服务器返回类型");
    };

    $(function () {
        $("#type_grid").datagrid({onClickRow: function (row, data) {
            $("#type_code").val(data.typeCode);
            nowCode = data.typeCode;
            $("#type_settings").datagrid({url: '/server/response/type/setting/list?typeCode=' + data.typeCode}).datagrid("reload");
        }});
    });
</script>