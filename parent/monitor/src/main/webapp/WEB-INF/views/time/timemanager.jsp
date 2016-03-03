<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../top.jsp" %>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center',title:'时间策略配置'">
        <%--搜索框--%>
        <div>
            <table>
                <tr>
                    <td><label for="strategy_name">策略名称:</label></td>
                    <td><input id="strategy_name"></td>
                    <td><a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true"
                           onclick="searchTime();">查询</a></td>
                </tr>
            </table>
        </div>
        <%--内容--%>
        <table id="time_item" class="easyui-datagrid" fitColumns="true" singleSelect="true"
               toolbar="#item_time_bar" url="/time/item/list/page" rownumbers="true" idField="id"
               data-options="pagination:true,pageSize: 10,pageList:[10,20,30,40]">
            <thead>
            <tr>
                <th field="strategyName">策略名称</th>
                <th field="memo">备注</th>
                <th field="createTime">创建时间</th>
            </tr>
            </thead>
        </table>
        <%--toolbar--%>
        <div id="item_time_bar">
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true"
               onclick="newTimeItem();">新增</a>
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true"
               onclick="modifyTimeItem();">修改</a>
        </div>
        <%--表单框--%>
        <div id="add_time_item" class="easyui-dialog" style="width: 200px;height: 300px;" closed="true"
             buttons="#add_item_buttons">
            <form method="post" id="form_time_item">
                <input type="hidden" name="id" id="setting_id">
                <input type="hidden" name="typeCode" id="type_code">

                <div class="fitem">
                    <label>策略名</label>
                    <input name="strategyName" class="easyui-validatebox" required="true"/>
                </div>

                <div class="fitem">
                    <label>备注</label>
                    <input name="memo" class="easyui-validatebox"/>
                </div>
            </form>
        </div>
        <div id="add_item_buttons">
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-ok" plain="true"
               onclick="pSaveTimeItem();">保存</a>
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cancel" plain="true"
               onclick="pCancelSaveTimeItem();">取消</a>
        </div>
    </div>


    <div data-options="region:'south',split:true,title:'详细策略配置信息'"
         style="min-height:150px; max-height:300px;padding:10px;">
        <table id="item_attr_grid" class="easyui-datagrid" fitColumns="true" singleSelect="true"
               toolbar="#item_attr_bar" url="/time/item/attr/list" rownumbers="true" idField="id">
            <thead>
            <tr>
                <th field="phaseName">策略属性</th>
                <th field="phaseStart">开始时间</th>
                <th field="phaseEnd">结束时间</th>
                <th field="tendency">状态</th>
                <th field="createTime">创建时间</th>
                <th field="memo">备注</th>
            </tr>
            </thead>
        </table>
        <%--toolbar--%>
        <div id="item_attr_bar">
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true"
               onclick="newTimeAttrItem();">新增</a>
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true"
               onclick="modifyTimeAttrItem();">修改</a>
        </div>
        <%--表单框--%>
        <div id="item_attr_dialog" class="easyui-dialog" style="width: 300px;height: 300px;" closed="true"
             buttons="#item_setting_dialog_button">
            <form method="post" id="item_attr_form">
                <input type="hidden" name="id">
                <input type="hidden" name="strategyId" id="setting_itemattr_id">

                <div class="fitem">
                    <label>策略属性</label>
                    <input name="phaseName" class="easyui-validatebox" required="true">
                </div>

                <div class="fitem">
                    <label>开始时间点</label>
                    <input name="phaseStart" class="easyui-timespinner" data-options="showSeconds:true"
                           required="true"/>
                </div>
                <div class="fitem">
                    <label>结束时间点</label>
                    <input name="phaseEnd" class="easyui-timespinner" data-options="showSeconds:true" required="true"/>
                </div>

                <div class="fitem">
                    <label>时段内趋势</label>
                    <select name="tendency">
                        <option value="0">平衡</option>
                        <option value="1">上升</option>
                        <option value="2">下降</option>
                    </select>
                </div>
                <div class="fitem">
                    <label>备注</label>
                    <input name="memo" class="easyui-validatebox" required="true">
                </div>

            </form>
        </div>
        <div id="item_setting_dialog_button">
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-ok" plain="true"
               onclick="saveTimeSettingAttr();">保存</a>
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cancel" plain="true"
               onclick="cancelTimeSaveSettingAttr();">取消</a>
        </div>
    </div>
</div>


<script type="text/javascript">
    <%--动态生成的东西，不能放到index.js中--%>
    var nowItem = "";
    $("#time_item").datagrid({onClickRow: function (row, data) {
        nowItem = data.id;
        $("#item_attr_grid").datagrid({url: '/time/item/attr/list?strategyId=' + data.id}).datagrid("reload");
    }});


    /****************************** 分割线 时间策略js操作   by zj***********************************/

    var searchTime = function () {
        var _serverName = $("#strategy_name").val();
        //装入数据
        var name = encodeURIComponent(encodeURIComponent(_serverName));
        $("#time_item").datagrid({url: "/time/item/list?strategyName="
                + name }).datagrid("reload");
        return false;
    };


    var itemSettingUrl = "";

    var newTimeItem = function () {
        itemSettingUrl = "/time/item/add";
        $("#add_time_item").dialog("open").dialog("setTitle", "增加一个时间策略");
        return false;
    };

    var modifyTimeItem = function () {
        var row = $("#time_item").datagrid("getSelected");
        if (row == undefined) {
            alert("请选择一行进行修改。");
            return false;
        }
        itemSettingUrl = "/time/item/update";
        $("#form_time_item").form("load", row);
        $("#add_time_item").dialog("open").dialog("setTitle", "修改时间策略");
        return false;
    };


    var pSaveTimeItem = function () {
        $("#form_time_item").form("submit", {url: itemSettingUrl, onSubmit: function () {
            return $(this).form("validate");
        },
            success: function (result) {
                result = eval('(' + result + ')');
                alert(result.message);
                if (result.success) {
                    $("#add_time_item").dialog("close");
                    $("#time_item").datagrid("reload");
                }
            }
        });
        return false;
    };

    var pCancelSaveTimeItem = function () {
        $("#add_time_item").dialog("close");
        return false;
    };


    /****************************** 分割线 时间策略 属性js操作  by zj**********************************/
    var itemAttrgUrl = "";

    var newTimeAttrItem = function () {
        var row = $("#time_item").datagrid("getSelected");
        if (row == undefined) {
            alert("请先选择上面时间策略然后添加属性。");
            return false;
        }
        $('#setting_itemattr_id').val(row.id);
        itemAttrgUrl = "/time/item/attr/add";
        $("#item_attr_dialog").dialog("open").dialog("setTitle", "增加一个时间策略的属性");
        return false;
    };

    var modifyTimeAttrItem = function () {
        var row = $("#item_attr_grid").datagrid("getSelected");
        if (row == undefined) {
            alert("请选择一行进行修改。");
            return false;
        }
        itemAttrgUrl = "/time/item/attr/update";
        $("#item_attr_form").form("load", row);
        $("#item_attr_dialog").dialog("open").dialog("setTitle", "修改时间策略的属性");
        return false;
    };


    var saveTimeSettingAttr = function () {
        $("#item_attr_form").form("submit", {url: itemAttrgUrl, onSubmit: function () {
            return $(this).form("validate");
        },
            success: function (result) {
                result = eval('(' + result + ')');
                alert(result.message);
                if (result.success) {
                    $("#item_attr_dialog").dialog("close");
                    $("#item_attr_grid").datagrid("reload");
                }
            }
        });
        return false;
    };

    var cancelTimeSaveSettingAttr = function () {
        $("#item_attr_dialog").dialog("close");
        return false;
    };
</script>

