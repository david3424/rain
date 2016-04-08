<%@ page import="com.wanmei.webservice.ServiceManage" %>
<%@ page import="common.LogInfo" %>
<%--
  Created by IntelliJ IDEA.
  User: czw
  Date: 13-7-19
  Time: 下午3:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<form action="testSendPrize.jsp" method="post">
    <table>
        <tr>
            <td>账号</td>
            <td><input type="text" name="account"/></td>
        </tr>
        <tr>
            <td>角色ID</td>
            <td><input type="text" name="roleid"/></td>
        </tr>

        <tr>
            <td>角色名</td>
            <td><input type="text" name="rolename"/></td>
        </tr>

        <tr>
            <td>奖品ID</td>
            <td><input type="text" name="prize"/></td>
        </tr>


        <tr>
            <td>Long or int</td>
            <td><select name="type">
                <option value="1">Int</option>
                <option value="2">Long</option>
            </select>
            </td>
        </tr>

        <tr>
            <td>所在服务器</td>
            <td><input type="text" name="server"/></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="发奖"/></td>
        </tr>
    </table>
    <%

        String account = request.getParameter("account");

        if(account == null || account.equals(""))
        {
            return;
        }

        String roleid = request.getParameter("roleid");
        String server = request.getParameter("server");
        String rolename = request.getParameter("rolename");

        Integer roleIdI = Integer.parseInt(roleid);

        String type = request.getParameter("type");
        Integer typeI = Integer.parseInt(type);

        Integer prize = Integer.parseInt(request.getParameter("prize"));

        Integer serverI = Integer.parseInt(server);



        if (rolename != null && (!rolename.trim().isEmpty())) {
            int rtn = ServiceManage.activityService.presentGoods2(account, serverI, rolename, prize, new LogInfo(1, "", "test rolename"));
            out.write("status:" + rtn);
        } else {
            if (typeI == 1) {
                int rtn = ServiceManage.activityService.presentGoods3(account, serverI, roleIdI, prize, new LogInfo(1, "", "test int role id"));
                out.write("status:" + rtn);
            } else {
                int rtn = ServiceManage.activityService.presentGoods4(account, serverI, Long.parseLong(roleid), prize, new LogInfo(1, "", "test long role id"));
                out.write("status:" + rtn);
            }
        }
    %>

</form>
</body>
</html>