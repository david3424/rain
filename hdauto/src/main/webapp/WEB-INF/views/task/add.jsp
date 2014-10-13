<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>添加任务</title>
</head>
<body>
<div class="container">
    <!-- Main hero unit for a primary marketing message or call to action -->
    <div class="hero-unit">
        <h1>添加定时器</h1>

        <p>定时器可自定义添加，不用的定时器请暂停或者直接删除。corn表达式请移步到具体<abbr title="corn表达式说明">说明页</abbr>。</p>

        <p><a class="btn btn-primary btn-inverse">查看更多 &raquo;</a></p>
    </div>

    <form id="add_form" action="${ctx}/task/create" method="post" class="form-horizontal">
        <div class="control-group">
            <label class="control-label" for="triggerName">定时器名字：</label>

            <div class="controls">
                <input class="span5" type="text" id="triggerName" name="table_name" placeholder="定时器名字"> <span
                    class="help-inline">   （必须使用发奖表表名）</span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="trigger_dataSource">数据源：</label>

            <div class="controls">
                <!--<input type="text" id="triggerGroup" name="triggerGroup" placeholder="定时器分组">-->
                <select class="span5" name="datasource" id="trigger_dataSource">
                    <option value="event">event</option>
                    <option value="huodong218">huodong218(event20)</option>
                    <option value="huodong161">huodong161(eventie)</option>
                    <option value="huodong108">huodonghk(huodong108)</option>
                </select>
                <span class="help-inline">（补发使用eventie） </span>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label" for="send_type">发奖类型</label>

            <div class="controls">
                <!--<input type="text" id="triggerGroup" name="triggerGroup" placeholder="定时器分组">-->
                <select class="span5" name="send_type" id="send_type">
                    <option value="1">普通发奖</option>
                    <option value="2">团购发奖</option>
                    <option value="3">新团购发奖</option>
                </select>
                <span class="help-inline">目前就三种</span>
            </div>
        </div>

        <div class="control-group" style="display: none" id="couponType">
            <label class="control-label" for="consume_type">新团购消费类型</label>
            <div class="controls">
                <!--<input type="text" id="triggerGroup" name="triggerGroup" placeholder="定时器分组">-->
                <select class="span5" name="consume_type" id="consume_type">
                    <option value="1">点券（GCoins）</option>
                    <option value="2">元宝</option>
                </select>
                <span class="help-inline">老团购这里没有用，新团购目前就2种</span>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label" for="role_id_type">role_id_type</label>

            <div class="controls">
                <input type="hidden" name="role_id_type" id="hidden_id_type" value="1"/>
                <!--<input type="text" id="triggerGroup" name="triggerGroup" placeholder="定时器分组">-->
                <select class="span5" id="role_id_type">
                    <option value="1">角色ID——int类型（非神魔）</option>
                    <option value="2">角色ID——long类型（神魔类型：shenmo,sdxl,mhzx,mhzx2,xajh,seiya）</option>
                </select>
                <span class="help-inline">以后新增游戏都是Long类型的,团购活动默认是long类型的</span>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label" for="send_interface">发奖接口</label>

            <div class="controls">
                <select class="span5" name="send_interface" id="send_interface">
                    <option value="1">大陆接口</option>
                    <option value="2">海外接口</option>
                </select>
                <span class="help-inline">目前就两种</span>
            </div>
        </div>

        <div class="control-group">
            <label class="control-label">发奖检查</label>

            <div class="controls">
                <span>不检查：</span><input class="inline" type="radio" checked="checked" name="send_check" value="0" title="不检查"/>
                <span>检查：</span><input  class="inline" type="radio" name="send_check" value="1" title="检查"/>
            </div>
        </div>

        <div class="control-group" id="corn_simple_div">
            <label class="control-label" for="cronSimple">发送间隔：</label>

            <div class="controls" id="select_div">
                <select class="span2" id="cronSimple" name="display_name">
                    <option value="60">1分钟</option>
                    <option value="30">30秒</option>
                    <option value="40">40秒</option>
                    <option value="50">50秒</option>
                    <option value="90">1分半</option>
                </select>（300个每次）
                <span class="btn btn-info" id="hard">高级</span>
            </div>
            <div id="corn_div" class="controls" style="display: none">
                <input class="span5" type="text" id="cronExpression" disabled="disabled" name="display_name"
                       placeholder="如(0/10 * * * * ?)，每10秒中执行调试一次"/>
                <span class="btn btn-info" id="simple">简单</span>
            </div>
        </div>

        <div class="form-actions">
            <input type="submit" class="btn btn-primary" value="创建"/>
        </div>
    </form>
    <hr>


</div>

<script>
    $(document).ready(function () {

        $('#hard').click(function () {
            $("#select_div").hide();
            $("#cronSimple").attr("disabled", "disabled");

            $("#corn_div").show();
            $("#cronExpression").removeAttr("disabled");

        });

        $("#simple").click(function () {
            $("#corn_div").hide();
            $("#cronExpression").attr("disabled", "disabled");

            $("#select_div").show();
            $("#cronSimple").removeAttr("disabled");
        });

        $("#send_type").change(function () {
            var _type = $(this).val();
            if (_type != "1") {
                $('#couponType').show();
                $("#role_id_type>option").each(function () {
                    if ($(this).val() == "2") {
                        $(this).attr("selected", "selected");
                    }
                    else {
                        $(this).removeAttr("selected");
                    }
                });
                $("#role_id_type").attr("disabled", "true");
                $("#hidden_id_type").val("2");
            }
            else {
                $('#couponType').hide();
                $("#role_id_type").removeAttr("disabled");
            }
        });

        $("#role_id_type").change(function () {
            $("#hidden_id_type").val($(this).val());
        });

        $("#add_form").validate({
            submitHandler: function (form) {
                form.submit();
            },
            rules: {
                table_name: {
                    remote: "${ctx}/task/checkTaskName",
                    required: true
                }
            },
            messages: {
                table_name: {
                    remote: "该表名已存在",
                    required: "表名不能为空"
                }
            }
        });
    });
</script>
</body>
</html>