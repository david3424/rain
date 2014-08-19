<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="top.jsp"%>
<table id="role_grid" class="easyui-datagrid"
       data-options="idField:'id',toolbar:'#role_manage_bar',collapsible:true,url:'/role/list',fitColumns:'true',rownumbers:'true', singleSelect:'true', method:'post' ">
    <thead>
    <tr>
        <th field="name">角色名</th>
        <th field="permissions">权限</th>
    </tr>
    </thead>
</table>
<%--toolbar--%>
<shiro:hasPermission name="user:edit">
<div id="role_manage_bar" style="height:auto">
    <div style="margin-bottom:5px">
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true"
           onclick="newRole();">新增</a>
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true"
           onclick="updateRole();">修改</a>
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-no" plain="true"
           onclick="deleteRole();">删除</a>
    </div>
</div>
</shiro:hasPermission>

<%--弹出表单框-name与bean字段一致--%>
<div id="add_role_dlg" class="easyui-dialog" style="width: 400px;height: 300px;" closed="true"
     buttons="#add_role_buttons">
    <form method="post" id="role_form">
        <input type="hidden" name="id" id="setting_id">

        <div class="fitem">
            <label for="input_rolename">角色名</label>
            <input id="input_rolename" name="name" class="easyui-validatebox" required="true" readonly="true"/>
        </div>
        <div class="fitem">
            <label for="input_permissions">权限</label>
            <input id="input_permissions" name="permissions" class="easyui-validatebox" required="true"/>
        </div>
        <%--rolelist--%>
        <div class="fitem">
            <label for="users">用户:</label>
            <div id="users">
                <%--<input id="roleList1" name="roleList" type="checkbox" value="1" checked="checked">admin--%>
                <%--<input id="roleList2" name="roleList" type="checkbox" value="1" checked="checked">user--%>
            </div>
        </div>
    </form>
</div>
<%--确认取消按钮--%>
<div id="add_role_buttons">
    <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-ok" plain="true"
       onclick="pSaveRole();">保存</a>
    <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cancel" plain="true"
       onclick="pCancelSaveRole();">取消</a>
</div>

