<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: czw
  Date: 14-3-6
  Time: 下午6:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../top.jsp" %>
<html>
<head>
    <title>数据监控管理页面</title>
</head>
<body>
<div data-options="region:'center'" style="height:420px;">
    <div class="easyui-layout" data-options="fit:true">
        <div data-options="region:'center',title:'监控项列表'" style="height:480px;">
            <%--搜索框--%>
            <div>
                <table>
                    <tr>
                        <td><label for="data_item_name">服务名:</label></td>
                        <td><input id="data_item_name"></td>
                        <td><a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true"
                               onclick="searchItem();">查询</a></td>
                    </tr>
                </table>
            </div>
            <%--内容--%>
          <div style="width:1000px;">
            <table id="monitor_item" class="easyui-datagrid" fitColumns="true" singleSelect="true"
                   toolbar="#item_manage_bar" url="/data/item/list" rownumbers="true" idField="id"
                   data-options="pagination:true,pageSize: 10,pageList:[10,20,30,40]">
                <thead>
                <tr>
                    <th field="itemName">监控项名</th>
                    <th field="itemNameCh" formatter="itemNameFormat">监控中文名</th>
                    <th field="beginTime">监控开始时间</th>
                    <th field="endTime">监控结束时间</th>
                    <th field="changeTime">状态改变时间</th>
                    <th field="userId" formatter="userFormat">创建人</th>
                    <th field="jobStatus" formatter="jobStatusFormat">定时器状态</th>
                    <th field="jobCron">执行计划</th>
                    <th field="status" formatter="statusFormat">当前状态</th>
                </tr>
                </thead>
            </table>
           </div>
            <%--toolbar--%>
            <div id="item_manage_bar">
                <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true"
                   onclick="newDataItem();">新增</a>
                <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true"
                   onclick="updateDataItem();">修改</a>
                <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-no" plain="true"
                   onclick="deleteDataItem();">删除</a>
                <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-reload" plain="true"
                   onclick="startDataMJob();">开启</a>
                <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
                   onclick="pauseDataMJob();">暂停</a>
            </div>
            <%--表单框--%>
            <div id="add_server_item" class="easyui-dialog" style="width: 300px;height:350px;" closed="true"
                 buttons="#add_item_buttons">
                <form method="post" id="form_server_item">
                    <input type="hidden" name="id" id="setting_id">
                    <input type="hidden" name="typeCode" id="type_code">

                    <div class="fitem">
                        <label>数据监控名</label>
                        <input name="itemName" class="easyui-validatebox" required="true"/>
                    </div>

                    <div class="fitem">
                        <label>数据源(用于开关)</label>
                        <select name="dataSource" class="easyui-validatebox">
                            <option value="event">event</option>
                            <option value="huodong218">huodong218</option>
                            <option value="huodong226">huodong226</option>
                            <option value="huodong108">huodong108(eventhk)</option>
                            <option value="huodong164">eventie(huodong164读)</option>
                            <option value="hdbase">hdbase(log)</option>
                        </select>
                    </div>

                    <div class="fitem">
                        <label>中文名字</label>
                        <input name="itemNameCh" class="easyui-validatebox" required="true"/>
                    </div>

                    <div class="fitem">
                        <label>监控开始时间</label>
                        <input name="beginTime" class="easyui-datetimebox" required="true"/>
                    </div>

                    <div class="fitem">
                        <label>监控结束时间</label>
                        <input name="endTime" class="easyui-datetimebox" required="true"/>
                    </div>
                    <div>
                        <label>cron</label>
                        <input name="jobCron" value="0/30 * * * * ?">
                    </div>

                    <div>
                        <label>备注</label>
                        <textarea name="memo" style="height: 60px;"></textarea>
                    </div>

                </form>
            </div>
            <div id="add_item_buttons">
                <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-ok" plain="true"
                   onclick="saveDataItem();">保存</a>
                <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cancel" plain="true"
                   onclick="cancelSaveDataItem();">取消</a>
            </div>
        </div>
        <div data-options="region:'east',split:true,collapsible:true,title:'提醒列表'" style="width:150px">
            <table id="remind_grid" class="easyui-datagrid"
                   data-options="idField:'id',toolbar:'#remind_operate_bar',url:'/data/item/remind/list',rownumbers:'true', singleSelect:'true', method:'post'">
                <thead>
                <tr>
                    <th field="typeName">类型</th>
                    <th field="remindType" hidden="true"></th>
                    <th field="chName">人员</th>
                    <th field="userId" hidden="true"></th>
                </tr>
                </thead>
            </table>
            <shiro:hasPermission name="user:edit">
                <div id="remind_operate_bar">
                    <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true"
                       onclick="addRemind();">新增</a>
                    <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true"
                       onclick="modifyRemind();">修改</a>
                    <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-no" plain="true"
                       onclick="deleteRemind();">删除</a>
                </div>
            </shiro:hasPermission>
            <div id="remind_dialog" class="easyui-dialog" style="width: 250px;height: 200px;" closed="true"
                 buttons="#remind_dialog_button">
                <form method="post" id="remind_form">
                    <input type="hidden" name="id">
                    <input type="hidden" name="itemId" id="item_id">

                    <div class="fitem">
                        <label>提醒方式</label>
                        <select name="remindType">
                            <option value="1">短信</option>
                            <option value="2">邮件</option>
                        </select>
                    </div>
                    <div>
                        <label>提醒人</label>
                        <input id="input_remind_user" class="easyui-combobox" name="userId"
                               data-options="valueField:'id',textField:'chName',url:'/user/list'"/>
                    </div>
                </form>
            </div>
            <div id="remind_dialog_button">
                <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-ok" plain="true"
                   onclick="saveRemind();">保存</a>
                <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cancel" plain="true"
                   onclick="cancelSaveRemind();">取消</a>
            </div>
        </div>
    </div>

</div>
<div data-options="region:'south',title:'监控设置',split:true" style="height:400px;">
<div class="easyui-layout" data-options="fit:true">
<div data-options="region:'west',title:'属性设置',split:true"
     style="min-width: 400px; max-width:600px;">
    <table id="attr_setting_grid" class="easyui-datagrid" fitColumns="true" singleSelect="true"
           toolbar="#attr_setting_bar" url="/data/item/attr/list" rownumbers="true" idField="id">
        <thead>
        <tr>
            <th field="attrName" width="100px">属性名</th>
            <th field="chName" width="100px">中文名</th>
            <th field="sql" width="200px" formatter="itemNameFormatSQL">查询sql</th>
            <th field="dataSource" width="100px">数据源</th>
        </tr>
        </thead>
    </table>
    <%--toolbar--%>
    <div id="attr_setting_bar">
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true"
           onclick="addAttr();">新增</a>
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-no" plain="true"
           onclick="deleteAttr();">删除</a>
    </div>
    <%--表单框--%>
    <div id="attr_setting_dialog" class="easyui-dialog" style="width: 340px;height: 320px;" closed="true"
         buttons="#attr_setting_dialog_button">
        <form method="post" id="attr_setting_form">
            <input type="hidden" name="id">
            <input type="hidden" name="itemId" id="attr_item_id">

            <div class="fitem">
                <label>属性名</label>
                <input name="attrName" class="easyui-validatebox" required="true">
            </div>

            <div class="fitem">
                <label>属性中文名</label>
                <input name="chName" class="easyui-validatebox" required="true">
            </div>

            <div class="fitem">
                <label>数据源</label>
                <!-- 这里应该数据库里面配置的，但是增么都得改代码，所以就放在这里了-->
                <select name="dataSource">
                    <option value="event">event</option>
                    <option value="huodong218">event20</option>
                    <option value="huodong108">eventhk</option>
                    <option value="huodong164">eventie(huodong164)</option>
                    <option value="hdbase">hdbase</option>
                </select>
            </div>

            <div class="fitem">
                <label>sql</label>
                <textarea name="sql" style="height: 60px;" class="easyui-validatebox" required="true"></textarea>
            </div>
            <div class="fitem">
                <label>备注</label>
                <textarea name="memo" style="height: 40px;"></textarea>
            </div>

        </form>
    </div>
    <div id="attr_setting_dialog_button">
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-ok" plain="true"
           onclick="saveAttr();">保存</a>
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cancel" plain="true"
           onclick="cancelSaveAttr();">取消</a>
    </div>
</div>
<div data-options="region:'center',title:'监控设置',split:true"
     style="width:600px;">
    <div id="check_two" class="easyui-accordion" data-options="fit:true,multiple:true"
         style="overflow: auto;padding: 10px;height: 400px; width: 580px;">
        <div title="规则监控">
            <table id="rule_setting_grid" class="easyui-datagrid" fitColumns="true" singleSelect="true"
                   toolbar="#rule_setting_bar" url="/data/item/rule/list" rownumbers="true" idField="id">
                <thead>
                <tr>
                    <th field="checkerName" width="100px">检查名</th>
                    <th field="chName" width="100px">中文名</th>
                    <th field="expression" width="200px">检查表达式</th>
                    <th field="abnLevel" width="50px" formatter="abnLevelFormat">预警级别</th>
                    <th field="status" width="50px" formatter="attrStatusFormat">异常状态</th>
                </tr>
                </thead>
            </table>
            <%--toolbar--%>
            <div id="rule_setting_bar">
                <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true"
                   onclick="addRuleSetting();">新增</a>
                <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true"
                   onclick="modifyRuleSetting();">修改</a>
                <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-no" plain="true"
                   onclick="deleteRuleSetting();">删除</a>
            </div>
            <%--表单框--%>
            <div id="rule_setting_dialog" class="easyui-dialog" style="width: 400px;height: 200px;" closed="true"
                 buttons="#rule_setting_dialog_button">
                <form method="post" id="item_setting_form">
                    <input type="hidden" name="id">
                    <input type="hidden" name="itemId" id="rule_item_id">


                    <div class="fitem">
                        <label>检查名</label>
                        <input name="checkerName" id="checkerName" class="easyui-validatebox" required="true">
                    </div>


                    <div class="fitem">
                        <label>中文名</label>
                        <input name="chName" class="easyui-validatebox" required="true">
                    </div>

                    <div class="fitem">
                        <label>表达式</label>
                        <input name="expression" class="easyui-validatebox" required="true" style="width: 200px;"/>
                    </div>

                    <div class="fitem">
                        <label>预警级别</label>
                        <select name="abnLevel">
                            <option value="4">错误</option>
                        </select>
                    </div>
                    <div class="fitem">
                        <label>备注</label>
                        <textarea name="memo" style="height: 60px;"></textarea>
                    </div>
                </form>
            </div>
            <div id="rule_setting_dialog_button">
                <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-ok" plain="true"
                   onclick="saveRuleSetting();">保存</a>
                <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cancel" plain="true"
                   onclick="cancelSaveRuleSetting();">取消</a>
            </div>
        </div>

        <div title="波动检测" style="overflow: auto;padding: 10px;">
            <table id="oscl_setting_grid" class="easyui-datagrid" singleSelect="true"
                   toolbar="#oscl_setting_bar" url="/data/item/oscillation/list" rownumbers="true" idField="id">
                <thead>
                <tr>
                    <input type="hidden" name="itemId" field="itemId" fix="true" id="oscl_item_id_temp">
                    <th field="oscillation" fix="true" width="100px">检测名</th>
                    <th field="attrName" fix="true" width="100px">检查属性</th>
                    <th field="timeStep" fix="true" width="100px">间隔（分钟）</th>
                    <th field="phaseStrategy" fix="true" width="100px" formatter="phaseStrategyFormatter">时段策略
                    </th>
                    <th field="abnLevel" fix="true" width="50px" formatter="abnLevelFormat">预警级别</th>
                    <th field="status" fix="true" width="50px" formatter="attrStatusFormat">异常状态</th>
                </tr>
                </thead>
            </table>
            <%--toolbar--%>
            <div id="oscl_setting_bar">
                <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true"
                   onclick="addOsclSetting();">新增</a>
                <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true"
                   onclick="modifyOsclSetting();">修改</a>
                <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-no" plain="true"
                   onclick="deleteOsclSetting();">删除</a>&nbsp;&nbsp; &nbsp;
                <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true"
                   onclick="searchLog();">查询波动LOG</a>
            </div>
            <%--表单框--%>
            <div id="oscl_setting_dialog" class="easyui-dialog" style="width: 400px;height: 300px;" closed="true"
                 buttons="#oscl_setting_dialog_button">
                <form method="post" id="oscl_setting_form">
                    <input type="hidden" name="itemId" id="oscl_item_id">

                    <div class="fitem">
                        <label>检查名</label>
                        <input name="oscillation" id="oscillation" class="easyui-validatebox" required="true">
                    </div>

                    <div class="fitem">
                        <label>检查属性</label>
                        <input name="attrName" class="easyui-validatebox" required="true">
                    </div>

                    <div class="fitem">
                        <label>检查时间间隔</label>
                        <input name="timeStep" class="easyui-validatebox" required="true" style="width: 200px;"/>
                    </div>

                    <div>
                        <label>时段策略</label>
                        <input id="input_phase_strategy" class="easyui-combobox" name="phaseStrategy"
                               data-options="valueField:'id',textField:'strategyName',url:'/time/item/list'"/>
                    </div>

                    <div class="fitem">
                        <label>预警级别</label>
                        <select name="abnLevel">
                            <option value="3">预警</option>
                        </select>
                    </div>
                    <div class="fitem">
                        <label>备注</label>
                        <textarea name="memo" style="height: 60px;"></textarea>
                    </div>
                </form>
            </div>
            <div id="oscl_setting_dialog_button">
                <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-ok" plain="true"
                   onclick="saveOsclSetting();">保存</a>
                <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cancel" plain="true"
                   onclick="cancelSaveOsclSetting();">取消</a>
            </div>

        </div>
    </div>

</div>
</div>
</div>


<script type="text/javascript">
var dataItemFormUrl = "";
var nowSelectItemId = 0; // 记录一下当前选中要操作的记录,当然也可以不用


/*
 */

var itemNameFormat = function (value, row) {
    return "<a href='#' title='" + row.memo + "' class='easyui-tooltip'>" + row.itemNameCh + "</a>";
};

var itemNameFormatSQL = function (value, row) {
    return "<a href='#' title='" + row.sql + "' class='easyui-tooltip'>" + row.sql + "</a>";
};


var userFormat = function (value, row) {
    return row.user.username;
};

var jobStatusFormat = function (value, row) {
    if(row.jobStatusName=="运行"){
        return "<span style='color:red'>"+row.jobStatusName+"</span>";
    }else{
        return row.jobStatusName;
    }

};

var statusFormat = function (value, row) {
    return row.statusName;
};

var newDataItem = function () {
    dataItemFormUrl = "/data/item/add";
    $("#add_server_item").dialog("open").dialog("setTitle", "增加一个数据监控项");
    return false;
};

var saveDataItem = function () {
    $("#form_server_item").form("submit", {url: dataItemFormUrl, onSubmit: function () {
        return $(this).form("validate");
    },
        success: function (result) {
            result = eval('(' + result + ')');
            alert(result.message);
            if (result.success) {
                $("#add_server_item").dialog("close");
                $("#monitor_item").datagrid("reload");
            }
        }
    });
    return false;
};

var cancelSaveDataItem = function () {
    $("#add_server_item").dialog("close");
    return false;
};

var updateDataItem = function () {
    var row = $("#monitor_item").datagrid("getSelected");
    dataItemFormUrl = "/data/item/update";
    $("#form_server_item").form("load", row);
    $("#add_server_item").dialog("open").dialog("setTitle", "修改数据监控");
    return false;
};

var deleteDataItem = function () {
    var row = $("#monitor_item").datagrid("getSelected");
    if (row == undefined) {
        alert("请选择一行后删除。");
        return false;
    }
    if (confirm("确定要删除数据监控项：[" + row.itemName + "]？")) {
        var _url = "/data/item/delete";
        $.post(_url, {id: row.id}, function (result) {
            alert(result.message);
            if (result.success) {
                $("#monitor_item").datagrid("reload");
            }
        }, "json");
    }
    return false;
};

var searchItem = function () {
    var _serverName = $("#item_name").val();
    var _serverUrl = $("#item_url").val();
    //装入数据
    $("#monitor_item").datagrid({url: "/data/item/list?itemName="
            + _serverName + "&itemUrl=" + _serverUrl}).datagrid("reload");
    return false;
};


var startDataMJob = function () {
    var row = $("#monitor_item").datagrid("getSelected");
    if (row == undefined) {
        alert("请选择一项监控进行开启。");
        return false;
    }
    if (row.jobStatus == "1") {
        alert("监控已经是开启状态了。");
        return false;
    }
    if (confirm("确定要开启监控：[" + row.itemName + "]的job？")) {
        var _url = "/data/item/job/start";
        $.post(_url, {id: row.id}, function (result) {
            alert(result.message);
            if (result.success) {
                $("#monitor_item").datagrid("reload");
            }
        }, "json");
    }
    return false;
};

var pauseDataMJob = function () {
    var row = $("#monitor_item").datagrid("getSelected");
    if (row == undefined) {
        alert("请选择一项监控进行暂停。");
        return false;
    }
    if (row.jobStatus == "0") {
        alert("监控已经是暂停状态了。");
        return false;
    }
    if (confirm("确定要关闭监控：[" + row.itemName + "]的job？")) {
        var _url = "/data/item/job/pause";
        $.post(_url, {id: row.id}, function (result) {
            alert(result.message);
            if (result.success) {
                $("#monitor_item").datagrid("reload");
            }
        }, "json");
    }
    return false;
};


$("#monitor_item").datagrid({onClickRow: function (row, data) {
    nowSelectItemId = data.id;
    $("#attr_setting_grid").datagrid({url: '/data/item/attr/list?itemId=' + data.id}).datagrid("reload");
    $("#rule_setting_grid").datagrid({url: '/data/item/rule/list?itemId=' + data.id}).datagrid("reload");
    $("#remind_grid").datagrid({url: '/data/item/remind/list?itemId=' + data.id}).datagrid("reload");
    $("#oscl_setting_grid").datagrid({url: '/data/item/oscillation/list?itemId=' + data.id}).datagrid("reload");
}});
/*************************以下就是属性设置的js*******************************/

var attrSettingUrl = "";

var addAttr = function () {
    var row = $("#monitor_item").datagrid("getSelected");
    if (row == undefined) {
        alert("请选择一个数据监控项为期添加监控属性。");
        return false;
    }

    attrSettingUrl = "/data/item/attr/add";
    $("#attr_item_id").val(nowSelectItemId);
    $("#attr_setting_dialog").dialog("open").dialog("setTitle", "增加一个监控属性");
    return false;
};

var deleteAttr = function () {
    var row = $("#attr_setting_grid").datagrid("getSelected");
    if (row == undefined) {
        alert("请选择一行后删除。");
        return false;
    }
    if (confirm("确定要删除监控属性：" + row.attrName + "--提醒--[" + row.chName + "]？")) {
        var _url = "/data/item/attr/delete";
        $.post(_url, {itemId: row.itemId, attrName: row.attrName}, function (result) {
            alert(result.message);
            if (result.success) {
                $("#attr_setting_grid").datagrid("reload");
            }
        }, "json");
    }
    return false;
};

var saveAttr = function () {
    $("#attr_setting_form").form("submit", {url: attrSettingUrl, onSubmit: function () {
        return $(this).form("validate");
    },
        success: function (result) {
            result = eval('(' + result + ')');
            alert(result.message);
            if (result.success) {
                $("#attr_setting_dialog").dialog("close");
                $("#attr_setting_grid").datagrid("reload");
            }
        }
    });
    return false;
};

var cancelSaveAttr = function () {
    $("#attr_setting_dialog").dialog("close");
    return false;
};

/*************************以下就是检查 设置的js*******************************/
var ruelItemFormUrl = "";
var addRuleSetting = function () {
    var row = $("#monitor_item").datagrid("getSelected");
    $("#checkerName").removeAttr("readonly").attr("required", "true");
    if (row == undefined) {
        alert("请选择一个数据监控项为期添加监控检查属性。");
        return false;
    }

    ruelItemFormUrl = "/data/item/rule/add";
    $("#rule_item_id").val(nowSelectItemId);
    $("#rule_setting_dialog").dialog("open").dialog("setTitle", "增加一个监控检查项属性");
    return false;
};
var modifyRuleSetting = function () {
    var row = $("#rule_setting_grid").datagrid("getSelected");
    $("#checkerName").attr("readonly", "true");
    ruelItemFormUrl = "/data/item/rule/update";
    $("#item_setting_form").form("load", row);
    $("#rule_setting_dialog").dialog("open").dialog("setTitle", "修改监控检查项属性");
    return false;
};
var deleteRuleSetting = function () {
    var row = $("#rule_setting_grid").datagrid("getSelected");
    if (row == undefined) {
        alert("请选择一行后删除。");
    }
    if (confirm("确定要删除监控检查项属性：" + row.checkerName + "--提醒--[" + row.chName + "]？")) {
        var _url = "/data/item/rule/delete";
        $.post(_url, {itemId: row.itemId, checkerName: row.checkerName}, function (result) {
            alert(result.message);
            if (result.success) {
                $("#rule_setting_grid").datagrid("reload");
            }
        }, "json");
    }

    return false;
};

var saveRuleSetting = function () {
    $("#item_setting_form").form("submit", {url: ruelItemFormUrl, onSubmit: function () {
        return $(this).form("validate");
    },
        success: function (result) {
            result = eval('(' + result + ')');
            alert(result.message);
            if (result.success) {
                $("#rule_setting_dialog").dialog("close");
                $("#rule_setting_grid").datagrid("reload");
            }
        }
    });
    return false;
};

var cancelSaveRuleSetting = function () {
    $("#rule_setting_dialog").dialog("close");
    return false;
};

/*******************************波动检查的设置********************************/

var osclFormUrl = "";

var addOsclSetting = function () {
    var row = $("#monitor_item").datagrid("getSelected");
    if (row == undefined) {
        alert("请选择一个数据监控项为期添加监控检查属性。");
        return false;
    }
    osclFormUrl = "/data/item/oscillation/add";
    $("#oscl_item_id").val(nowSelectItemId);
    $("#oscl_setting_dialog").dialog("open").dialog("setTitle", "增加一个波动检查");
    return false;
};

var modifyOsclSetting = function () {
    var row = $("#oscl_setting_grid").datagrid("getSelected");
    osclFormUrl = "/data/item/oscillation/update";
    $("#oscl_setting_form").form("load", row);
    $("#oscl_setting_dialog").dialog("open").dialog("setTitle", "修改波动检查");
    return false;
};

var deleteOsclSetting = function () {
    var row = $("#oscl_setting_grid").datagrid("getSelected");
    if (row == undefined) {
        alert("请选择一行后删除。");
        return false;
    }
    if (confirm("确定要删除波动检查：" + row.typeName + "--提醒--[" + row.chName + "]？")) {
        var _url = "/data/item/oscillation/delete";
        $.post(_url, {id: row.id}, function (result) {
            alert(result.message);
            if (result.success) {
                $("#oscl_setting_grid").datagrid("reload");
            }
        }, "json");
    }
    return false;
};

var saveOsclSetting = function () {
    $("#oscl_setting_form").form("submit", {url: osclFormUrl, onSubmit: function () {
        return $(this).form("validate");
    },
        success: function (result) {
            result = eval('(' + result + ')');
            alert(result.message);
            if (result.success) {
                $("#oscl_setting_dialog").dialog("close");
                $("#oscl_setting_grid").datagrid("reload");
            }
        }
    });
    return false;
};

var cancelSaveOsclSetting = function () {
    $("#oscl_setting_dialog").dialog("close");
    return false;
};


var phaseStrategyFormatter = function (value, row) {
    return row.dateTimeItem.strategyName;
};

/******************************提醒列表的代码****************************/

var remindFormUrl = "";
var addRemind = function () {
    remindFormUrl = "/data/item/remind/add";
    $("#item_id").val(nowSelectItemId);
    $("#input_remind_user").removeAttr("readonly");
    $("#remind_dialog").dialog("open").dialog("setTitle", "增加一个提醒");
    return false;
};

var modifyRemind = function () {
    var row = $("#remind_grid").datagrid("getSelected");
    remindFormUrl = "/data/item/remind/update";
    $("#input_remind_user").attr("readonly", "true");
    $("#remind_form").form("load", row);
    $("#remind_dialog").dialog("open").dialog("setTitle", "修改提醒");
    return false;
};

var deleteRemind = function () {
    var row = $("#remind_grid").datagrid("getSelected");
    if (row == undefined) {
        alert("请选择一行后删除。");
        return false;
    }
    if (confirm("确定要删除提醒：" + row.typeName + "--提醒--[" + row.chName + "]？")) {
        var _url = "/data/item/remind/delete";
        $.post(_url, {id: row.id}, function (result) {
            alert(result.message);
            if (result.success) {
                $("#remind_grid").datagrid("reload");
            }
        }, "json");
    }
    return false;
};

var saveRemind = function () {
    $("#remind_form").form("submit", {url: remindFormUrl, onSubmit: function () {
        return $(this).form("validate");
    },
        success: function (result) {
            result = eval('(' + result + ')');
            alert(result.message);
            if (result.success) {
                $("#remind_dialog").dialog("close");
                $("#remind_grid").datagrid("reload");
            }
        }
    });
    return false;
};

var cancelSaveRemind = function () {
    $("#remind_dialog").dialog("close");
    return false;
};


<!--波动监控log查看-->
var searchLog = function () {
    var row = $("#oscl_setting_grid").datagrid("getSelected");
    if (row == undefined) {
        alert("请选择一个属性项进行log查询。");
        return false;
    }
    window.location.href = "/data/item/oscillation/oslog?itemId=" + row.itemId + "&attrName=" + row.attrName;
}

</script>
</body>
</html>
