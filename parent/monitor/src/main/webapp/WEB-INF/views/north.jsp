<%--
  Created by IntelliJ IDEA.
  User: david
  Date: 14-2-22
  Time: 上午12:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div data-options="region:'north',border:false" style="background-color:#0088cc;text-align:center;height:80px;padding:10px">
   <%-- <div style="padding: 5px; border: 1px solid #dddddd;background-color: #bce8f1">
        <a href="${ctx}/server/item/manage" class="easyui-linkbutton" data-options="plain:true">服务监控</a>
        <a href="${ctx}/user/manage" class="easyui-linkbutton" data-options="plain:true">用户管理</a>
        <a href="${ctx}/server/response/manage" class="easyui-linkbutton" data-options="plain:true">服务监控规则管理</a>
    </div>--%>
       <div id="header-inner">
           <table cellpadding="0" cellspacing="0" style="width:100%;">
               <tr>
                   <td rowspan="2" style="width:20px;">
                   </td>
                   <td style="height:52px;">
                       <div style="color:#fff;font-size:22px;font-weight:bold;">
                           <a href="" style="color:#fff;font-size:22px;font-weight:bold;text-decoration:none">活动监控平台</a>
                       </div>
                       <div style="color:#fff">
                           <a href="" style="color:#fff;text-decoration:none">预警&预测</a>
                       </div>
                   </td>
                   <td style="padding-right:5px;text-align:right;vertical-align:bottom;">
                       <shiro:user>
                           <!-- shiro:principal打印出了Subject的主当事人 - 在这个示例中，就是用户名： -->
                          <p style='font-family: "Microsoft YaHei",serif;color: lightyellow'>
                              你好, <shiro:principal property="name"/> <a style='font-family: "Microsoft YaHei",serif;color: #ffe0e0' href="${ctx}/logout">退出</a>
                          </p>
                       </shiro:user>
                   </td>
               </tr>
           </table>
       </div>
    </div>
