<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
    <title>梦幻诛仙充值促销活动</title>
    <link href="${ctx}/static/styles/boxy.css" type="text/css" rel="stylesheet">
</head>

<body>
<div style="text-align:left;font-size:large;font-weight:bolder;padding:20px">充值促销活动demo</div>

<c:choose>
    <c:when test="${empty sessionScope.USER.account}">
        <input type="button" value="登录"
               onclick="wanmei.passport.login({opacity:60,url:'${ctx}/test/rechargetest/login',session:'USER',hdid:'mhzx_rechargetest_prize'});">
    </c:when>
    <c:otherwise>
        <input type="button" value="退出"
               onclick="wanmei.passport.logout({opacity:60, session:'USER'});">
        <br><br>
        当前总积分：${tbScore} 已使用积分:${usedScore}
        剩余积分：${tbScore-usedScore}
    </c:otherwise>
</c:choose>

<br><br>
兑换奖品:
<table border="1">
    <tr>
        <th>奖品名称</th>
        <th>奖品描述</th>
        <th>兑换</th>
    </tr>

    <c:forEach items="${configPrizeList}" var="prize" varStatus="bean">
        <tr>
            <td>${prize.prizeName}</td>
            <td>${prize.prizeDesc}</td>
            <td><a href="javascript:void(0)"
                   pageId="${bean.index}"
                   prizeName="${prize.prizeName}"
                   score="${prize.score}"
                   accountRestrict="${prize.accountRestrict}">兑换</a></td>
        </tr>
    </c:forEach>
</table>
<br><br>
<a href="javascript:void(0)" onclick="view();return false;">查看已兑换奖品</a>
</body>
<script type="text/javascript" src="${ctx}/static/jquery/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/event/jquery.boxy.js"></script>
<script type="text/javascript" src="${ctx}/extend/wmpassport2/wanmei.passport.js"></script>
<script type="text/javascript">

    var exchange = function () {
        var $btn = $(this);
        var _id = $btn.attr("pageId");
        var _prizeName = $btn.attr("prizeName");
        var _score = $btn.attr("score");
        var _occupy = $btn.attr("occupy");

        var _accountRestrict = $btn.attr("accountRestrict");

        $btn.unbind("click");
        wanmei.passport.islogin({_false: function () {
            alert("请您登录完美通行证后参与活动。");
            wanmei.passport.login({opacity: 60, url: '${ctx}/test/rechargetest/login', session: 'USER',hdid:"mhzx_rechargetest_prize"});
        }, _true: function () {
            if (confirm("确定兑换奖品" + _prizeName + "(消耗积分" + _score + ")?")) {
                $.post('${ctx}/test/rechargetest/exchange', {id: _id}, function (data) {
                    $btn.click(exchange);
                    switch (data.status) {
                        case 1:
                            $.simpleAlert({
                                content: "兑换成功。",
                                ok: function () {
                                    location.reload();
                                }
                            });
                            break;
                        case -105:
                            $.simpleAlert("登录超时，请你重新登录。");
                            break;
                        case -102:
                            $.simpleAlert("活动还没开启。");
                            break;
                        case -103:
                            $.simpleAlert("活动已经结束，感谢您的关注。");
                            break;
                        case -104:
                            $.simpleAlert("活动维护中，请您稍后再来。");
                            break;
                        case -1:
                            $.simpleAlert("数据异常,请重试。");
                            break;
                        case -2:
                            $.simpleAlert("您的积分不足，请您充值。");
                            break;
                        case -3:
                            $.simpleAlert("对不起，您兑换【" + _prizeName + "】的数量已达本活动规则上限" + _accountRestrict + "个。");
                            break;
                        case -4:
                            $.simpleAlert("对不起，【" + _prizeName + "】礼包在每个服务领取数量有限制，请先领取未发送的礼包再兑换。");
                            break;
                        case -5:
                            $.simpleAlert("对不起，您没有达到领取礼包的积分。");
                            break;
                        default:
                            $.simpleAlert("兑换失败，请重试。");
                            break;
                    }
                });
            }
            else {
                $btn.click(exchange);
            }
        }, session: "USER"});

        return false;
    };

    $("[prizeName]").click(exchange);

    function view() {
        wanmei.passport.islogin({_false: function () {
            $.simpleAlert({
                content: "请您登录完美通行证后参与活动。",
                ok: function () {
                    wanmei.passport.login({opacity: 60,
                        url: '${ctx}/test/rechargetest/login', session: 'USER',hdid:"mhzx_rechargetest_prize"});
                }
            });
        }, _true: function () {
            location.href = "${ctx}/test/rechargetest/view";
        }, session: 'USER'
        });
    }


    var isEmpty = function (arg) {
        return arg == "undefined" || arg == null || arg == "" || arg == "null";
    };
</script>
<script type="text/javascript">
    var msg = "${msg}";
    var alertMsg = "";
    if (!isEmpty(msg)) {
        switch (parseInt(msg)) {
            case -102:
                alertMsg = "活动还没开启。";
                break;
            case -103:
                alertMsg = "活动已结束。谢谢您的参与。";
                break;
            case -104 :
                alertMsg = "对不起!活动维护中!暂时不能参与!";
                break;
            case -2:
                alertMsg = "您的账号无充值记录，请您充值后参与活动。";
                break;
            case -1:
                alertMsg = "数据异常,请重试！";
                break;
            case -105:
                alertMsg = "登录失败，请重试。";
                break;
        }
        alert(alertMsg);
        // 登出
        <%--$.simpleAlert({content: alertMsg, ok: function () {--%>
        <%--location.href = "${ctx}/extend/wmpassport2/jsp/ssologout.jsp?url=${ctx}/test/rechargetest/index";--%>
        <%--}});--%>
    }

</script>
</html>