<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<%--<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />--%>
<head>
    <title></title>

</head>
<body>
<div>
    <c:choose>
        <c:when test="${empty sessionScope.USER.account}">
            <a class="" href="javascript:void(0)" id="login">立即登录</a>
        </c:when>
        <c:otherwise>
            欢迎你，${sessionScope.USER.account}:
            <a class="" href="javascript:void(0)" id="logout">登出帐号</a>
        </c:otherwise>
    </c:choose>
</div>

<div>
    <form id="form1" action="${ctx}/example/submit/withrole" method="post">
        <ul>
            <li>
                <input type="hidden" name="server" id="server" value=""/>
                <input type="hidden" name="serverName" id="servername" value=""/>

                <div class="lb"><label>服务器：</label></div>
                <div class="val">
                    <select id="serverlist" rule="{required:true}" message="{required: '服务器不能为空。'}"></select>
                    <span class="msg"></span>
                </div>
            </li>
            <li>
                <div class="lb"><label>角色名：</label></div>
                <div class="val">
                    <input class="text" name="roleName" type="text" placeholder="32字以内"
                           maxlength="32"
                           rule="{required:true,minlength:1,maxlength:32}"
                           message="{required: '角色名不能为空。'}"/>
                    <span class="msg"></span>
                </div>
            </li>
            <li>
                <div class="lb"><label>qq：</label></div>
                <div class="val">
                    <input class="text" name="qq" type="text" placeholder="32字以内"
                           maxlength="32"
                           rule="{required:true,minlength:1,maxlength:32}"
                           message="{required: '角色名不能为空。'}"/>
                    <span class="msg"></span>
                </div>
            </li>
            <li>
                <label>手机号：</label>
                <input name="phone" placeholder="11位手机号码"
                       maxlength="11"
                       rule="{required:true,phone:true,minlength:1,maxlength:11}"
                       message="{required: '手机号不能为空。'}"/>
                <span class="msg"></span>
            </li>

            <li>
                <label>验证码：</label>
                <input type="text" id="rand" placeholder="请输入验证码"  name="rand" rule="{required:true}"
                       message="{required: '验证码不能为空。'}">
                <span id="rand_img_change"onclick="$('#rand_img').attr('src', '${ctx}/servlet/CaptchaServlet?r=' + Math.random());"><img
                                      id="rand_img" src="${ctx}/servlet/CaptchaServlet"/></span>
                <span class="msg"></span>
            </li>
        </ul>

        <button type="button" id="btn_submit">提交</button>
    </form>
</div>
<script type="text/javascript" src="${ctx}/static/jquery/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/event/jquery.boxy.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/plugin/jquery.validate.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/plugin/jquery.placeholder.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/plugin/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/event/serverlist.js"></script>
<script type="text/javascript" src="${ctx}/extend/wmpassport2/pppppp.passport.js"></script>
<script type="text/javascript">
    var username = "${sessionScope.USER.account}";

    $(function () {
        initLoginLogout();
        initForm();
        $("#serverlist").serverlist({
            gamename: "sdxl"
        });
    });

    function initLoginLogout() {
        $('#login').click(function () {
            login();
        });
        $('#logout').click(function () {
            logout();
        });
    }
    function login() {
        pppppp.passport.islogin({session: 'USER', _true: function () {
        }, _false: function () {
            pppppp.passport.login({session: 'USER'})
        }});
    }
    function logout() {
        pppppp.passport.logout({opacity: 60, session: 'USER'});
    }
    var isEmpty = function (arg) {
        return arg == "undefined" || arg == null || arg == "" || arg == "null";
    };
    var isLogin = function () {
        if (isEmpty(username)) {
            $.simpleAlert({content: "请先登录。", ok: function () {
                login();
            }});
            return false;
        }

        return true;
    };

    function initForm() {
        //验证
        var validator = $.wrapValidate('#form1', {
            errorPlacement: function (label, element) {
                var errorContainer = element.parent().find('.msg');
                label.appendTo(errorContainer);
            },
            submitHandler: function (form, event) {
                $(form).ajaxSubmit({
                    success: function (data) {
                        $.simpleAlert(data.message);
                    } ,
                    dataType:"json"
                });
            }
        });

        $('#form1').placeholder();
        //提交
        $("#btn_submit").click(function () {
            if (!isLogin()) {
                return false;
            }
            $('#form1').submit();
            return false;
        });
    }

</script>
</body>
</html>
