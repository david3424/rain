<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/taglib.jsp" %>
<div class="navbar navbar-inverse navbar-fixed-top">
    <div class="navbar-inner">
        <div class="container-fluid">
            <span class="brand" style="color: #fff"><%=gameName%></span><!-- 这里的跳转地址在联运情况最好为当前地址 -->
            <!--/.nav-collapse 账号管理-->
            <div class="nav-collapse collapse">
                <ul class="navbar-text pull-right" style="margin: 10px">
                    <li class="dropdown"><a href="#" role="button" class="dropdown-toggle" data-toggle="dropdown">
                        <sec:authentication property="name"/><i class='icon-caret-down'></i></a>
                        <ul class="dropdown-menu" role="menu">
                            <li role="presentation"><a role="menuitem" tabindex="-1" href="#dialog_password_form"
                                                       data-toggle="modal">Change Password</a></li>
                            <li role="presentation"><a role="menuitem" href="<%=basePath%>">Choose Game</a></li>
                            <!-- 这里的跳转地址在联运情况最好为当前地址 -->
                            <li role="presentation"><a role="menuitem" href="<%=basePath%>j_spring_security_logout">Logout</a>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>

<div id="dialog_password_form" class="modal hide fade" tabindex="-1" role="dialog" data-backdrop="static"
     aria-hidden="true">
    <div class="modal-header"><h4>修改密码</h4></div>
    <div class="modal-body">
        <div id="password_validate_tip" class="alert alert-error div-display">
            <span id="password_validate_content"></span>
        </div>
        <div id="password_success_tip" class="alert alert-success div-display">
            <span id="password_success_content"></span>
        </div>
        <form class="form-horizontal">
            <fieldset>
                <div id="old_password_main" class="control-group">
                    <label class="control-label" for="old_password">原始密码：</label>
                    <div class="controls">
                        <input type="password" class="input-xlarge" id="old_password">
                    </div>
                </div>
                <div id="new_password_main" class="control-group">
                    <label class="control-label" for="new_password">新密码：</label>

                    <div class="controls">
                        <input type="password" class="input-xlarge" id="new_password">
                    </div>
                </div>
                <div id="affirm_password_main" class="control-group">
                    <label class="control-label" for="affirm_password">确认密码：</label>

                    <div class="controls">
                        <input type="password" class="input-xlarge" id="affirm_password">
                    </div>
                </div>
            </fieldset>
        </form>
    </div>
    <div class="modal-footer">
        <button type="button" class="btn" onclick="closePassword();">取消</button>
        <button type="button" class="btn btn-primary" onclick="changePassword();">提交</button>
    </div>
</div>

<script type="text/javascript">
    var stayTime = 1000;
    function closePassword() {
        $("#password_validate_tip").val("").hide();
        $(".control-group").removeClass("error");
        clearPasswordInput();
        $("#dialog_password_form").modal("hide");
    }

    function changePassword() {
        $("#password_validate_tip").val("").hide();
        $(".control-group").removeClass("error");
        changePasswordProcess($.trim($("#old_password").val()),
                $.trim($("#new_password").val()),
                $.trim($("#affirm_password").val()));
    }

    function changePasswordProcess(_oldPassword, _newPassword, _affirmPassword) {
        if (_oldPassword == null || _oldPassword.length == 0) {
            showPasswordValidateTip("原始密码不能为空！");
            $("#old_password_main").addClass("error");
            $("#old_password").focus();
            return false;
        }
        if (_newPassword == null || _newPassword.length == 0) {
            showPasswordValidateTip("新密码不能为空！");
            $("#new_password_main").addClass("error");
            $("#new_password").focus();
            return false;
        }
        if (_affirmPassword == null || _affirmPassword.length == 0) {
            showPasswordValidateTip("确认密码不能为空！");
            $("#affirm_password_main").addClass("error");
            $("#affirm_password").focus();
            return false;
        }
        if (_newPassword !== _affirmPassword) {
            showPasswordValidateTip("两次密码输入不一致！");
            $("#new_password_main").addClass("error");
            $("#new_password").focus();
            return false;
        }

        $.ajax({
            url: basePath + "wanmei/system/change_password",
            data: {
                'oldPassword': _oldPassword,
                'newPassword': _newPassword
            },
            dataType: 'json',
            type: 'POST',
            async: true,
            success: function (response) {
                if (response == 1) {
                    showPasswordSuccessTip("修改密码成功！");
                    setTimeout(function () {
                        closePasswordDialogForm();
                    }, stayTime);
                } else if (response == -1) {
                    showPasswordValidateTip("原始密码输入不正确！");
                } else {
                    showPasswordValidateTip("修改密码失败！");
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                validatePasswordTipInfo(XMLHttpRequest, "修改密码失败！");
                return false;
            }
        });
    }

    /**
     * 显示操作成功提示_password
     */
    function showPasswordSuccessTip(_info) {
        $("#password_success_content").html(_info);
        $("#password_success_tip").show();
    }

    /**
     * 显示验证错误提示_password
     */
    function showPasswordValidateTip(_info) {
        $("#password_validate_content").html(_info);
        $("#password_validate_tip").show();
    }

    /**
     * Ajax请求错误处理_ValidateTip
     */
    function validatePasswordTipInfo(XMLHttpRequest, _msg) {
        if (XMLHttpRequest.getResponseHeader("__forbidden")) {
            showPasswordValidateTip(_msg + "您目前权限不足，如需要开通权限，请联系管理员！");
        } else {
            showPasswordValidateTip(_msg);
        }
    }

    /**
     * 关闭弹出对话框
     */
    function closePasswordDialogForm() {
        $("#dialog_password_form").modal("hide");
        $("#password_success_tip").val("").hide();
        $(".control-group").removeClass("error");
        clearPasswordInput();
    }

    function clearPasswordInput() {
        $("#old_password").val(null);
        $("#new_password").val(null);
        $("#affirm_password").val(null);
    }

</script>