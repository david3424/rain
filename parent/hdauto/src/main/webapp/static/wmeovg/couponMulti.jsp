<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<%@page import="com.david.web.wanmei.wmeovg.request.service.IPrizeService" %>
<%@page import="com.david.web.wanmei.wmeovg.request.service.PrizeServiceManager" %>
<%@page import="com.david.web.wanmei.wmeovg.request.util.Priority" %>
<%@page import="java.util.Random" %>
<%@ page import="com.david.web.wanmei.wmeovg.request.entity.GoodsLog" %>
<%@ page import="com.david.web.wanmei.wmeovg.request.util.PrizeSendType" %>
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
            <td><input type='text' name='appid' value="chibi_couponplatform_prize"/></td>
        </tr>
        <tr>
            <td>所在服务器</td>
            <td><input type='text' name='zoneid' value="121"/></td>
        </tr>
        <tr>
            <td>账号</td>
            <td><input type='text' name='account' value="tanghc@163.com"/></td>
        </tr>
        <tr>
            <td>账号id</td>
            <td><input type='text' name='userid' value="2002"/></td>
        </tr>
        <tr>
            <td>角色ID</td>
            <td><input type='text' name='roleid' value="2655808"/></td>
        </tr>
        <tr>
            <td>奖品ID</td>
            <td><input type='text' name='prizeid' value="6645"/></td>
        </tr>
        <tr>
            <td>奖品名</td>
            <td><input type='text' name='prizename' value="礼包碧波清影装"/></td>
        </tr>
        <tr>
            <td>发送个数</td>
            <td><input type='text' name='count' value="1"/></td>
        </tr>
        <tr>
            <td>消费元宝券数</td>
            <td><input type='text' name='score' value="1"/></td>
        </tr>
        <tr>
            <td>折前价格</td>
            <td><input type='text' name='oldscore' value="1"/></td>
        </tr>
        <tr>
            <td>orderid</td>
            <td><input type='text' name='orderid' value="<%=longOrderId%>"/></td>
        </tr>
        <tr>
            <td>附加参数</td>
            <td><input type='text' name='callback' value="chibi_couponplatform_prize&43&ROLEID&huodong108&COUPON_MULTI" size="80"/></td>
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
    String account = request.getParameter("account");
    String roleid = request.getParameter("roleid");
    String pid = request.getParameter("prizeid");
    String rolename = request.getParameter("rolename");
    String userid = request.getParameter("userid");
    String orderid = request.getParameter("orderid");
    String c = request.getParameter("count");
    String callback = request.getParameter("callback");
    String pri = request.getParameter("priority");
    String score = request.getParameter("score");
    String oldscore = request.getParameter("oldscore");
    String prizename = request.getParameter("prizename");

    IPrizeService prizeService = PrizeServiceManager.abroadPrizeService;

    if (pid != null) {
        Integer zoneid = new Integer(zid);
        Integer prizeid = new Integer(pid);
        Integer count = new Integer(c);
        Priority priority;
        if (pri.equals("1"))
            priority = Priority.NORMAL;
        else if (pri.equals("2"))
            priority = Priority.MEDIUM;
        else
            priority = Priority.FAST;

        //System.out.println("********" + callback);
        /*int t = 10000;
          while(t-->0){
              Integer[] prizes = {33,201};
              Random r = new Random();
              prizeid = prizes[r.nextInt(2)];
              String gid = prizeService.genGid(); // 客户端生成流水号
              prizeService.send(appid, gid, zoneid, account, rolename,
                      prizeid, count, priority, callback);
          }*/
        GoodsLog goodsLog = new GoodsLog();
        goodsLog.setAppid(appid);
        goodsLog.setGid(prizeService.genGid());
        goodsLog.setCallback(callback);
        goodsLog.setPriority((byte) 1);
        goodsLog.setCount(1);
        goodsLog.setZoneid(zoneid);
        goodsLog.setUserid(Integer.parseInt(userid));
        goodsLog.setRoleid(Long.parseLong(roleid));
        goodsLog.setRolename(rolename);
        goodsLog.setPrizeid(prizeid);
        goodsLog.setMailTitle(prizename);
        goodsLog.setAttachMoney(0);
        goodsLog.setGoodsFlag(1);
        goodsLog.setGoodsPrice(Integer.parseInt(score));
        goodsLog.setGoodsPriceBeforeDiscount(Integer.parseInt(oldscore));
        goodsLog.setReserved1(2); //元宝消耗
        goodsLog.setReserved2(0);
        goodsLog.setGid(prizeService.genGid());
        goodsLog.setPrizeSendType(PrizeSendType.COUPON_MULTI_PRIZE);
        goodsLog.setCouponOrderid(Long.parseLong(orderid));
        int rtn = prizeService.send(goodsLog);

        out.write("奖品兑换请求响应状态码：" + rtn);
    }
%>
</body>
</html>