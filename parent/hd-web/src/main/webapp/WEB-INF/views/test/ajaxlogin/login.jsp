<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 14-5-26
  Time: 上午11:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>登陆demo</title>

    <script src="http://api.wanmei.com/js/jquery-1.9.1.min.js"></script>
    <script src="http://api.wanmei.com/js/auth-js.js"></script>
    <script src="http://api.wanmei.com/js/loginkey.vbs" type="text/vbscript"></script>
    <script language="javascript">
        $(document).ready(

                function () {

                    WEBGame.beforeLogin(

                            function (data) {
                                if (data.error_code != "0") {
                                    alert(data.error_description);
                                    return;
                                }
                                //success({"error_code":0,"error_description":"success","challenge":"RZE2","type":"text"})
                                if (data.type == 'text') {

                                    $("#login_challenge").attr("value", data.challenge);
                                } else {

                                    $("#showchallenge").attr("style", "display:\"\"");
                                    $("#img_challenge_path").attr("value", data.challenge);

                                    $("#randimg").attr("src", data.challenge);
                                }
                            }

                    );


                    $("#quick_login_btn").bind("click", function () {
                        $("#logintype").attr("value", "normal");
                        WEBGame.login(

                                function (data) {

                                    if (data.error_code == "6") {//绑定密保卡或手机令牌需要进一步验证
                                        $("#logintype").attr("value", data.logintype);
                                        if (data.logintype == "lingpai") {//绑定令牌
                                            $("#div_lingpai").attr("style", "display:\"\"");
                                        } else if (data.logintype == "matrix") {//绑定密宝卡
                                            var coordinates = data.coordinates.split("|");
                                            $("#coor0").html(coordinates[0]);
                                            $("#coor1").html(coordinates[1]);
                                            $("#coor2").html(coordinates[2]);
                                            $("#div_matrix").attr("style", "display:\"\"");
                                        }
                                    } else {
//                                        alert('登陆结果,' + data.error_code + "," + data.error_description + ",token=" + data.token + ",expiration=" + data.expiration);
                                        if (data.error_code == '0') {
//                                            $("#token").attr("value", data.token);
//                                            $("#verifytoken").attr("style", "display:\"\"");
                                            var user=$("#login_username").val().trim();
                                            verifyToken(user,data.token);
                                        } else {
                                            $("#errorMessage").html(data.error_description)
                                        }
                                    }
                                }

                        );

                    });

                    function  verifyToken(username,token){
                         var url="${ctx}/test/ajaxlogin/check";
                        $.get(url, {username: username, token:token,r: Math.random()}, function (data) {
                            if(data.success){
                                $("#quickLoginForm").hide();
                                $("#user").html(username);
                                $("#loginInfo").show();
                            }
                        },"json");
                    }

                    $("#mb_login_btn").bind("click", function () {
                        $("#logintype").attr("value", "matrix");
                        WEBGame.login(

                                function (data) {


                                    alert('登陆结果,' + data.error_code + "," + data.error_description + ",token=" + data.token + ",expiration=" + data.expiration);
                                    if (data.error_code == '0') {
//                                        $("#token").attr("value", data.token);
//                                        $("#verifytoken").attr("style", "display:\"\"");
                                        var user=$("#login_username").val().trim();
                                        verifyToken(user,data.token);
                                    }
                                }

                        );

                    });
                    $("#lingpai_login_btn").bind("click", function () {
                        $("#logintype").attr("value", "lingpai");
                        WEBGame.login(

                                function (data) {


                                    alert('登陆结果,' + data.error_code + "," + data.error_description + ",token=" + data.token + ",expiration=" + data.expiration);
                                    if (data.error_code == '0') {
//                                        $("#token").attr("value", data.token);
//                                        $("#verifytoken").attr("style", "display:\"\"");

                                        var user=$("#login_username").val().trim();
                                        verifyToken(user,data.token);
                                    }
                                }

                        );

                    });
                    $("#key_login_btn").bind("click", function () {

                        WEBGame.keylogin(

                                function (data) {
                                    alert('key登陆结果,' + data.error_code + "," + data.error_description + ",token=" + data.token + ",userid=" + data.userid);
                                    if (data.error_code == '0') {
//                                        $("#token").attr("value", data.token);
//                                        $("#verifytoken").attr("style", "display:\"\"");

                                        var user=$("#login_username").val().trim();
                                        verifyToken(user,data.token);
                                    }
                                }

                        );

                    });


                    $("#img_refresh_btn").bind("click", function () {

                        $("#randimg").attr("src", $("#img_challenge_path").val() + "&rr=" + Math.random());

                    })


                }


        );

        var username='${sessionScope.USER.account}';
        function init(){
            if(isEmpty(username)){
               $("#quickLoginForm").show();
               $("#loginInfo").hide();
            }else{
                $("#quickLoginForm").hide();
                $("#loginInfo").show();
            }
        }

        $(function(){
           init();
        });

            var isEmpty = function (arg) {
                return arg == "undefined" || arg == null || arg == "" || arg == "null";
            };


    </script>
</head>
<body>
<div id="activeid"></div>
<embed height="0" width="0" id="pluginobj2" type="application/pwrd-usbkey"></embed>
<br/><br/>
<div id="loginInfo">
        <span id="user">${sessionScope.USER.account}</span>，您好！
    </div>

<form id="quickLoginForm" >
    <input type="hidden" name="appid" id="appid" value="30005"/>
    <input type="hidden" name="clientsecret" id="clientsecret" value="fc8ea6f6215563834a6ad3236421c499"/>
    <input type="hidden" id="img_challenge_path" name="img_challenge_path"/>

    <input type="hidden" name="logintype" id="logintype" value="normal"/>

    <div id="errorMessage">
    </div>

    <li>
        输入账号

        <input id="login_username" name="login_username" type="text"/>

    </li>
    <li>
        输入密码
        <input id="login_password" name="login_password" type="password"/>
    </li>


    <li class="li02" id="showchallenge" style="display:none">
        验证码 <input type="text" id="login_challenge" name="login_challenge" maxlength=4/>

        <img id="randimg" name="randimg" src="" width="60" height="25" align="absmiddle"/>
        </p>
        <p class="p12">
            <em>&nbsp;&nbsp;</em><input type="button" id="img_refresh_btn" name="img_refresh_btn" value="换一张"/>
            <em>看不清？&nbsp;</em>
    </li>
    <li class="li03">
        <input id="quick_login_btn" type="button" value="登陆"/>
        <a id="toRegisterUl" href="javascript:void(0);"></a>

        <input id="key_login_btn" type="button" value="usbkey登陆"/>
        <a id="toRegisterUl" href="javascript:void(0);"></a>
    </li>


    <div id="div_lingpai" name="div_lingpai" style="display:none">

        <li>您绑定了手机令牌，请输入动态密码：</li>
        <li>
            <label>动态密码</label><input type="text" name="mobile_token" id="mobile_token" style="width: 110"
                                      maxlength="6"/>
        </li>
        <li>
            <input type="button" id="lingpai_login_btn" value="令牌登录"/>
        <li>
    </div>


    <div id="div_matrix" name="div_matrix" style="display:none">

        <li>您绑定了密保卡，请输入密保卡坐标：</li>
        <li>
            <label>动态位置</label> <span id="coor0"> </span> <span
                id="coor1"> </span> <span id="coor2"> </span>
        </li>
        <li>
            <label>动态数字</label>
            <span><input type="text" name="coor0_value" id="coor0_value" maxlength=2/> -</span>
            <span><input type="text" name="coor1_value" id="coor1_value" maxlength=2/> -</span>
            <span><input type="text" name="coor2_value" id="coor2_value" maxlength=2/> </span>
        </li>
        <li>
            <input type="button" id="mb_login_btn" value="密保卡登录"/>
        </li>
    </div>
</form>
<br/> <br/>

<div id="verifytoken" style="display:none">
    <form id="regForm" method="post" action="${ctx}/test/ajaxlogin/check">
        <li>登录时用户名 <input type="text" id="username" name="username"/> 登陆成功后获取的token值：<input type="text" id="token"
                                                                                            name="token"/></li>
        <li>
            <input type="submit" value="提交到本服务器验证token"/>
        </li>
    </form>
</div>
</body>
</html>