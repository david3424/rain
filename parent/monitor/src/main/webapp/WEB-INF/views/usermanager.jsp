<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="top.jsp" %>
<table id="user_grid" class="easyui-datagrid"
       data-options="idField:'id',toolbar:'#user_manage_bar',collapsible:true,url:'/user/list/page',fitColumns:'true',rownumbers:'true', singleSelect:false, method:'post',pagination:true,pageList:[10,20,30] ">
    <thead>
    <tr>
        <th field="ck" checkbox="true">用户名</th>
        <th field="username">用户名</th>
        <th field="chName">中文名</th>
        <th field="email">邮箱</th>
        <th field="phone">电话号码</th>
        <th field="createTime">创建时间</th>
        <th field="roles">角色</th>
        <th field="status">状态</th>
    </tr>
    </thead>
</table>
<%--toolbar--%>
<div id="user_manage_bar" style="height:auto">
    <div style="margin-bottom:5px">
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true"
           onclick="newUser();">新增</a>
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true"
           onclick="updateUser();">修改</a>
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-no" plain="true"
           onclick="deleteUser();">删除</a>
    </div>
    <%--search area--%>
    <div>
        创建时间 From: <input class="easyui-datebox" id="start" name="start" style="width:120px">
        To: <input class="easyui-datebox" id="end" style="width:120px">
        角色:
        <select class="easyui-combobox" panelHeight="auto" id="roles" name="roles" style="width:100px"
                data-options="valueField:'name',textField:'name',url:'/role/list'">
        </select>
        <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="searchUser();">Search</a>
    </div>
</div>

<%--弹出表单框-name与bean字段一致--%>
<div id="add_user_dlg" class="easyui-dialog" style="width: 400px;height: 300px;" closed="true"
     buttons="#add_user_buttons">
    <form method="post" id="user_form">
        <input type="hidden" name="id" id="setting_id">

        <div class="fitem">
            <label for="input_username">用户名</label>
            <input id="input_username" name="username" class="easyui-validatebox" required="true" readonly="true"/>
        </div>
        <div class="fitem">
            <label for="input_chName">中文名</label>
            <input name="chName" id="input_chName" class="easyui-validatebox" required="true"/>
        </div>
        <div class="fitem">
            <label for="input_phone">电话</label>
            <input id="input_phone" name="phone" class="easyui-validatebox" required="true"/>
        </div>
        <div class="fitem">
            <label for="input_email">邮箱</label>
            <input id="input_email" name="email" class="easyui-validatebox" required="true"/>
        </div>
        <%--rolelist--%>
        <div class="fitem">
            <label for="roles">角色:</label>

            <div id="roles">
                <%--<input id="roleList1" name="roleList" type="checkbox" value="1" checked="checked">admin--%>
                <%--<input id="roleList2" name="roleList" type="checkbox" value="1" checked="checked">user--%>
            </div>
        </div>
        <%-- <div class="fitem">
             <label for="input_status">状态</label>
             <input id="input_status" name="status" class="easyui-validatebox" required="true"/>
         </div>--%>

    </form>
</div>
<%--确认取消按钮--%>
<div id="add_user_buttons">
    <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-ok" plain="true"
       onclick="pSaveUser();">保存</a>
    <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cancel" plain="true"
       onclick="pCancelSaveUser();">取消</a>
</div>

