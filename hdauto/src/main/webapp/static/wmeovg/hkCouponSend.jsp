<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<%@page import="com.wanmei.wmeovg.request.service.IPrizeService" %>
<%@page import="com.wanmei.wmeovg.request.service.PrizeServiceManager" %>
<%@page import="com.wanmei.wmeovg.request.util.Priority" %>
<%@page import="java.util.Random" %>
<%@ page import="com.wanmei.wmeovg.request.entity.GoodsLog" %>
<%@ page import="java.text.SimpleDateFormat" %>

<%
    SimpleDateFormat sdfId = new SimpleDateFormat("yyMMddHHmmss");
    long longOrderId =  (200l << 48) + (2l << 40) + (2l << 32) + Long.parseLong(sdfId.format(System.currentTimeMillis())) + (new Random().nextInt(99999));
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
</head>
<body>
<form action="couponMulti.jsp" method="post">
    <table>
        <tr>
            <td>应用标识</td>
            <td><input type='text' name='appid' value="hdauto_multiprize_coupon"/></td>
        </tr>
        <tr>
            <td>所在服务器</td>
            <td><input type='text' name='zoneid' value="943"/></td>
        </tr>
        <%--<tr>--%>
            <%--<td>账号</td>--%>
            <%--<td><input type='text' name='account' value="dollwawa84"/></td>--%>
        <%--</tr>--%>
        <tr>
            <td>账号id</td>
            <td><input type='text' name='userid' value="2491"/></td>
        </tr>
        <tr>
            <td>角色ID</td>
            <td><input type='text' name='roleid' value="11367"/></td>
        </tr>
        <tr>
            <td>奖品ID</td>
            <td><input type='text' name='prizeid' value="8263"/></td>
        </tr>
        <tr>
            <td>奖品名</td>
            <td><input type='text' name='prizename' value="测试奖品"/></td>
        </tr>
        <tr>
            <td>发送个数</td>
            <td><input type='text' name='count' value="1"/></td>
        </tr>
        <tr>
            <td>消费GCoins数</td>
            <td><input type='text' name='score' value="1"/></td>
        </tr>
        <tr>
            <td>orderid</td>
            <td><input type='text' name='orderid' value="<%=longOrderId%>"/></td>
        </tr>
        <tr>
            <td>附加参数</td>
            <td><input type='text' name='callback' value="chibi_coupon_prize&id=2&huodong108&COUPON" size="80"/></td>
        </tr>
        <tr>
            <td>发送优先级</td>
            <td><select name="priority">
                <option value="1">普通</option>
                <option value="2">较快</option>
                <option value="3">最高</option>
            </select></td>
        </tr>
        <tr>
            <td><input type="submit" value="提交"/></td>
        </tr>
    </table>
</form>
<%
    request.setCharacterEncoding("UTF-8");

    String appid = request.getParameter("appid");
    String zid = request.getParameter("zoneid");
    String roleid = request.getParameter("roleid");
    String pid = request.getParameter("prizeid");
    String userid = request.getParameter("userid");
    String orderid = request.getParameter("orderid");
    String c = request.getParameter("count");
    String callback = request.getParameter("callback");
//    String pri = request.getParameter("priority");
    String score = request.getParameter("score");

    IPrizeService prizeService = PrizeServiceManager.abroadPrizeService;

    if (pid != null) {
        Integer zoneid = new Integer(zid);
        Integer prizeid = new Integer(pid);
        Integer count = new Integer(c);
//        Priority priority;
//        if (pri.equals("1"))
//            priority = Priority.NORMAL;
//        else if (pri.equals("2"))
//            priority = Priority.MEDIUM;
//        else
//            priority = Priority.FAST;
        GoodsLog goodsLog = new GoodsLog();

        goodsLog.setAppid(appid);
        goodsLog.setPriority(new Byte((byte) 1));
        goodsLog.setCount(1);
        goodsLog.setZoneid(zoneid);
        goodsLog.setUserid(Integer.parseInt(userid));
        goodsLog.setRoleid(Long.parseLong(roleid));
        goodsLog.setPrizeid(prizeid);
        goodsLog.setAttachMoney(0);
        goodsLog.setGoodsFlag(1);
        goodsLog.setGoodsPrice(Integer.parseInt(score));
        goodsLog.setGoodsPriceBeforeDiscount(0);
        goodsLog.setReserved2(0);
        goodsLog.setGid(prizeService.genGid());
        goodsLog.setPrizeSendType((byte)4);
        goodsLog.setCouponOrderid(Long.parseLong(orderid));
        goodsLog.setCallback(callback);

        int rtn = prizeService.send(goodsLog);

        out.write("奖品兑换请求响应状态码：" + rtn);
    }
%>
</body>
</html>