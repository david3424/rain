<%@ page import="com.david.web.wanmei.common.components.util.ActionUtil" %>
<%--
  Created by IntelliJ IDEA.
  User: daidandan
  Date: 2014/12/1
  Time: 14:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
	<script type="text/javascript">
		var shareData = {
    img_url: "",
    img_width: 200,
    img_height: 200,
    link: '',
    desc: '',
    title: '',
    appid: 0
};

document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
    // 发送给好友
    WeixinJSBridge.on('menu:share:appmessage', function(argv){
        shareFriend();
    });
    // 分享到朋友圈
    WeixinJSBridge.on('menu:share:timeline', function(argv){
        shareTimeline();
    }); 
}, false);


function shareTimeline() {
    WeixinJSBridge.invoke('shareTimeline', shareData, function(res) {
        validateShare(res);
        _report('timeline', res.err_msg);
    });
}

function shareFriend() {
    WeixinJSBridge.invoke('sendAppMessage', shareData, function(res) {
        validateShare(res);
        _report('send_msg', res.err_msg);
    });
}

function validateShare(res) {
    if(res.err_msg != 'send_app_msg:cancel' && res.err_msg != 'share_timeline:cancel') {
    	alert("分享完毕回调") ;
    //分享完毕回调
    }
}
		
</script>
<head>
    <title>testip</title>
</head>
<body>
<% String ip = ActionUtil.getRealIp() ;
  out.print("您的ip是@@@： ------"+ip+ "---------");
%>



</body>
</html>
