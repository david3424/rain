<%--
  Created by IntelliJ IDEA.
  Date: 13-10-29
  Time: 下午4:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<div>
    <h3>服务器列表：serverlist测试</h3>

    <p>
        <label for="server">服务器ID</label>
        <input id="server"/>
        <label for="servername">服务器名</label>
        <input id="servername"/>
        <select id="serverlist"></select>
    </p>
</div>

<script type="text/javascript" src="/static/jquery/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="/static/jquery/event/serverlist.js"></script>

<script type="text/javascript">
    $(function () {
        $("#serverlist").serverlist({
            gamename: "zhuxian2"
        });
    });
</script>
</body>
</html>