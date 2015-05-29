<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.david.web.wanmei.service.hdinterface.wrapper.ActivityServiceWrapper" %>
<%@ page import="com.david.web.wanmei.service.hdinterface.wrapper.MonthlyServiceWrapper" %>
<%@ page import="com.david.web.wanmei.service.hdinterface.wrapper.WeeklyServiceWrapper" %>
<%@ page import="common.*" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Vector" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>activity接口测试</title>
</head>
<body>
<%
    if (false) {
        response.sendRedirect("http://www.wanmei.com");
    } else {
        HdUser hdUser;
        ActivityServiceWrapper activityServiceInterface = (ActivityServiceWrapper) request.getAttribute("activityServiceInterface");
        MonthlyServiceWrapper monthlyServiceInterface = (MonthlyServiceWrapper) request.getAttribute("monthlyServiceInterface");
        WeeklyServiceWrapper weeklyServiceInterface = (WeeklyServiceWrapper) request.getAttribute("weeklyServiceInterface");
%>

<form action="/activitytest" method="post">
    获取用户信息getUserInfoByName<br>
    用户名：<input type="text" name="getUserInfoByName.username">&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit"><br>
</form>

<%
    String getUserInfoByName_username = request.getParameter("getUserInfoByName.username");
    if (StringUtils.isNotBlank(getUserInfoByName_username)) {
        hdUser = new HdUser();
        hdUser.setUsername(getUserInfoByName_username);
        UserBean userBean = activityServiceInterface.getUserInfoByName(hdUser);
%>
<div>
    用户ID：<%=userBean == null ? "null" : userBean.userid%>
</div>
<%
} else {
%>
<div>error</div>
<%}%>

<br><br>


<form action="/activitytest" method="post">
    查询充值queryChargeSumByName<br>
    用户名：<input type="text" name="queryChargeSumByName.username">&nbsp;&nbsp;&nbsp;&nbsp;
    游戏ID：<input type="text" name="queryChargeSumByName.gameid">&nbsp;&nbsp;&nbsp;&nbsp;
    服务器：<input type="text" name="queryChargeSumByName.server">&nbsp;&nbsp;&nbsp;&nbsp;
    开始时间：<input type="text" name="queryChargeSumByName.starttime">&nbsp;&nbsp;&nbsp;&nbsp;
    结束时间：<input type="text" name="queryChargeSumByName.endtime">&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit"><br>
</form>

<%
    String queryChargeSumByName_username = request.getParameter("queryChargeSumByName.username");
    String queryChargeSumByName_gameid = request.getParameter("queryChargeSumByName.gameid");
    String queryChargeSumByName_server = request.getParameter("queryChargeSumByName.server");
    String queryChargeSumByName_starttime = request.getParameter("queryChargeSumByName.starttime");
    String queryChargeSumByName_endtime = request.getParameter("queryChargeSumByName.endtime");
    if (StringUtils.isNotBlank(queryChargeSumByName_username) && StringUtils.isNotBlank(queryChargeSumByName_gameid) && StringUtils.isNotBlank(queryChargeSumByName_server) && StringUtils.isNotBlank(queryChargeSumByName_starttime) && StringUtils.isNotBlank(queryChargeSumByName_endtime)) {
        hdUser = new HdUser();
        hdUser.setUsername(queryChargeSumByName_username);
        hdUser.setGameid(Integer.parseInt(queryChargeSumByName_gameid));
        hdUser.setServer(Integer.parseInt(queryChargeSumByName_server));
        hdUser.setSearchstart(queryChargeSumByName_starttime);
        hdUser.setSearchend(queryChargeSumByName_endtime);
        Long rtn = activityServiceInterface.queryChargeSumByName(hdUser);
%>
<div>
    积分：<%=rtn%>
</div>
<%
} else {
%>
<div>error</div>
<%}%>

<br><br>


<form action="/activitytest" method="post">
    查询充值记录queryBillByName<br>
    用户名：<input type="text" name="queryBillByName.username">&nbsp;&nbsp;&nbsp;&nbsp;
    游戏ID：<input type="text" name="queryBillByName.gameid">&nbsp;&nbsp;&nbsp;&nbsp;
    开始时间：<input type="text" name="queryBillByName.starttime">&nbsp;&nbsp;&nbsp;&nbsp;
    结束时间：<input type="text" name="queryBillByName.endtime">&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit"><br>
</form>

<%
    String queryBillByName_username = request.getParameter("queryBillByName.username");
    String queryBillByName_gameid = request.getParameter("queryBillByName.gameid");
    String queryBillByName_starttime = request.getParameter("queryBillByName.starttime");
    String queryBillByName_endtime = request.getParameter("queryBillByName.endtime");
    if (StringUtils.isNotBlank(queryBillByName_username) && StringUtils.isNumeric(queryBillByName_gameid)
            && StringUtils.isNotBlank(queryBillByName_starttime) && StringUtils.isNotBlank(queryBillByName_endtime)) {
        hdUser = new HdUser();
        hdUser.setUsername(queryBillByName_username);
        hdUser.setGameid(Integer.parseInt(queryBillByName_gameid));
        hdUser.setSearchstart(queryBillByName_starttime);
        hdUser.setSearchend(queryBillByName_endtime);
        List<ScoreInfo> rtn = activityServiceInterface.queryBillByName(hdUser);
%>
<div>
    账单：
    <%
        if (rtn == null) {
            response.getWriter().print("参数错误，查看接口返回定义。");
        } else {
            for (ScoreInfo scoreInfo : rtn) {
                response.getWriter().println(scoreInfo);
            }
        }
    %>
</div>
<%
} else {
%>
<div>error</div>
<%}%>

<br><br>


<form action="/activitytest" method="post">
    角色验证verifyRoleExists2<br>
    用户名：<input type="text" name="verifyRoleExists2.username">&nbsp;&nbsp;&nbsp;&nbsp;
    服务器id：<input type="text" name="verifyRoleExists2.server">&nbsp;&nbsp;&nbsp;&nbsp;
    角色名：<input type="text" name="verifyRoleExists2.rolename">&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit"><br>
</form>

<%
    String verifyRoleExists2_username = request.getParameter("verifyRoleExists2.username");
    String verifyRoleExists2_server = request.getParameter("verifyRoleExists2.server");
    String verifyRoleExists2_rolename = request.getParameter("verifyRoleExists2.rolename");
    if (StringUtils.isNotBlank(verifyRoleExists2_username) && StringUtils.isNotBlank(verifyRoleExists2_rolename) && StringUtils.isNumeric(verifyRoleExists2_server)) {
        hdUser = new HdUser();
        hdUser.setUsername(verifyRoleExists2_username);
        hdUser.setServer(Integer.parseInt(verifyRoleExists2_server));
        hdUser.setRolename(verifyRoleExists2_rolename);
        Long rtn = activityServiceInterface.verifyRoleExists(hdUser);
%>
<div>
    角色ID：<%=rtn%>
</div>
<%
} else {
%>
<div>error</div>
<%}%>

<br><br>


<form action="/activitytest" method="post">
    激活用户activeUser<br>
    用户名：<input type="text" name="activeUser.username">&nbsp;&nbsp;&nbsp;&nbsp;
    激活码：<input type="text" name="activeUser.sn">&nbsp;&nbsp;&nbsp;&nbsp;
    激活类型：<input type="text" name="activeUser.type">&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit"><br>
</form>

<%
    String activeUser_username = request.getParameter("activeUser.username");
    String activeUser_sn = request.getParameter("activeUser.sn");
    String activeUser_type = request.getParameter("activeUser.type");
    if (StringUtils.isNotBlank(activeUser_username) && StringUtils.isNotBlank(activeUser_sn) && StringUtils.isNumeric(activeUser_type)) {
        hdUser = new HdUser();
        hdUser.setUsername(activeUser_username);
        hdUser.setSn(activeUser_sn);
        hdUser.setType(Integer.parseInt(activeUser_type));
        Integer rtn = activityServiceInterface.activeUser(hdUser);
%>
<div>
    返回：<%=rtn%> (0.成功; 1.用户不存在; 2.用户已被激活; 3.激活码不存在; -1.其他错误. 20110720
    判断的条件是取code前两位和GMServer.conf配置中activeUser中的字段匹配，不根据aid判断)
</div>
<%
} else {
%>
<div>error</div>
<%}%>

<br><br>


<form action="/activitytest" method="post">
    是否是帮主verifyFactionMaster2<br>
    用户名：<input type="text" name="verifyFactionMaster2.username">&nbsp;&nbsp;&nbsp;&nbsp;
    服务器id：<input type="text" name="verifyFactionMaster2.server">&nbsp;&nbsp;&nbsp;&nbsp;
    角色名：<input type="text" name="verifyFactionMaster2.rolename">&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit"><br>
</form>

<%
    String verifyFactionMaster2_username = request.getParameter("verifyFactionMaster2.username");
    String verifyFactionMaster2_server = request.getParameter("verifyFactionMaster2.server");
    String verifyFactionMaster2_rolename = request.getParameter("verifyFactionMaster2.rolename");
    if (StringUtils.isNotBlank(verifyFactionMaster2_username) && StringUtils.isNotBlank(verifyFactionMaster2_rolename) && StringUtils.isNumeric(verifyFactionMaster2_server)) {
        hdUser = new HdUser();
        hdUser.setUsername(verifyFactionMaster2_username);
        hdUser.setServer(Integer.parseInt(verifyFactionMaster2_server));
        hdUser.setRolename(verifyFactionMaster2_rolename);
        Integer rtn = activityServiceInterface.verifyFactionMaster2(hdUser);
%>
<div>
    帮派ID：<%=rtn%>
</div>
<%
} else {
%>
<div>error</div>
<%}%>

<br><br>

<form action="/activitytest" method="post">
    玩家建团createTeam<br>
    用户名：<input type="text" name="createTeam.username">&nbsp;&nbsp;&nbsp;&nbsp;
    游戏id：<input type="text" name="createTeam.gameid">&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit"><br>
</form>

<%
    String createTeam_username = request.getParameter("createTeam.username");
    String createTeam_gameid = request.getParameter("createTeam.gameid");
    if (StringUtils.isNotBlank(createTeam_username) && StringUtils.isNumeric(createTeam_gameid)) {
        hdUser = new HdUser();
        hdUser.setUsername(createTeam_username);
        hdUser.setGameid(Integer.parseInt(createTeam_gameid));
        CreateTeamRet createTeamRet = activityServiceInterface.createTeam(hdUser);
%>
<div>
    建团成功（0=成功；1=已有团；2=账号不存在；null=参数错误）：<%=createTeamRet == null ? "null" : createTeamRet.retcode%>
</div>
<%
} else {
%>
<div>error</div>
<%}%>


<br><br>

<form action="/activitytest" method="post">
    玩家入团joinTeam<br>
    用户名：<input type="text" name="joinTeam.username">&nbsp;&nbsp;&nbsp;&nbsp;
    团队id：<input type="text" name="joinTeam.termid">&nbsp;&nbsp;&nbsp;&nbsp;
    游戏id：<input type="text" name="joinTeam.gameid">&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit"><br>
</form>

<%
    String joinTeam_username = request.getParameter("joinTeam.username");
    String joinTeam_termid = request.getParameter("joinTeam.termid");
    String joinTeam_gameid = request.getParameter("joinTeam.gameid");
    if (StringUtils.isNotBlank(joinTeam_username) && StringUtils.isNumeric(joinTeam_gameid) && StringUtils.isNumeric(joinTeam_termid)) {
        hdUser = new HdUser();
        hdUser.setUsername(joinTeam_username);
        hdUser.setGameid(Integer.parseInt(joinTeam_gameid));
        hdUser.setTermid(joinTeam_termid);
        Integer rtn = activityServiceInterface.joinTeam(hdUser);
%>
<div>
    玩家入团（0=成功, 1=该团不存在, 2=该团已满，入团失败 3=该用户已有团，入团失败 4=账号不存在, null=参数错误）：<%=rtn%>
</div>
<%
} else {
%>
<div>error</div>
<%}%>


<br><br>

<form action="/activitytest" method="post">
    根据团队ID查询团队信息getTeamInfoByTeamID<br>
    团队id：<input type="text" name="getTeamInfoByTeamID.termid">&nbsp;&nbsp;&nbsp;&nbsp;
</form>

<%
    String getTeamInfoByTeamID_termid = request.getParameter("getTeamInfoByTeamID.termid");
    if (StringUtils.isNumeric(getTeamInfoByTeamID_termid)) {
        hdUser = new HdUser();
        hdUser.setTermid(getTeamInfoByTeamID_termid);
        TeamInfo teamInfo = activityServiceInterface.getTeamInfoByTeamID(hdUser);
%>
<div>
    团队信息：<%=teamInfo == null ? "null" : "建团时间：" + teamInfo.creatime + "-团队等级：" + teamInfo.level + "-团队成员数：" + teamInfo.membernum%>
</div>
<%
} else {
%>
<div>error</div>
<%}%>


<br><br>

<form action="/activitytest" method="post">
    根据用户名和游戏ID查询该用户所在团队的信息getTeamInfoByNameAndAid<br>
    用户名：<input type="text" name="getTeamInfoByNameAndAid.username">&nbsp;&nbsp;&nbsp;&nbsp;
    游戏id：<input type="text" name="getTeamInfoByNameAndAid.gameid">&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit"><br>
</form>

<%
    String getTeamInfoByNameAndAid_username = request.getParameter("getTeamInfoByNameAndAid.username");
    String getTeamInfoByNameAndAid_gameid = request.getParameter("getTeamInfoByNameAndAid.gameid");
    if (StringUtils.isNotBlank(getTeamInfoByNameAndAid_username) && StringUtils.isNumeric(getTeamInfoByNameAndAid_gameid)) {
        hdUser = new HdUser();
        hdUser.setUsername(getTeamInfoByNameAndAid_username);
        hdUser.setGameid(Integer.parseInt(getTeamInfoByNameAndAid_gameid));
        TeamInfo teamInfo = activityServiceInterface.getTeamInfoByNameAndAid(hdUser);
%>
<div>
    团队信息：<%=teamInfo == null ? "null" : "建团时间：" + teamInfo.creatime + "-团队等级：" + teamInfo.level + "-团队成员数：" + teamInfo.membernum%>
</div>
<%
} else {
%>
<div>error</div>
<%}%>


<br><br>

<form action="/activitytest" method="post">
    根据团队ID查询团队成员信息getTeamMemberInfoByTeamID<br>
    团队id：<input type="text" name="getTeamMemberInfoByTeamID.termid">&nbsp;&nbsp;&nbsp;&nbsp;
</form>

<%
    String getTeamMemberInfoByTeamID_termid = request.getParameter("getTeamMemberInfoByTeamID.termid");
    if (StringUtils.isNumeric(getTeamMemberInfoByTeamID_termid)) {
        hdUser = new HdUser();
        hdUser.setTermid(getTeamMemberInfoByTeamID_termid);
        Vector vector = activityServiceInterface.getTeamMemberInfoByTeamID(hdUser);
%>
<div>
    团员信息：<%
    if (vector != null) {
        for (Object object : vector) {
            TeamMemberInfo teamMemberInfo = (TeamMemberInfo) object;
            response.getWriter().println("入团时间：" + teamMemberInfo.jointime + "-用户名：" + teamMemberInfo.username + "-用户id：" + teamMemberInfo.userid + "-游戏id：" + teamMemberInfo.aid + "-团队id：" + teamMemberInfo.teamid);
        }
    } else {
        response.getWriter().println("null");
    }
%>
</div>
<%
} else {
%>
<div>error</div>
<%}%>


<br><br>

<form action="/activitytest" method="post">
    包月exchangeMonth2<br>
    用户名：<input type="text" name="exchangeMonth2.username">&nbsp;&nbsp;&nbsp;&nbsp;
    用户名ID：<input type="text" name="exchangeMonth2.userid">&nbsp;&nbsp;&nbsp;&nbsp;
    服务器ID：<input type="text" name="exchangeMonth2.server">&nbsp;&nbsp;&nbsp;&nbsp;
    游戏id：<input type="text" name="exchangeMonth2.aid">&nbsp;&nbsp;&nbsp;&nbsp;
    密钥串keynumber：<input type="text" name="exchangeMonth2.keynumber">&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit"><br>
</form>

<%
    String exchangeMonth2_username = request.getParameter("exchangeMonth2.username");
    String exchangeMonth2_userid = request.getParameter("exchangeMonth2.userid");
    String exchangeMonth2_server = request.getParameter("exchangeMonth2.server");
    String exchangeMonth2_aid = request.getParameter("exchangeMonth2.aid");
    String exchangeMonth2_keynumber = request.getParameter("exchangeMonth2.keynumber");
    if (StringUtils.isNotBlank(exchangeMonth2_username) && StringUtils.isNumeric(exchangeMonth2_userid) && StringUtils.isNumeric(exchangeMonth2_server) && StringUtils.isNumeric(exchangeMonth2_aid) && StringUtils.isNotBlank(exchangeMonth2_keynumber)) {
        hdUser = new HdUser();
        hdUser.setUsername(exchangeMonth2_username);
        hdUser.setGameid(Integer.parseInt(exchangeMonth2_aid));
        hdUser.setServer(Integer.parseInt(exchangeMonth2_server));
        hdUser.setUserid(exchangeMonth2_userid);
        hdUser.setIp("127.0.0.1");
        String rtn = monthlyServiceInterface.month(hdUser, exchangeMonth2_keynumber);
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


<form action="/activitytest" method="post">
    包周exchangeWeek2<br>
    用户名：<input type="text" name="exchangeWeek2.username">&nbsp;&nbsp;&nbsp;&nbsp;
    用户名ID：<input type="text" name="exchangeWeek2.userid">&nbsp;&nbsp;&nbsp;&nbsp;
    服务器ID：<input type="text" name="exchangeWeek2.server">&nbsp;&nbsp;&nbsp;&nbsp;
    游戏id：<input type="text" name="exchangeWeek2.aid">&nbsp;&nbsp;&nbsp;&nbsp;
    密钥串keynumber：<input type="text" name="exchangeWeek2.keynumber">&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit"><br>
</form>

<%
    String exchangeWeek2_username = request.getParameter("exchangeWeek2.username");
    String exchangeWeek2_userid = request.getParameter("exchangeWeek2.userid");
    String exchangeWeek2_server = request.getParameter("exchangeWeek2.server");
    String exchangeWeek2_aid = request.getParameter("exchangeWeek2.aid");
    String exchangeWeek2_keynumber = request.getParameter("exchangeWeek2.keynumber");
    if (StringUtils.isNotBlank(exchangeWeek2_username) && StringUtils.isNumeric(exchangeWeek2_userid) && StringUtils.isNumeric(exchangeWeek2_server) && StringUtils.isNumeric(exchangeWeek2_aid) && StringUtils.isNotBlank(exchangeWeek2_keynumber)) {
        hdUser = new HdUser();
        hdUser.setUsername(exchangeWeek2_username);
        hdUser.setGameid(Integer.parseInt(exchangeWeek2_aid));
        hdUser.setServer(Integer.parseInt(exchangeWeek2_server));
        hdUser.setUserid(exchangeWeek2_userid);
        hdUser.setIp("127.0.0.1");
        String rtn = weeklyServiceInterface.week(hdUser, exchangeWeek2_keynumber);
%>
<div>
    返回：<%=rtn%>
</div>
<%
} else {
%>
<div>error</div>
<%}%>


<%
    }
%>
</body>
</html>