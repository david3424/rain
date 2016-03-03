<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="top.jsp"%>
<table id="model_grid" class="easyui-datagrid"
       data-options="idField:'id',toolbar:'#model_manage_bar',collapsible:true,url:'/model/list/tree',fitColumns:'true',rownumbers:'true', singleSelect:'true', method:'post',pagination:true,pageList:[10,20,30] ">
    <thead>
    <tr>
        <th field="text">功能名称</th>
        <th field="url">url</th>
        <th field="parentId">父节点id</th>
        <th field="status">状态</th>
    </tr>
    </thead>
</table>
<%--toolbar--%>
<div id="model_manage_bar" style="height:auto">
    <div style="margin-bottom:5px">
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true"
           onclick="newModel();">新增</a>
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true"
           onclick="updateModel();">修改</a>
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-no" plain="true"
           onclick="deleteModel();">删除</a>
    </div>
</div>

<%--弹出表单框-name与bean字段一致--%>
<div id="add_model_dlg" class="easyui-dialog" style="width: 400px;height: 300px;" closed="true"
     buttons="#add_model_buttons">
    <form method="post" id="model_form">
        <input type="hidden" name="id" id="setting_id">

        <div class="fitem">
            <label for="input_text">功能名称：</label>
            <input id="input_text" name="text" class="easyui-validatebox" required="true" readonly="true"/>
        </div>
        <div class="fitem">
            <label for="input_url">URL：</label>
            <input name="url" id="input_url" class="easyui-validatebox" required="true"/>
        </div>
        <div class="fitem">
            <label for="parentId">parentID：</label>
            <%--<input id="input_parentId" name="parentId" class="easyui-validatebox" required="true"/>--%>
            <select id="parentId" name="parentId" onchange="onSelect()">
            </select>
        </div>

    </form>
</div>
<%--确认取消按钮--%>
<div id="add_model_buttons">
    <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-ok" plain="true"
       onclick="pSaveModel();">保存</a>
    <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cancel" plain="true"
       onclick="pCancelSaveModel();">取消</a>
</div>

