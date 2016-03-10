<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../top.jsp" %>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center',title:'监控项列表'">
        <%--搜索框--%>
        <div>
            <table>
                <tr>
                    <td><label for="item_name">服务名:</label></td>
                    <td><input id="item_name"></td>
                    <td><label for="item_url">服务地址:</label></td>
                    <td><input id="item_url"></td>

                    <td><a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true"
                           onclick="searchItem();">查询</a></td>
                </tr>
            </table>
        </div>
        <%--内容--%>
        <table id="monitor_item" class="easyui-datagrid" fitColumns="true" singleSelect="true"
               toolbar="#item_manage_bar" url="/server/item/list" rownumbers="true" idField="id"
               data-options="pagination:true,pageSize: 10,pageList:[10,20,30,40]">
            <thead>
            <tr>
                <th field="itemName">服务名</th>
                <th field="itemUrl" formatter="itemUrlFormat">服务地址</th>
                <th field="returnType">返回类型</th>
                <th field="changeTime">状态改变时间</th>
                <th data-options="field:'chName'">创建人</th> <%--指定属性 width可根据总的设置--%>
                <%--<th field="userId" hidden="true"></th>--%>
                <th field="jobStatusName" formatter="jobStatusFormat">定时器状态</th>
                <%--<th field="jobStatus" hidden="true"></th>--%>
                <th field="jobCron">执行计划</th>
                <th field="statusName">当前状态</th>
            </tr>
            </thead>
        </table>
        <%--toolbar--%>
        <div id="item_manage_bar">
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true"
               onclick="newItem();">新增</a>
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true"
               onclick="updateItem();">修改</a>
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-no" plain="true"
               onclick="deleteItem();">删除</a>
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-reload" plain="true"
               onclick="startJob();">开启</a>
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
               onclick="pauseJob();">暂停</a>
        </div>
        <%--表单框--%>
        <div id="add_server_item" class="easyui-dialog" style="width: 200px;height: 300px;" closed="true"
             buttons="#add_item_buttons">
            <form method="post" id="form_server_item">
                <input type="hidden" name="id" id="setting_id">
                <input type="hidden" name="typeCode" id="type_code">
                <input type="hidden" name="userId" id="user_id">
                <div class="fitem">
                    <label>服务名</label>
                    <input name="itemName" class="easyui-validatebox" required="true"/>
                </div>
                <div class="fitem">
                    <label>服务中文名</label>
                    <input name="itemNameCh" class="easyui-validatebox" required="true"/>
                </div>
                <div class="fitem">
                    <label>服务地址</label>
                    <input name="itemUrl" class="easyui-validatebox" required="true"/>
                </div>
                <div class="fitem">
                    <label>返回类型</label>
                    <input id="type_list" class="easyui-combobox" name="returnType"
                           data-options="valueField:'typeCode',textField:'typeCode',url:'/server/response/type/list'">
                </div>
                <div>
                    <label>cron</label>
                    <input name="jobCron" value="0/30 * * * * ?">
                </div>
            </form>
        </div>
            
        <div id="add_item_buttons">
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-ok" plain="true"
               onclick="pSaveItem();">保存</a>
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cancel" plain="true"
               onclick="pCancelSaveItem();">取消</a>
        </div>
    </div>
    <%--提醒模块--%>
    <div data-options="region:'east',split:true,collapsed:true,collapsible:true,title:'提醒列表'" style="width:250px">
        <table id="remind_grid" class="easyui-datagrid"
               data-options="idField:'id',toolbar:'#remind_operate_bar',url:'',rownumbers:'true', singleSelect:'true', method:'post'">
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
    <%--详细监控信息--%>
    <div data-options="region:'south',split:true,title:'详细监控信息'" style="min-height:150px; max-height:300px;padding:10px;">
        <table id="item_setting_grid" class="easyui-datagrid" fitColumns="true" singleSelect="true"
               toolbar="#item_setting_bar" url="" rownumbers="true" idField="id">
            <thead>
            <tr>
                <th field="attributeName">属性名</th>
                <th field="healthValue">健康值</th>
                <th field="judgeMethod">判断方式</th>
                <th field="abnLevel" formatter="abnLevelFormat">预警级别</th>
                <th field="abnTimes">异常次数</th>
                <th field="statusBegin">状态改变时间</th>
                <th field="status" formatter="attrStatusFormat">异常状态</th>
            </tr>
            </thead>
        </table>
        <%--detail toolbar--%>
        <div id="item_setting_bar">
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true"
               onclick="addSettingAttr();">新增</a>
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true"
               onclick="modifySettingAttr();">修改</a>
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-no" plain="true"
               onclick="deleteSettingAttr();">删除</a>
        </div>
        <%--表单框--%>
        <div id="item_setting_dialog" class="easyui-dialog" style="width: 300px;height: 200px;" closed="true"
             buttons="#item_setting_dialog_button">
            <form method="post" id="item_setting_form">
                <input type="hidden" name="id">
                <input type="hidden" name="itemId" id="setting_item_id">
                <input type="hidden" name="typeCode" id="setting_item_type"/>
    
                <div class="fitem">
                    <label>属性名</label>
                    <input name="attributeName" class="easyui-validatebox" required="true">
                </div>
    
                <div class="fitem">
                    <label>健康值</label>
                    <input name="healthValue" class="easyui-validatebox" required="true">
                </div>
    
                <div class="fitem">
                    <label>判断方式</label>
                    <select name="judgeMethod">
                        <option value=">">大于健康值</option>
                        <option value="<">小于健康值</option>
                        <option value=">=">大于、等于健康值</option>
                        <option value="<=">小于、等于健康值</option>
                        <option value="=">等于健康值</option>
                    </select>
                </div>
    
                <div class="fitem">
                    <label>预警级别</label>
                    <select name="abnLevel">
                        <option value="4">错误</option>
                        <option value="3">警告</option>
                    </select>
                </div>
    
            </form>
        </div>
        <div id="item_setting_dialog_button">
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-ok" plain="true"
               onclick="saveSettingAttr();">保存</a>
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cancel" plain="true"
               onclick="cancelSaveSettingAttr();">取消</a>
        </div>
    </div>
</div>
