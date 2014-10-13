<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>添加补发任务</title>
</head>
<body>
<div class="container">
    <!-- Main hero unit for a primary marketing message or call to action -->
    <div class="hero-unit">
        <h1>添加补发任务</h1>

        <p> 补发需要审核，暂时有管理员统一操作。
        </p>

        <p><a class="btn btn-primary btn-inverse">查看更多 &raquo;</a></p>
    </div>
    <form id="myForm" action="${ctx}/resend/add" method="post" class="form-horizontal">
        <div class="control-group">
            <label class="control-label" for="actname">任务单名称：</label>

            <div class="controls">
                <input class="span5" type="text" id="actname" name="actname" placeholder="任务单名称"> <span
                    class="help-inline"></span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="gamename">所属游戏：</label>

            <div class="controls">
                <select class="span5" name="gamename" id="gamename">
                    <option value="">请选择</option>
                    <option value="神魔大陆">神魔大陆</option>
                    <option value="诛仙2">诛仙2</option>
                    <option value="完美国际">完美国际</option>
                    <option value="武林2">武林2</option>
                    <option value="赤壁">赤壁</option>
                    <option value="热舞派对">热舞派对</option>
                    <option value="降龙之剑">降龙之剑</option>
                    <option value="梦幻诛仙">梦幻诛仙</option>
                    <option value="神鬼传奇">神鬼传奇</option>
                    <option value="神鬼世界">神鬼世界</option>
                    <option value="笑傲江湖">笑傲江湖</option>
                    <option value="倚天屠龙记">倚天屠龙记</option>
                    <option value="完美世界">完美世界</option>
                    <option value="口袋西游">口袋西游</option>
                    <option value="神雕侠侣">神雕侠侣</option>
                    <option value="降龙极致">降龙极致</option>
                    <option value="圣斗士星矢">圣斗士星矢</option>
                    <option value="美食猎人">美食猎人</option>
                </select>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="ordertype">发奖方式：</label>

            <div class="controls">
                <select class="span5" name="ordertype" id="ordertype">
                    <option value="">请选择</option>
                    <option value="非梦诛">角色id</option>
                    <option value="梦诛类型">角色id（梦诛/神魔/神雕）</option>
                </select>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="totalnum">奖品数量：</label>

            <div class="controls">
                <input class="span5" type="text" id="totalnum" name="totalnum" placeholder="请填写整数"/>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="description">目的：</label>

            <div class="controls">
                <textarea class="span5" type="text" id="description" name="description"></textarea>
            </div>
        </div>
        <div class="form-actions">
            <input type="submit" class="btn btn-primary" value="创建"/>
        </div>
        <%--暂时不用审核--%>
        <input type="hidden" name="status" value="1"/>
    </form>
    <hr>


</div>

<script>
    $(document).ready(function() {
        //聚焦第一个输入框
//			$("#triggerName").focus();
        $("#myForm").validate({
            rules: {
                actname: {
                    <%--remote: "${ctx}/task/checkTaskName",--%>
                    required:true
                }
                /*,
                description:{
                    required:true,
                    minlength:10
                }*/
            },
            messages: {
                trigger_name: {
                    remote: "该表名已存在"
                }
            }
        });
    });
</script>
</body>
</html>