<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 14-4-17
  Time: 下午2:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
<title>JS完整获取IE浏览器信息</title>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<script language="Javascript">
//输出浏览器信息
document.write("浏览器名称:"+navigator.appName+"<br />");
document.write("版本号:"+navigator.appVersion+"<br />");
document.write("代码名字:"+navigator.appCodeName+"<br />");
document.write("用户代理标识:"+navigator.userAgent);
</script>
<table width="435" border="1" cellspacing="3" cellpadding="0">
  <tr>
    <td>浏览器类型</td>
    <td>
      <script>document.write(navigator.appName)</script>
    </td>
  </tr>
  <tr>
    <td >浏览器版本</td>
    <td >
      <script>document.write(navigator.appVersion)</script>
    </td>
  </tr>
  <tr>
    <td>浏览器语言</td>
    <td>
      <script>document.write(navigator.browserLanguage)</script>
    </td>
  </tr>
  <tr>
    <td >CPU类型</td>
    <td >
      <script>document.write(navigator.cpuClass)</script>
    </td>
  </tr>
  <tr>
    <td>操作系统</td>
    <td>
      <script>document.write(navigator.platform)</script>
    </td>
  </tr>
  <tr>
    <td >系统语言</td>
    <td >
      <script>document.write(navigator.systemLanguage)</script>
    </td>
  </tr>
  <tr>
    <td>用户语言;</td>
    <td>
      <script>document.write(navigator.userLanguage)</script>
    </td>
  </tr>
  <tr>
    <td >在线情况</td>
    <td >
      <script>document.write(navigator.onLine)</script>
    </td>
  </tr>
  <tr>
    <td>屏幕分辨率</td>
    <td>      <script>document.write(window.screen.width+"x"+window.screen.height)</script></td>
  </tr>
  <tr>
    <td>颜色</td>
    <td> <script>document.write(window.screen.colorDepth+"位")</script></td>
  </tr>
  <tr>
    <td>字体平滑</td>
    <td><script>document.write(window.screen.fontSmoothingEnabled)</script></td>
  </tr>
  <tr>
    <td>appMinorVersion</td>
    <td>
      <script>document.write(navigator.appMinorVersion)</script>
    </td>
  </tr>
  <tr>
    <td >appCodeName</td>
    <td >
      <script>document.write(navigator.appCodeName)</script>
    </td>
  </tr>
  <tr>
    <td>cookieEnabled</td>
    <td>
      <script>document.write(navigator.cookieEnabled)</script>
    </td>
  </tr>
  <tr>
    <td >userAgent</td>
    <td >
      <script>document.write(navigator.userAgent)</script>
    </td>
  </tr>
  <tr>
    <td>javaEnabled</td>
    <td>
      <script>document.write(navigator.javaEnabled())</script>
    </td>
  </tr>
  <tr>
    <td >taintEnabled</td>
    <td >
      <script>document.write(navigator.taintEnabled())</script>
    </td>
  </tr>
</table>
</body>
</html>
