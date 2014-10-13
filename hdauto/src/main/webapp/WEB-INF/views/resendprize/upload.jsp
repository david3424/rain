<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>上传文件</title>
</head>
<body>
<div class="container">
    <div class="hero-unit">
        <h1>上传特定格式的文件</h1>

        <p>需按照指定的顺序来上传文件，详细请移步<abbr title="excel格式">说明页</abbr>。</p>

        <p><a class="btn btn-primary btn-inverse">查看更多 &raquo;</a></p>
    </div>
    <%--<c:if test="${not empty message}">--%>
    <%--<div id="message" class="alert alert-success">--%>
    <%--<button data-dismiss="alert" class="close">×</button>--%>
    <%--${message}</div>--%>
    <%--</c:if>--%>
    <form id="myForm" class="form-horizontal"
          action="" method="post" class="form-horizontal" enctype="multipart/form-data">

        <div class="control-group">
            <label class="control-label" for="tablename">选择目的表</label>

            <div class="controls">
                <select name="tablename" id="tablename" class="span4">
                    <option value="kefu_sendprize_temp">通道0-默认（kefu_sendprize_temp）</option>
                    <option value="kefu_sendprize_temp_1">通道1（kefu_sendprize_temp_1）</option>
                    <option value="kefu_sendprize_temp_2">通道2（kefu_sendprize_temp_2）</option>
                    <option value="kefu_sendprize_temp_3">通道3（kefu_sendprize_temp_3）</option>
                    <option value="kefu_sendprize_temp_4">通道4（kefu_sendprize_temp_4）</option>
                    <option value="kefu_sendprize_temp_5">通道5（kefu_sendprize_temp_5）</option>
                    <option value="kefu_sendprize_temp_6">通道6（kefu_sendprize_temp_6）</option>
                    <option value="kefu_sendprize_temp_7">通道7（kefu_sendprize_temp_7）</option>
                    <option value="kefu_sendprize_temp_8">通道8（kefu_sendprize_temp_8）</option>
                    <option value="kefu_sendprize_temp_9">通道9（kefu_sendprize_temp_9）</option>
                    <option value="kefu_sendprize_temp_10">通道10（kefu_sendprize_temp_10）</option>
                </select>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="filename">上传文件</label>

            <div class="controls">
                <input type="file" name="file" id="filename"/>
            </div>
        </div>
        <div class="form-actions">
            <input type="button" id="btn_submit" class="btn btn-primary" value="上传"/>
            <a class="btn" href="${ctx}/resend/orderlist">返回</a>
        </div>
        <input type="hidden" name="orderid" value="${param.orderid}"/>
    </form>
    <hr>


</div>

<!-- Le javascript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script>
    $(function () {
        $('.dropdown-toggle').dropdown();


        var submitFunc = function () {
            var $btn_submit = $(this);
            $btn_submit.unbind("click");
            var options = {
                dataType: 'json',
                url: '${ctx}/resend/uploadexcel',
                beforeSend: function () {

                    if (!$.isEmptyObject($("#message")[0])) {
                        $('#message').removeClass().addClass("alert alert-info").html("正在上传，请稍候");
                    }
                    else {
                        var msg = '<div id="message" class="alert alert-info">正在上传，请稍候</div>';
                        $('div.hero-unit').after(msg);
                    }
                },
                success: function (data) {
                    $btn_submit.click(submitFunc);
                    if (data.success) {
                        $('#message').removeClass("alert-info").addClass("alert-success").html(data.message);
                    } else {
                        $('#message').removeClass("alert-info").addClass("alert-error").html(data.message);
                    }
                }
            };
            $('#myForm').ajaxForm(options).submit();
        };
        $('#btn_submit').click(submitFunc);
    });
</script>
</body>
</html>