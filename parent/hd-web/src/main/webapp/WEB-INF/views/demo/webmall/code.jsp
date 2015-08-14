<%--
  Created by IntelliJ IDEA.
  User: ssw
  Date: 2015/7/2
  Time: 17:39
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="author" content="Design:; Web Layout:liangweiming;"/>
    <meta name="keywords"
          content="hex,HEX,集换英雄,hex官网,hex激活码,什么是hex,hex下载,hex官方网站,hex卡牌,HEX卡组,hex论坛,集换卡牌,卡牌游戏,卡牌网游,完美世界,休闲游戏,免费游戏,hexipad版"/>
    <meta name="description"
          content="欢迎来到hex官网，hex集换英雄是首款免费在线集换式卡牌游戏，由完美世界代理，灵活的卡牌交易与卡组构筑，在hex官方网站领取hex激活码，带你走进hex卡牌游戏新世界。"/>
    <link href="${ctx}/static/styles/boxy.css" type="text/css" rel="stylesheet">
    <link href="http://event51.wanmei.com/hex/201507/moshi/style/master.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="http://www.wanmei.com/public/js/wm.js" top="false" bottom="false"></script>
    <script type="text/javascript" src="http://www.wanmei.com/public/js/jq_171.js"></script>
    <title>骇星魔石商城 -《HEX》官方网站</title>
</head>
<body>
<div class="bg">
    <!--left-->
    <div class="left">
        <div class="wm-public" logo="{name:'hex',skin:2,top:20,left:-5,zindex:500}" title="HEX官方网站">HEX官方网站</div>
        <!--left_nav-->
        <div class="left_nav">
            <a id="nav1" href="${ctx}/hex/webmall/index"></a>
            <a id="nav2" class="on" href="${ctx}/hex/webmall/toexchange"></a>
            <a id="nav3" href="javascript:alert('敬请期待！')"></a>
            <a id="nav4" href="javascript:void(0);" ></a>
            <a id="nav5" href="http://hex.wanmei.com/" target="_blank"></a>
        </div>
    </div>
    <!--right-->
    <div class="right">
        <div class="title"><img src="http://event51.wanmei.com/hex/201507/moshi/images/title01.png"/></div>
        <!--login or logout-->
        <!--login or logout-->
        <div class="loginbox">
            <c:choose>
                <c:when test="${empty sessionScope.USER.account}">
                    <p class="loginbtn"><a href="javascript:void(0)" id="login">登录</a></p>
                </c:when>
                <c:otherwise>
                    <p class="logoutbtn">欢迎您，<span
                            class="username">${sessionScope.USER.account}</span>当前魔石数量：<span>${stoneCount}</span>个<a
                            href="javascript:void(0)" id="logout">【退出】</a></p>
                </c:otherwise>
            </c:choose>
        </div>
        <!--clock-->
        <div class="clockbox">
            <div class="clocktime"><strong>整点抢号</strong>距下一次抢号 还剩：</div>
            <ul class="sheng_time" id="time" endtime="${endtime}">
                <li><p>Hours</p><span class="hours"></span></li>
                <li><p>Minutes</p><span class="minuters"></span></li>
                <li><p>Seconds</p><span class="seconds"></span></li>
            </ul>
        </div>
        <!--立即兑换-->
        <div class="exchange"><a href="javascript:void(0)" id="exchange">立即兑换</a>兑换所需魔石：100</div>
        <!--抢码须知-->
        <div class="instru">
            <h1>抢码须知</h1>

            <p>1：从7月1日起，每天09:00开-21:00整点开启魔石兑换激活码活动，数量有限，兑完即止。</p>

            <p>2：兑换激活码需要100个魔石，兑换失败魔石退回原账户。</p>

            <p>3：活动开启后，只有拥有足够魔石的用户方可兑换不删档测试激活码，并扣除相应魔石（未成功兑换不扣除）。</p>

            <p>4：每个账号只能成功兑换一个HEX封测激活码。</p>

            <p>5：HEX项目组有权对非正常参与活动的账号作出取消测试资格的处理。</p>
        </div>
        <p class="gonlink">做任务获取更多魔石<a href="/hex/webmall/index" target="_blank"> 【立即前往】</a></p>
        <c:choose>
            <c:when test="${not empty myCode}">
                <div class="jihuocode">
                    <p><b>您获得的激活码是：</b><span id="inviteCode">${myCode}</span><a href="javascript:void(0)">复制</a><input
                            type="button" class="wwlp_btn" id="copy01" value="复制"/></p>
                </div>
            </c:when>
        </c:choose>


    </div>

</div>
<div class="footer">
    <iframe src="http://hex.wanmei.com/footer.html" width="100%" height="135" frameborder="0" scrolling="no"
            allowtransparency="true"></iframe>
</div>
<script type="text/javascript" src="http://www.wanmei.com/public/js/jq_171.js"></script>
<script type="text/javascript" src="http://www.wanmei.com/public/js/modules/boxy/jquery.boxy.js"></script>
<script type="text/javascript" src="${ctx}/extend/wmpassport2/wanmei.passport.js"></script>
<script type="text/javascript" src="${ctx}/static/event/hex/webmall/js/mycountday.js"></script>
<script src="http://www.wanmei.com/public/js/swfobject.js "></script>
<script type="text/javascript">
    //加入复制按钮
    addCopyButton('copy01', 42, 35, 'setClipboard01');
    //复制按钮回调函数
    function setClipboard01() {
        var _string = document.getElementById("inviteCode").innerHTML;
        alert("该激活码已经复制到剪贴板！");
        return _string;
    }
</script>

<script type="text/javascript">

    var username = "${sessionScope.USER.account}";
    var isEmpty = function (arg) {
        return arg == "undefined" || arg == null || arg == "" || arg == "null";
    };

    $(function () {
        initLoginLogout();
        $("#exchange").click(function () {
            exchangeProcess();
        });

        $("#nav4").click(function(){
            if(isEmpty(username)){
                login();
                return false;
            }
            window.location.href="${ctx}/hex/webmall/mycenter";
        });

    });

    function login() {
        wanmei.passport.islogin({session: 'USER', _false: function () {
            wanmei.passport.login({session: 'USER', hdid: 'hex_webmall_status'});
        }});
    }
    function logout() {
        wanmei.passport.logout({session: 'USER'})
    }
    function initLoginLogout() {
        $('#login').click(function () {
            login();
            return false;
        });
        $('#logout').click(function () {
            logout();
            return false;
        });
    }


    var exchangeProcess = function () {
        if (isEmpty(username)) {
            login();
            return;
        }
        var isSignToday = '${isSignToday}';
        if (isSignToday == 1) {
            $.simpleAlert("今天您已经签到了哦！");
            return;
        }
        var url = "/hex/webmall/exchange";
        $.getJSON(url, {t: Math.random()}, function (json) {
            if (json.success) {
                $("#inviteCode").html(json.message);
            }
            $.simpleAlert(json.message);
        });
        return false;
    }
</script>


</body>
</html>

