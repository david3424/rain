<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<%@page import="com.david.web.pppppp.wmeovg.request.service.IPrizeService"%>
<%@page import="com.david.web.pppppp.wmeovg.request.service.PrizeServiceManager"%>
<%@page import="com.david.web.pppppp.wmeovg.request.util.Priority"%>
<%@page import="java.util.Random"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="send.jsp" method="post">
<table>
	<tr>
		<td>应用标识</td>
		<td><input type='text' name='appid' value="app_1" /></td>
	</tr>
	<tr>
		<td>所在服务器</td>
		<td><input type='text' name='zoneid' value="100" /></td>
	</tr>
	<tr>
		<td>账号</td>
		<td><input type='text' name='account' value="test" /></td>
	</tr>
	<tr>
		<td>角色名</td>
		<td><input type='text' name='rolename' value="roletest" /></td>
	</tr>
	<tr>
		<td>奖品ID</td>
		<td><input type='text' name='prizeid' value="201" /></td>
	</tr>
	<tr>
		<td>发送个数</td>
		<td><input type='text' name='count' value="1" /></td>
	</tr>
	<tr>
		<td>附加参数</td>
		<td><input type='text' name='callback'
			value="table='cb_new_user'&id=200&note='赤壁活动表'" size="80" /></td>
	</tr>
	<tr>
		<td>发送优先级</td>
		<td><select name="priority">
			<option value="1">普通</option>
			<option value="2">较快</option>
			<option value="3">最高</option>
		</select></td>
	</tr>
	<tr>
		<td><input type="submit" value="提交" /></td>
	</tr>
</table>
</form>
<%
	request.setCharacterEncoding("UTF-8");

	String appid = request.getParameter("appid");
	String zid = request.getParameter("zoneid");
	String account = request.getParameter("account");
	String rolename = request.getParameter("rolename");
	String pid = request.getParameter("prizeid");
	String c = request.getParameter("count");
	String callback = request.getParameter("callback");
	String pri = request.getParameter("priority");

	IPrizeService prizeService = PrizeServiceManager.prizeService;

	if (pid != null) {
		Integer zoneid = new Integer(zid);
		Integer prizeid = new Integer(pid);
		Integer count = new Integer(c);
		Priority priority;
		if (pri.equals("1"))
			priority = Priority.NORMAL;
		else if (pri.equals("2"))
			priority = Priority.MEDIUM;
		else
			priority = Priority.FAST;

		//System.out.println("********" + callback);
		 /*int t = 10000;
		while(t-->0){
			Integer[] prizes = {33,201};
			Random r = new Random();
			prizeid = prizes[r.nextInt(2)];
			String gid = prizeService.genGid(); // 客户端生成流水号
			prizeService.send(appid, gid, zoneid, account, rolename,
					prizeid, count, priority, callback);
		}*/
		 
		
		String gid = prizeService.genGid(); // 客户端生成流水号
		int rtn = prizeService.send(appid, gid, zoneid, account,
				rolename,  prizeid, count, priority,callback);
		

		out.write("奖品兑换请求响应状态码：" + rtn);
	}
%>
</body>
</html>