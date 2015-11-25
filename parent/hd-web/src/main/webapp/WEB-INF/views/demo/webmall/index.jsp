<%--
  Created by IntelliJ IDEA.
  Date: 2015/7/1
  Time: 14:04
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <link href="${ctx}/static/styles/boxy.css" type="text/css" rel="stylesheet">
    <link href="${ctx}/static/styles/master.css" rel="stylesheet" type="text/css"/>
    <title>demo商城</title>
</head>
<body>
<div class="bg">
    <!--left-->
    <div class="left">
        <!--left_nav-->
        <div class="left_nav">
            <a id="nav1" class="on" href="${ctx}/demo/webmall/index"></a>
            <a id="nav2" href="${ctx}/demo/webmall/toexchange"></a>
            <a id="nav3" href="javascript:alert('敬请期待！')"></a>
            <a id="nav4" href="javascript:void (0);" ></a>
            <a id="nav5" href="#" target="_blank"></a>
        </div>
    </div>
    <!--right-->
    <div class="right">
        <div class="title"><img src="${ctx}/static/images/title01.png" alt="商城大标题"/></div>
        <!--login or logout-->
        <div class="loginbox">
            <c:choose>
                <c:when test="${empty sessionScope.USER.account}">
                    <p class="loginbtn"><a href="javascript:void(0)" id="login">登录</a></p>
                </c:when>
                <c:otherwise>
                    <p class="logoutbtn">欢迎您，<span
                            class="username">${sessionScope.USER.account}</span>当前积分数量：<span>${stoneCount}</span>个<a
                            href="javascript:void(0)" id="logout">【退出】</a></p>
                </c:otherwise>
            </c:choose>
        </div>
        <!--签到，个人信息，百科问答-->
        <div class="boxcon">
            <!--签到-->
            <div class="signIn">
                <div class="left_ll"><img src="http://event51.pppppp.com/hex/201507/moshi/images/l01.jpg"/></div>
                <div class="mid_ll">
                    <h1>每日签到</h1>
                    <p><strong>活动说明：</strong>点击进行每日签到，获取奖励。</p>
                    <p><strong>活动时间：</strong>xxx-xxx</p>
                </div>
                <div class="right_ll">
                    <a href="javascript:void(0)" id="signIn">
                        <p class="pp on">积分*10</p>
                        <p class="pp01 nowfaill" id="taskSign">立即完成</p>
                    </a>
                </div>
            </div>
            <!--个人信息-->
            <div class="weixin">
                <div class="left_ll"><img src="http://event51.pppppp.com/hex/201507/moshi/images/l02.jpg"/></div>
                <div class="mid_ll">
                    <h1>完善个人信息</h1>
                    <p><strong>活动说明：</strong>完善个人信息，获取奖励。</p>
                    <p><strong>活动时间：</strong>长期</p>
                </div>
                <div class="right_ll">
                    <a href="javascript:void(0);" id="toTaskInfo">
                        <p class="pp onli">积分*10</p>
                        <p class="pp01 nowfaill" id="taskInfo">立即完成</p>
                    </a>
                </div>
            </div>
            <!--百科问答-->
            <div class="question">
                <div class="left_ll"><img src="http://event51.pppppp.com/hex/201507/moshi/images/l04.jpg"/></div>
                <div class="mid_ll">
                    <h1>百科问答</h1>

                    <p><strong>活动说明：</strong>点击进行每日签到，获取相应奖励。</p>

                    <p><strong>活动时间：</strong>xxx</p>
                </div>
                <div class="right_ll">
                    <a href="javascript:void(0)" id="question">
                        <p class="pp onli">积分*10</p>
                        <p class="pp01 nowfaill" id="taskAnswer">立即完成</p>
                    </a>
                </div>
            </div>
        </div>
        <!--page-->
        <div class="page"></div>
        <!--page-->

    </div>

</div>
<div class="footer">
</div>
<!-- 签到 pop-->
<div class="pop1" id="pop1">
    <img src="http://event51.pppppp.com/hex/201507/moshi/images/close.jpg" class="closebtn01"/>

    <div class="title02">2015-09</div>
    <div class="weeknum">
        <span>星期日</span><span>星期一</span><span>星期二</span><span>星期三</span><span>星期四</span><span>星期五</span><span>星期六</span>
    </div>
    <div class="days">
        <span class="span01"></span>
        <span></span>
        <span></span>
        <span signin='2015-09-01' signincode="1">01</span>
        <span signin='2015-09-02' signincode="2">02</span>
        <span signin='2015-09-03' signincode="3">03</span>
        <span signin='2015-09-04' signincode="4" class="span01">04</span>

        <span signin='2015-09-05' signincode="5" class="span01">05</span>
        <span signin='2015-09-06' signincode="6">06</span>
        <span signin='2015-09-07' signincode="7">07</span>
        <span signin='2015-09-08' signincode="8">08</span>
        <span signin='2015-09-09' signincode="9">09</span>
        <span signin='2015-09-10' signincode="10">10</span>
        <span signin='2015-09-11' signincode="11" class="span01">11</span>

        <span signin='2015-09-12' signincode="12" class="span01">12</span>
        <span signin='2015-09-13' signincode="13">13</span>
        <span signin='2015-09-14' signincode="14">14</span>
        <span signin='2015-09-15' signincode="15">15</span>
        <span signin='2015-09-16' signincode="16">16</span>
        <span signin='2015-09-17' signincode="17">17</span>
        <span signin='2015-09-18' signincode="18" class="span01">18</span>


        <span signin='2015-09-19' signincode="19" class="span01">19</span>
        <span signin='2015-09-20' signincode="20">20</span>
        <span signin='2015-09-21' signincode="21">21</span>
        <span signin='2015-09-22' signincode="22">22</span>
        <span signin='2015-09-23' signincode="23">23</span>
        <span signin='2015-09-24' signincode="24">24</span>
        <span signin='2015-09-25' signincode="25" class="span01">25</span>

        <span signin='2015-09-26' signincode="26" class="span01">26</span>
        <span signin='2015-09-27' signincode="27">27</span>
        <span signin='2015-09-28' signincode="28">28</span>
        <span signin='2015-09-29' signincode="29">29</span>
        <span signin='2015-09-30' signincode="30">30</span>
    </div>
    <div class="signsubmit" id="signsubmitdiv" ><a href="javascript:void(0)" id="signButton">签到</a></div>
</div>
<!-- 百科问答 pop-->
<div class="pop3" id="pop3">
    <img src="http://event51.pppppp.com/hex/201507/moshi/images/close.jpg" class="closebtn03"/>

    <div class="title02">HEX百科问答</div>
    <div class="wenjuan">
        <form class="form2" id='form2' name="form2" method="post" action="/demo/webmall/answer">
            <div class="ques01">
                <h1>1.HEX的TCG集换属性主要体现在？　<b>＊</b></h1>

                <div class="quenum">
                    <p><input type="radio" name="a0" value="A"/>在线对战</p>

                    <p><input type="radio" name="a0" value="B"/>在线交易</p>

                    <p><input type="radio" name="a0" value="C"/>在线比赛</p>
                </div>
            </div>
            <div class="ques01">
                <h1>2.游戏中内共有___色卡牌？　<b>＊</b></h1>

                <div class="quenum">
                    <p><input type="radio" name="a1" value="A"/>4</p>

                    <p><input type="radio" name="a1" value="B"/>5</p>

                    <p><input type="radio" name="a1" value="C"/>6</p>
                </div>
            </div>
            <div class="ques01">
                <h1>3.游戏中每个对战回合共有___个行动阶段？　<b>＊</b></h1>

                <div class="quenum">
                    <p><input type="radio" name="a2" value="A"/>6</p>

                    <p><input type="radio" name="a2" value="B"/>8</p>

                    <p><input type="radio" name="a2" value="C"/>10</p>
                </div>
            </div>

            <div class="ques01">
                <h1>4.游戏中可以通过___对卡牌进行买卖交易？　<b>＊</b></h1>

                <div class="quenum">
                    <p><input type="radio" name="a3" value="A"/>试练场</p>

                    <p><input type="radio" name="a3" value="B"/>锦标赛</p>

                    <p><input type="radio" name="a3" value="C"/>拍卖行</p>
                </div>
            </div>

            <div class="ques01">
                <h1>5.游戏中积分是如何提升卡牌能力的？　<b>＊</b></h1>

                <div class="quenum">
                    <p><input type="radio" name="a4" value="A"/>镶嵌卡牌</p>

                    <p><input type="radio" name="a4" value="B"/>卡牌升级</p>

                    <p><input type="radio" name="a4" value="C"/>购买卡牌</p>
                </div>
            </div>

            <div class="ques01">
                <h1>6.在对方回合也能攻击/反击对方的卡牌类别是？　<b>＊</b></h1>

                <div class="quenum">
                    <p><input type="radio" name="a5" value="A"/>基本战术</p>

                    <p><input type="radio" name="a5" value="B"/>快速战术</p>

                    <p><input type="radio" name="a5" value="C"/>资源</p>
                </div>
            </div>

            <div class="ques01">
                <h1>7.对战中每个英雄的初始血量为？　<b>＊</b></h1>

                <div class="quenum">
                    <p><input type="radio" name="a6" value="A"/>20</p>

                    <p><input type="radio" name="a6" value="B"/>25</p>

                    <p><input type="radio" name="a6" value="C"/>30</p>
                </div>
            </div>

            <div class="ques01">
                <h1>8.HEX每个卡包包含___张卡牌？　<b>＊</b></h1>

                <div class="quenum">
                    <p><input type="radio" name="a7" value="A"/>12</p>

                    <p><input type="radio" name="a7" value="B"/>15</p>

                    <p><input type="radio" name="a7" value="C"/>18</p>
                </div>
            </div>
        </form>
    </div>
    <div class="signsubmit"><a href="javascript:void(0)" id="submit">提交</a></div>
    <p class="truemsg">说明：选择题形式，每道题只有唯一答案，提交时如有错误会提示，直至全部正确方可提交</p>
</div>

<script type="text/javascript" src="${ctx}/static/jquery/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/event/jquery.boxy.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/plugin/jquery.placeholder.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/plugin/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/plugin/jquery.validate.js"></script>

<script type="text/javascript">

    <%--var username = "${sessionScope.USER.account}";--%>
    var username = 'david3424';

    var  _question = ["HEX的TCG集换属性主要体现在？","游戏中内共有___色卡牌？","游戏中每个对战回合共有___个行动阶段？",
        "游戏中可以通过___对卡牌进行买卖交易？","游戏中积分是如何提升卡牌能力的？","在对方回合也能攻击/反击对方的卡牌类别是？","对战中每个英雄的初始血量为？","HEX每个卡包包含___张卡牌？"];
    var isEmpty = function (arg) {
        return arg == "undefined" || arg == null || arg == "" || arg == "null";
    };
    
    $(function () {
//        绑定按钮事件
        initButton();
        initSignin();
        initTask();
        initForm();
    });

    //初始化签到/奖品按钮状态
    function initSignin() {
        $('[signin=${today}]').addClass("qian");
        <c:forEach var="signLogs" items="${signLogs}" varStatus="status">
        $("[signin=${signLogs}]").removeClass("qian").addClass("qianguo");
        </c:forEach>
    }

    function initTask(){
        //当日已签到处理
        if(!isEmpty(username)){
            var isSignToday='${isSignToday}';
            if(isSignToday==1){
                $("#taskSign").removeClass("pp01 nowfaill").addClass("pp01 fiall");
                $("#taskSign").html("已完成");

                $("#signsubmitdiv a").html("已签到");
            }

            var isSubmitInfo='${isSubmitInfo}';
            if(isSubmitInfo==1){
                $("#taskInfo").removeClass("pp01 nowfaill").addClass("pp01 fiall");
                $("#taskInfo").html("已完成");
            }

            var isAnswer='${isAnswer}';
            if(isAnswer==1){
                $("#taskAnswer").removeClass("pp01 nowfaill").addClass("pp01 fiall");
                $("#taskAnswer").html("已完成");
            }
        }
    }

    function initButton(){
        $('.closebtn01').click(function () {
            $.simpleDialog.close('pop1');
        });

        $('.closebtn03').click(function () {
            $.simpleDialog.close('pop3');
        });
        
        $("#nav4").click(function(){
            window.location.href="${ctx}/demo/webmall/mycenter";
        });

        $('#signButton').click(function () {
            signInProcess();
            return false;
        });
        $('#testServer').click(function () {
            fetchServer();
            return false;
        });

        //签到
        $('#signIn').click(function () {
            if(isEmpty(username)){
                login();
                return;
            }
            $.simpleDialog({
                id: 'pop1',
                content: '#pop1',
                skin: false
            });
        });


        //个人信息
        $("#toTaskInfo").click(function(){
            var taskResult=$("#taskInfo").html();
            if(taskResult=='已完成'){
                $.simpleAlert("您已完成该任务了哦！");
                return;
            }else{
                window.location.href="${ctx}/demo/webmall/mycenter?type=1";
            }
        });



        //百科问答
        $('#question').click(function () {
            if(isEmpty(username)){
                login();
                return;
            }
            $.simpleDialog({
                id: 'pop3',
                content: '#pop3',
                skin: false
            });
        });



        $("#attentionButton").click(function(){
            var code=$("#code").val();
            wechatAttentionProcess(code);
        });

        $("#downloadButton").click(function(){
            var code=$("#downloadCode").val();
            downloadProcess(code);
        });
    }

    var signInProcess = function () {
        var isSignToday='${isSignToday}';
        if(isSignToday==1){
            $.simpleAlert("今天您已经签到了哦！");
            return;
        }
        var url = "/demo/webmall/signIn";
        $.getJSON(url, {t: Math.random()}, function (json) {
            if (json.success) {
                $("#taskSign").removeClass("pp01 nowfaill").addClass("pp01 fiall");
                $("#taskSign").html("已完成");
                $(".signsubmit a").html("已签到");
                $('[signin=${today}]').removeClass("qian").addClass("qianguo");
            }
            $.simpleAlert(json.message);
        });
        return false;
    }


    function initForm() {
        //验证
        var validator = $.wrapValidate('#form2', {
//            errorPlacement: function (label, element) {
//                var errorContainer = element.parent().find('.msg');
//                label.appendTo(errorContainer);
//            },
            submitHandler: function (form, event) {
                $(form).ajaxSubmit({
                    success: function (data) {
                        if(data.success){
                            validator.resetForm();
                            $.simpleAlert({content: data.message, ok: function () {
                                location.reload();
                            }});
                        }else{
                            $.simpleAlert(data.message);
                        }
                    },
                    dataType: "json"
                });
            }
        });
        $('#form2').placeholder();
        //提交
        $("#submit").click(function () {
            if (isEmpty(username)) {
                login();
                return false;
            }
            for(var i=0;i<8;i++){
                var name="a"+i;
                var val=$('input:radio[name='+name+']:checked').val();
                if(isEmpty(val)){
                    $.simpleAlert("请回答"+_question[i]);
                    return false;
                }
            }

            $.simpleConfirm({content: "确认提交？", ok: function () {
                $('#form2').submit();
                return false;
            }});
        });
    }

</script>
</body>
</html>

