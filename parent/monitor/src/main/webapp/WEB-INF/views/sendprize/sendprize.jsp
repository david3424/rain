<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../top.jsp" %>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center',title:'待发奖列表'">
        <%--搜索框--%>
        <div>
            <table>
                <tr>
                    <td><label for="table_name">表名:</label></td>
                    <td><input id="table_name"></td>
                    <td><label for="datasource">数据源:</label></td>
                    <td><input id="datasource"></td>
                    <td><a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true"
                           onclick="searchSendPrize();">查询</a></td>
                </tr>
            </table>
        </div>
        <%--内容--%>
        <table id="datagrid_send_prize" class="easyui-datagrid" fitColumns="true" singleSelect="true"
               toolbar="#send_prize_bar" url="/sendprize/list" rownumbers="true" idField="id"
               data-options="pagination:true,pageSize: 10,pageList:[10,20,30,40]">
            <thead>
            <tr>
                <th field="tableName">表名</th>
                <th field="datasource">数据源</th>
                <th field="roleIdType" formatter="roleTypeFormat">角色类型</th>
                <th field="sendType" formatter="sendTypeFormat">发送类型</th>
                <th field="jobCron">corn表达式</th>
                <th data-options="field:'createTime'">创建时间</th>
                <th field="updateTime" >更新时间</th>
                <th field="status" formatter="sendPrizeStatusFormat">定时器状态</th>
            </tr>
            </thead>
        </table>
        <%--toolbar--%>
        <div id="send_prize_bar">
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true"
               onclick="newSendPrize();">新增</a>
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true"
               onclick="updateSendPrize();">修改</a>
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-no" plain="true"
               onclick="deleteSendPrize();">删除</a>
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-reload" plain="true"
               onclick="startSendPrizeJob();">开启</a>
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
               onclick="pauseSendPrizeJob();">暂停</a>
        </div>
        <%--表单框--%>
        <div id="dialog_add_prize_send" class="easyui-dialog" style="width: 200px;height: 300px;" closed="true"
             buttons="#add_sendPrize_buttons">
            <form method="post" id="form_send_prize">
                <input type="hidden" name="id" id="sendprize_id">

                <div class="fitem">
                    <label>奖品表名</label>
                    <input name="tableName" id="tableName" class="easyui-validatebox" readonly="true"/>
                </div>
                <div class="fitem">
                    <label>数据源</label>
                    <input name="datasource" class="easyui-validatebox"/>
                </div>
                <div class="fitem">
                    <label>角色类型</label>
                    <select name="roleIdType">
                        <option value="0">角色名</option>
                        <option value="1">角色ID</option>
                    </select>
                </div>
                <div class="fitem">
                    <label>发送接口</label>
                    <select name="sendType">
                        <option value="0">接口1</option>
                        <option value="1">接口2</option>
                    </select>
                </div>
                <div class="fitem">
                    <label>corn表达式</label>
                    <input name="jobCron" value="0/30 * * * * ?">
                </div>
            </form>
        </div>

        <div id="add_sendPrize_buttons">
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-ok" plain="true"
               onclick="pSaveSendPrize();">保存</a>
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cancel" plain="true"
               onclick="pCancelSaveSendPrize();">取消</a>
        </div>
    </div>


    <%--提醒模块--%>
    <%-- <div data-options="region:'east',split:true,collapsed:true,collapsible:true,title:'提醒列表'" style="width:250px">
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
     </div>--%>
</div>
