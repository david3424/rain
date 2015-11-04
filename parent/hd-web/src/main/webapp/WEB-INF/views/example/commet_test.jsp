<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
    <script type="text/javascript"
            src="${ctx}/static/jquery/jquery-1.8.2.min.js"></script>
    <script type="text/javascript" src="${ctx}/dwr/engine.js"></script>
    <script type="text/javascript" src="${ctx}/dwr/util.js"></script>
    <script type="text/javascript"
            src="${ctx}/dwr/interface/directController.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            //这个方法用来启动该页面的ReverseAjax功能
            dwr.engine.setActiveReverseAjax(true);
            //设置在页面关闭时，通知服务端销毁会话
            dwr.engine.setNotifyServerOnPageUnload(true);
            //设置DWR调用服务出错时，不打印(alert)调试信息
            dwr.engine.setErrorHandler(function() {
                //
            });
            onPageLoad();
        })
        function onPageLoad() {
            directController.onPageLoad(${id});
        }
        function showMessage(message) {
            console.log(message);
            $('#orderNotice').append(message);
        }

        function registerSuccess(message) {
            console.log(message);
            $('#orderNotice').append('welcome:' + message + '\n');
        }
    </script>

</head>
<body onload="dwr.engine.setActiveReverseAjax(true);">

<div style="color: red" ><span>物品ID:${id}</span></div>

<textarea rows="20" cols="20" id="news_id"></textarea>
<br />
<div id="orderNotice"></div>
</body>
</html>