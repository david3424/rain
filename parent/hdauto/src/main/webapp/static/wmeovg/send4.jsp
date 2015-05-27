<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<%@page import="com.wanmei.wmeovg.request.service.IPrizeService" %>
<%@page import="com.wanmei.wmeovg.request.service.PrizeServiceManager" %>
<%@page import="com.wanmei.wmeovg.request.util.Priority" %>
<%@page import="java.util.Random" %>
<%@ page import="com.wanmei.wmeovg.request.entity.GoodsLog" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
</head>
<body>
<form action="send4.jsp" method="post">
    <table>
        <tr>
            <td>应用标识</td>
            <td><input type='text' name='appid' value="hdmanager"/></td>
        </tr>
        <tr>
            <td>所在服务器</td>
            <td><input type='text' name='zoneid' value="1089"/></td>
        </tr>
        <tr>
            <td>账号</td>
            <td><input type='text' name='account' value="dollwawa84"/></td>
        </tr>
        <tr>
            <td>账号id</td>
            <td><input type='text' name='userid' value="220113780"/></td>
        </tr>
        <tr>
            <td>角色ID（梦珠）</td>
            <td><input type='text' name='roleid' value="75042032"/></td>
        </tr>
        <tr>
            <td>角色名</td>
            <td><input type='text' name='rolename' value="喵小小小茜"/></td>
        </tr>
        <tr>
            <td>奖品ID</td>
            <td><input type='text' name='prizeid' value="2644"/></td>
        </tr>
        <tr>
            <td>发送个数</td>
            <td><input type='text' name='count' value="1"/></td>
        </tr>
        <tr>
            <td>消费点券数</td>
            <td><input type='text' name='score' value="10000"/></td>
        </tr>
        <tr>
            <td>orderid</td>
            <td><input type='text' name='orderid' value="1000000"/></td>
        </tr>
        <tr>
            <td>附加参数</td>
            <td><input type='text' name='callback' value="table='chibi_coupon_prize'&id=2&note='赤壁发奖'" size="80"/></td>
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

    IPrizeService prizeService = PrizeServiceManager.prizeService;

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
        goodsLog.setPriority(new Byte((byte)1));
        goodsLog.setCount(new Integer(1));
        goodsLog.setZoneid(zoneid);
        goodsLog.setUserid(new Integer(Integer.parseInt(userid)));
        goodsLog.setRoleid(new Long(Long.parseLong(roleid)));
        goodsLog.setRolename(rolename);
        goodsLog.setPrizeid(prizeid);
        goodsLog.setAttachMoney(new Integer(0));
        goodsLog.setGoodsFlag(new Integer(1));
        goodsLog.setGoodsPrice(new Integer(Integer.parseInt(score)));
        goodsLog.setGoodsPriceBeforeDiscount(new Integer(1000));
        goodsLog.setReserved1(new Integer(0));
        goodsLog.setReserved2(new Integer(0));
        goodsLog.setGid(prizeService.genGid());
        goodsLog.setCouponOrderid(new Long(Long.parseLong(orderid)));
        int rtn = prizeService.send(goodsLog);

        out.write("奖品兑换请求响应状态码：" + rtn);
    }
%>
</body>
</html>