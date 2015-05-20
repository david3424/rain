<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<jsp:include page="../../layouts/default.jsp"/>
<html>
<head>
    <title>模拟下线</title>
</head>

<body>
<form method="post" action="${rtx}/multi_game_card/payout"
      name="mgc_info">
    <input type="text" value="${infoMap.applicationCode}" name="applicationCode">
    <input type="text" value="${infoMap.currencyCode}" name="currencyCode">
    <input type="text" value="${infoMap.channelId}" name="channelId">
    <input type="text" value="${infoMap.referenceId}" name="referenceId">
    <input type="text" value="${infoMap.returnUrl}" name="returnUrl">
    <input type="text" value="127.0.0.1" name="ip">
    <input type="text" value="${infoMap.signature}" name="signature">
    <input id="submit_btn" type="submit" value="提交"/>&nbsp;
</form>

</body>
</html>
