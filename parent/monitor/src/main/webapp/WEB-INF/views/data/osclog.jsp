<%--
  Created by IntelliJ IDEA.
  User: gameuser
  Date: 14-3-18
  Time: 上午11:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../top.jsp" %>
<html>
<head>
    <title>波动监控LOG查看</title>
</head>
<body>
<div data-options="region:'center'" style="height:420px;">
    <div class="easyui-layout" data-options="fit:true">
        <div data-options="region:'center',title:'log数据'" style="height:420px;">
            <div>
                <table>
                    <tr>
                          <a href="javascript:history.go(-1);" class="easyui-linkbutton" iconCls="icon-back" plain="true">返回</a></td>
                    </tr>
                </table>
            </div>
            <table id="oslog_item" class="easyui-datagrid" fitColumns="true" singleSelect="true"
                   toolbar="#oslog_manage_bar" url="/data/item/oscillation/log/list?itemId=${itemId}&attrName=${attrName}"
                   rownumbers="true" idField="id"
                   data-options="pagination:true,pageSize: 10,pageList:[10,20,30,40]">
                <thead>
                <tr>
                    <th field="attrName">属性名称</th>
                    <th field="total">数据总数</th>
                    <th field="currentNum">当前监控段数据</th>
                    <th field="phaseDetail">时间策略</th>
                    <th field="times">监控次数</th>
                    <th field="createTime">创建时间</th>
                </tr>
                </thead>
            </table>
        </div>
    </div>
</div>

<%--<script type="text/javascript">--%>
    <%--var searchAttr = function () {--%>
        <%--var _serverName = $("#attr_name").val();--%>
        <%--//装入数据--%>
        <%--$("#oslog_item").datagrid({url: "/data/item/oscillation/search/list?attrName="--%>
                <%--+ _serverName}).datagrid("reload");--%>
        <%--return false;--%>
    <%--};--%>
<%--</script>--%>
</body>
</html>