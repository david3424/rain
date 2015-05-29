<%@ page import="com.david.web.wanmei.common.util.ip.IpUtils" %>
<%@ page import="common.HdUser" %>
<%@ page import="common.protocol.FactionReturnInfo" %>
<%@ page import="common.protocol.RoleInfo" %>
<%@ page import="common.protocol.activity.SpreadIDInfo" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page import="java.util.List" %>
<%@ page import="com.david.web.wanmei.service.hdinterface.wrapper.FactionInfoServiceWrapper" %>
<%@ page import="com.david.web.wanmei.service.hdinterface.wrapper.RoleServiceWrapper" %>
<%@ page import="com.david.web.wanmei.service.hdinterface.wrapper.ActivityProtocolWrapper" %>
<%@ page import="common.protocol.activity.RankingInfo" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>协议接口测试</title>
</head>
<body>
<%
    if (false) {
        response.sendRedirect("http://www.wanmei.com");
    } else {
        FactionInfoServiceWrapper factionInfoInterface = (FactionInfoServiceWrapper) request.getAttribute("factionInfoInterface");
        RoleServiceWrapper roleInfoInterface = (RoleServiceWrapper) request.getAttribute("roleInfoInterface");
        ActivityProtocolWrapper activityProtocolInterface = (ActivityProtocolWrapper) request.getAttribute("activityProtocolInterface");
%>
<form action="/protocoltest" method="post">
    获取帮派信息getFactionInfo<br>
    服务器ID：<input type="text" name="getFactionInfo.server">&nbsp;&nbsp;&nbsp;&nbsp;
    用户ID：<input type="text" name="getFactionInfo.userid">&nbsp;&nbsp;&nbsp;&nbsp;
    角色名：<input type="text" name="getFactionInfo.rolename">&nbsp;&nbsp;&nbsp;&nbsp;
    帮派名：<input type="text" name="getFactionInfo.factionName">&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit"><br>
</form>

<%
    String getFactionInfo_server = request.getParameter("getFactionInfo.server");
    String getFactionInfo_userid = request.getParameter("getFactionInfo.userid");
    String getFactionInfo_rolename = request.getParameter("getFactionInfo.rolename");
    String getFactionInfo_factionName = request.getParameter("getFactionInfo.factionName");
    if (StringUtils.isNumeric(getFactionInfo_server) && StringUtils.isNumeric(getFactionInfo_userid) && StringUtils.isNotBlank(getFactionInfo_rolename) && StringUtils.isNotBlank(getFactionInfo_factionName)) {
        HdUser hdUser = new HdUser();
        hdUser.setServer(Integer.parseInt(getFactionInfo_server));
        hdUser.setUserid(getFactionInfo_userid);
        hdUser.setRolename(getFactionInfo_rolename);
        hdUser.setFactionName(getFactionInfo_factionName);
        FactionReturnInfo factionReturnInfo = factionInfoInterface.getFactionInfo(hdUser);
%>
<div>
    返回状态 ：   <%=factionReturnInfo.getReturnCode()%>-------<%=factionReturnInfo.getFactionDetail().getName()%>
</div>
<%
} else {
%>
<div>error</div>
<%}%>

<br><br>


<form action="/protocoltest" method="post">
    获取角色所有信息getRoleList<br>
    服务器ID：<input type="text" name="getRoleList.server">&nbsp;&nbsp;&nbsp;&nbsp;
    用户ID：<input type="text" name="getRoleList.userid">&nbsp;&nbsp;&nbsp;&nbsp;
    游戏ID：<input type="text" name="getRoleList.gameid">&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit"><br>
</form>

<%
    String getRoleList_server = request.getParameter("getRoleList.server");
    String getRoleList_userid = request.getParameter("getRoleList.userid");
    String getRoleList_gameid = request.getParameter("getRoleList.gameid");
    if (StringUtils.isNumeric(getRoleList_server) && StringUtils.isNumeric(getRoleList_userid) && StringUtils.isNotBlank(getRoleList_gameid)) {
        HdUser hdUser = new HdUser();
        hdUser.setServer(Integer.parseInt(getRoleList_server));
        hdUser.setUserid(getRoleList_userid);
        hdUser.setGameid(Integer.parseInt(getRoleList_gameid));
        List<RoleInfo> roleInfoList = roleInfoInterface.getRoleList(hdUser);
%>
<div>
    <%
        if (roleInfoList != null && roleInfoList.size() > 0) {
            for (RoleInfo roleInfo : roleInfoList) {
                response.getWriter().println("角色ID：" + roleInfo.getRoleId() + "-角色名：" + roleInfo.getRoleName() + "-等级：" + roleInfo.getLevel());
            }
        } else {
            response.getWriter().println("角色信息为null。");
        }

    %>
</div>
<%
} else {
%>
<div>error</div>
<%}%>


<br><br>


<form action="/protocoltest" method="post">
    获取角色等级getRoleLevel<br>
    服务器ID：<input type="text" name="getRoleLevel.server">&nbsp;&nbsp;&nbsp;&nbsp;
    用户ID：<input type="text" name="getRoleLevel.userid">&nbsp;&nbsp;&nbsp;&nbsp;
    游戏ID：<input type="text" name="getRoleLevel.gameid">&nbsp;&nbsp;&nbsp;&nbsp;
    角色名：<input type="text" name="getRoleLevel.rolename">&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit"><br>
</form>

<%
    String getRoleLevel_server = request.getParameter("getRoleLevel.server");
    String getRoleLevel_userid = request.getParameter("getRoleLevel.userid");
    String getRoleLevel_gameid = request.getParameter("getRoleLevel.gameid");
    String getRoleLevel_rolename = request.getParameter("getRoleLevel.rolename");
    if (StringUtils.isNumeric(getRoleLevel_server) && StringUtils.isNumeric(getRoleLevel_userid) && StringUtils.isNotBlank(getRoleLevel_gameid) && StringUtils.isNotBlank(getRoleLevel_rolename)) {
        HdUser hdUser = new HdUser();
        hdUser.setServer(Integer.parseInt(getRoleLevel_server));
        hdUser.setUserid(getRoleLevel_userid);
        hdUser.setGameid(Integer.parseInt(getRoleLevel_gameid));
        hdUser.setRolename(getRoleLevel_rolename);
        Integer rtn = roleInfoInterface.getRoleLevel(hdUser);
%>
<div>
    返回：<%=rtn%>
</div>
<%
} else {
%>
<div>error</div>
<%}%>

<br><br>


<form action="/protocoltest" method="post">
    元宝数查询<br>
    服务器ID：<input type="text" name="getGoldNum.server">&nbsp;&nbsp;&nbsp;&nbsp;
    用户ID：<input type="text" name="getGoldNum.userid">&nbsp;&nbsp;&nbsp;&nbsp;
    游戏ID：<input type="text" name="getGoldNum.gameid">&nbsp;&nbsp;&nbsp;&nbsp;
    <input type="submit"><br>
</form>

<%
    String getGoldNum_server = request.getParameter("getGoldNum.server");
    String getGoldNum_userid = request.getParameter("getGoldNum.userid");
    String getGoldNum_gameid = request.getParameter("getGoldNum.gameid");
    if (StringUtils.isNumeric(getGoldNum_server) && StringUtils.isNumeric(getGoldNum_userid) && StringUtils.isNotBlank(getGoldNum_gameid)) {
        HdUser hdUser = new HdUser();
        hdUser.setServer(Integer.parseInt(getGoldNum_server));
        hdUser.setUserid(getGoldNum_userid);
        hdUser.setGameid(Integer.parseInt(getGoldNum_gameid));
        Integer rtn = activityProtocolInterface.getGoldInfo(hdUser);
%>
<div>
    返回：<%=rtn%>
</div>
<%
} else {
%>
<div>error</div>
<%}%>



<br><br>


<form action="/protocoltest" method="post">
    推广IDgenerateSpreadID<br>
    服务器ID：<input type="text" name="generateSpreadID.server">&nbsp;&nbsp;&nbsp;&nbsp;
    用户ID：<input type="text" name="generateSpreadID.userid">&nbsp;&nbsp;&nbsp;&nbsp;
    游戏ID：<input type="text" name="generateSpreadID.gameid">&nbsp;&nbsp;&nbsp;&nbsp;
    <input type="submit"><br>
</form>

<%
    String generateSpreadID_server = request.getParameter("generateSpreadID.server");
    String generateSpreadID_userid = request.getParameter("generateSpreadID.userid");
    String generateSpreadID_gameid = request.getParameter("generateSpreadID.gameid");
    if (StringUtils.isNumeric(generateSpreadID_server) && StringUtils.isNumeric(generateSpreadID_userid) && StringUtils.isNotBlank(generateSpreadID_gameid)) {
        HdUser hdUser = new HdUser();
        hdUser.setServer(Integer.parseInt(generateSpreadID_server));
        hdUser.setUserid(generateSpreadID_userid);
        hdUser.setGameid(Integer.parseInt(generateSpreadID_gameid));
        List<SpreadIDInfo> list = activityProtocolInterface.generateSpreadID(hdUser);
%>
<div>
    <%
        if (list != null && list.size() > 0) {
            for (SpreadIDInfo spreadIDInfo : list) {
                response.getWriter().println("推广ID：" + spreadIDInfo.promoteId + "-角色名ID：" + spreadIDInfo.roleId);
            }
        } else {
            response.getWriter().println("推广ID为空。");
        }

    %>
</div>
<%
} else {
%>
<div>error</div>
<%}%>
<form action="/protocoltest" method="post">
    排行榜<br>
    服务器ID：<input type="text" name="rankInfo.server">&nbsp;&nbsp;&nbsp;&nbsp;
    用户ID：<input type="text" name="rankInfo.userid">&nbsp;&nbsp;&nbsp;&nbsp;
    游戏ID：<input type="text" name="rankInfo.gameid">&nbsp;&nbsp;&nbsp;&nbsp;
    <input type="submit"><br>
</form>

<%
    String rankInfo_server = request.getParameter("rankInfo.server");
    String rankInfo_userid = request.getParameter("rankInfo.userid");
    String rankInfo_gameid = request.getParameter("rankInfo.gameid");
    if (StringUtils.isNumeric(rankInfo_server) && StringUtils.isNumeric(rankInfo_userid) && StringUtils.isNotBlank(rankInfo_gameid)) {
        HdUser hdUser = new HdUser();
        hdUser.setServer(Integer.parseInt(rankInfo_server));
        hdUser.setUserid(rankInfo_userid);
        hdUser.setGameid(Integer.parseInt(rankInfo_gameid));
//        if (activityProtocolInterface == null) {
//            activityProtocolInterface = new ActivityProtocolServiceImpl();
//            response.getWriter().println("new!");
//        }
        List<RankingInfo> list = activityProtocolInterface.ranking(hdUser);
%>
<div>
    <%
        if (list != null && list.size() > 0) {
            for (RankingInfo rankInfo : list) {
                response.getWriter().println("账号id：" + rankInfo.getUserid()
                        + " 角色名：" + rankInfo.getRolename() + " 排名：" + rankInfo.getRankid());
            }
        } else {
            response.getWriter().println("排行榜为空。");
        }

    %>
</div>
<%
} else {
%>
<div>error</div>
<%}%>

<%}%>
</body>
</html>