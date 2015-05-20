<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>添加客户端</title>
</head>

<body >
正在跳转。。。
<div class="modal-content">
    <div class="modal-body">
        <div class="progress progress-striped active"><div class="progress-bar" role="progressbar" aria-valuenow="45" aria-valuemin="0" aria-valuemax="100" style="width: 100%">Loading...</div></div>
    </div>
</div>
<form method="post" action="${infoMap.paymentUrl}"
      id="mgc_info">
    <input   type="hidden" value="${infoMap.currency}" name="currency">
    <input   type="hidden" value="${infoMap.failedReturnUrl}"
           name="failedReturnUrl">
    <input   type="hidden" value="${infoMap.merchantId}" name="merchantId">
    <input   type="hidden" value="${infoMap.notificationUrl}"
           name="notificationUrl">
    <input   type="hidden" value="${infoMap.paymentMethod}" name="paymentMethod">
    <input   type="hidden" value="${infoMap.refId}" name="refId">
    <input   type="hidden" value="${infoMap.successReturnUrl}"
           name="successReturnUrl">
    <input   type="hidden" value="${infoMap.trxTimestamp}" name="trxTimestamp">
    <input   type="hidden" value="${infoMap.signature}"
           name="signature">
</form>

</body>
    <script type="text/javascript">
    function validate(){
        document.getElementById('mgc_info').submit();
    }
    window.load=validate();
</script>
</html>
