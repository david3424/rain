<%--
  Created by IntelliJ IDEA.
  User: gameuser
  Date: 14-5-6
  Time: 下午5:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%
    String game = request.getParameter("game");
%>
<html>
<head>
    <title>测试复杂角色名服务器验证</title>
    <link href="${ctx}/static/styles/boxy.css" rel="stylesheet" type="text/css">
</head>
<body>
<h2>玩家角色名服务器验证测试</h2>
<div>
    <form id="form" action="${ctx}/test/role/pwrd" method="post">
        <input type="hidden" name="server" id="server" value=""/>
        <input type="hidden" name="serverName" id="servername" value=""/>
        <table>
            <tr>
                <td>账号：</td>
                <td> <input type="text" id="username" name="username" rule="{required:true}"
                            message="{required: '账号不能为空。'}" ><span class="msg"></span> </td>
            </tr>
            <tr>
                 <td>角色名：</td><td><input type="text" id="roleName" name="roleName" rule="{required:true,minlength:1,maxlength:32}"
                           message="{required: '角色名不能为空。'}" ><span class="msg"></span></td>
            </tr>
            <tr>
                <td>服务器：</td><td><select id="serverlist" rule="{required:true}" message="{required: '服务器不能为空。'}"></select>
                <span class="msg"></span></td>
            </tr>
            <tr>
                <td>游戏id：</td><td><input type="text" id="gameid" name="gameid" rule="{required:true}"
                                           message="{required: '游戏id不能为空。'}" ><span class="msg"></span> </td>
            </tr>
        </table>
        <input type="button" value="提 交" id="but_sub">
    </form>
</div>

<script type="text/javascript" src="${ctx}/static/jquery/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/event/jquery.boxy.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/plugin/jquery.validate.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/plugin/jquery.placeholder.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/plugin/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/event/serverlist.js"></script>
<script type="text/javascript" src="${ctx}/extend/wmpassport2/wanmei.passport.js"></script>
<script type="text/javascript">
    var username = "${sessionScope.USER.account}";
    $(function () {
        initLoginLogout();
        initForm();
        $("#serverlist").serverlist({
            gamename: "${game}"
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

    var isEmpty = function (arg) {
        return arg == "undefined" || arg == null || arg == "" || arg == "null";
    };
    function login() {
        wanmei.passport.islogin({session: 'USER', _true: function () {
        }, _false: function () {
            wanmei.passport.login({session: 'USER',hdid:'kefu_vipsurvey_status'})
        }});
    }

    function logout() {
        wanmei.passport.logout({opacity: 60, session: 'USER'});
    }
    function initForm() {
        //验证
        var validator = $.wrapValidate('#form', {
            errorPlacement: function (label, element) {
                var errorContainer = element.parent().find('.msg');
                label.appendTo(errorContainer);
            },
            submitHandler: function (form, event) {
                $(form).ajaxSubmit({
                    success: function (data) {
                        $.simpleAlert(data.message);
                    },
                    dataType: "json"
                });
            }
        });

        $('#form').placeholder();
        //提交
        $("#but_sub").click(function () {
            $('#form').submit();
            return false;
        });
    }
</script>
</body>
</html>