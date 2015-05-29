<%@ page import="com.david.web.wanmei.util.hdswitch.HdSwitchUtil" %>
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


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="Pragma" content="no-cache" />
<meta name="viewport" content="width=452;initial-scale=1;maximum-scale=1;minimum-scale=0.5;user-scalable=yes"/>
<%--<meta name="viewport" content="width=device-width;initial-scale=1"/>--%>
<title>完美世界通行证</title>

<link href="http://event20.wanmei.com/extend/wmpassport2/css/iframecss.css" rel="stylesheet" type="text/css" />

<script src="http://passport.wanmei.com/sso/script/jquery.js" type="text/javascript"></script>
<script src="http://passport.wanmei.com/sso/script/md5.js"></script>
<script src="http://passport.wanmei.com/sso/script/common.js"></script>
<script src="http://passport.wanmei.com/sso/script/loginkey.vbs" type="text/vbscript"></script>

</head>
<body>
	<div class="hidden">

			<embed height="0" width="0" id="pluginobj2"
				type="application/pwrd-usbkey"></embed>
		
	</div>

	<form id="form1" name="form1" method="post"
		action="/sso/accounts/serviceLogin">

		<input id="randtoken" name="randtoken" type="hidden" value="82F3CB321538142C4AB19AB99F447ECF" />
		<input name="continue" type="hidden" value="http://event20.wanmei.com/SSOServerLogin" /> <input
			name="service" type="hidden" value="activity" /> <input
			name="location" type="hidden" value="2f6a73702f73736f6c6f67696e2e6a7370" /> <input
			type="hidden" name="macval" id="macval" value="" /> <input
			type="hidden" name="needRand" value="0" id="needRand" /> <input
			name="isiframe" type="hidden" value="1" /> <input
			name="logintype" id="logintype" type="hidden" value="" />

		<input type="hidden" name="CSSStyle" value="http://event20.wanmei.com/extend/wmpassport2/css/iframecss.css"
			id="CSSStyle" />

		<div class="enteriframe" id="enteriframe"
			style="position: relative; zoom: 1">
			<dl>
				<dt class="top"></dt>
				<dt class="title">若您已拥有完美通行证，请先登录。</dt>
				<dt class="login_ts" style="position:absolute;top:40px;left:128px;color:red;font-size:12px;"></dt>
				<dd>
					<p>
						<b>通行证账号</b><input id="username" name="username" type="text"
							size="15" tabindex="1" maxlength="50" value="" />
					</p>
					<p>
						<b>通行证密码</b><input id="password" name="passwd" type="password"
							size="15" tabindex="2" maxlength="16" value="" />  <a target="_blank"
							href="http://passport.wanmei.com/jsp/member/password.jsp">忘记密码？</a>
					</p>

					
						
						
							<input type="hidden" name="rand" id="rand" value="WXDY" />
						
					
					<p>
						<input type="button" class="btn_01" value="登录"
							onclick="login(event);" /> <input name="" type="button"
							class="btn_01 btn_right" value="注册" onclick="toregister();" />
					</p>
					<center>
						<input type="button" class="btn_02" value="完美神盾 登录"
							onclick="keylogin();" />
					</center>

				</dd>
				<dt class="bot"></dt>
			</dl>
		</div>

		


	</form>


</body>
</html>