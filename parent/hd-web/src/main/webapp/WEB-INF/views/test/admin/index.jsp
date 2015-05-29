<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 13-9-17
  Time: 下午3:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

</head>
<body>

<a href="/test/wmlogin">登录</a>     <br>    <br>
<a href="/test/wushaobintest.jsp">tomcat测试</a>      <br>   <br>
<a href="/activitytest">au接口测试</a>       <br>  <br>
<a href="/173test">173接口测试</a>         <br><br>
<a href="/shortmsgtest">短信测试</a>      <br>   <br>
<a href="/protocoltest">协议测试</a>      <br>   <br>
<a href="/test/database/write">写数据库</a>       <br>  <br>
<a href="/test/database/read">读数据库</a>       <br>  <br>
<a href="/test/database/img">odi数据库(img)</a>    <br>     <br>
<a href="/example/memcached">memcached</a>      <br>   <br>
<a href="/example/msm">memcachedForSession</a>      <br>   <br>
<a href="/example/submit/page">提交页</a>      <br>   <br>
<a href="/example/ajaxpage">ajax分页</a>      <br>   <br>
<a href="http://img.event.wanmei.com/control/prize/page">发奖开关</a>   <br>     <br>
<a href="/test/hdswitch">活动开关</a>        <br>      <br>




</body>
<script type="text/javascript" src="${ctx}/static/jquery/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/extend/wmpassport2/wanmei.passport.js"></script>

</html>