<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.david.web.pppppp.common.TempDBManager"%>

<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%--
  Created by IntelliJ IDEA.
  User: lly
  Date: 2007-7-24
  Time: 13:38:56
  完美时空 游戏活动管理平台
  作者：李亮阳
--%>
<html>
<head><title>Simple jsp page</title></head>


<body>

*********this is test page!********
<form action="<%=request.getContextPath()%>/test/dbtest.jsp" method="post">
    <input type="text" size="20" name="hdname"><input type="submit" value="send">
</form>
<hr>
数据库连接测试：
<br>
<%
    List dbnames=null;
    if(dbnames==null) dbnames=new ArrayList();

    String s="未连接到数据库<br>";
    String hdname=request.getParameter("hdname");
    out.print("hdname:"+hdname+"<br>");
    if(hdname!=null&&hdname.trim().length()>0){
        dbnames.add(hdname);
        try {
            s= TempDBManager.getTestList(hdname);
        } catch (Exception e) {
            System.out.println(e.getMessage());
             out.print("数据源异常");
        }
    }
    out.print(s);
    out.print("<hr>");
    out.print("已经测试做的数据库：<br>");
    out.print(dbnames);
%>
</body>
</html>
