<%@ page import="com.david.web.wanmei.service.hdinterface.wrapper.MallServiceWrapper" %>
<%@ page import="com.david.web.wanmei.vip.bean.Vip" %>
<%@ page import="common.HdUser" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>角色接口验证</title>
</head>
<body>
<%
    if (false) {
        response.sendRedirect("http://www.wanmei.com");
    } else {
        MallServiceWrapper mallServiceInterface = (MallServiceWrapper) request.getAttribute("mallServiceInterface");
%>
<form action="/malltest" method="post">
    判断Vip isVip<br>
    用户ID：<input type="text" name="isVip.userid">&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit"><br>
</form>

<%
    String isVip_userid = request.getParameter("isVip.userid");
    if (StringUtils.isNumeric(isVip_userid)) {
        HdUser hdUser = new HdUser();
        hdUser.setUserid(isVip_userid);
        Integer rtn = mallServiceInterface.isVip(hdUser);
%>
<div>
    返回(0普通用户 ，1是银卡用户，2是金卡用户，3是白金卡用户，4是终身白金卡用户。返回值>0为VIP用户，参数错误 null) ：   <%=rtn%>
</div>
<%
} else {
%>
<div>error</div>
<%}%>

<br><br>

<form action="/malltest" method="post">
    根据用户ID获取VIP信息 getVipByUserid<br>
    用户ID：<input type="text" name="getVipByUserid.userid">&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit"><br>
</form>

<%
    String getVipByUserid_userid = request.getParameter("getVipByUserid.userid");
    if (StringUtils.isNumeric(getVipByUserid_userid)) {
        HdUser hdUser = new HdUser();
        hdUser.setUserid(getVipByUserid_userid);
        Vip vip = mallServiceInterface.getVipByUserid(hdUser);
%>
<div>
    vip信息：<%=vip%>
</div>
<%
} else {
%>
<div>error</div>
<%}%>

<br><br>


<form action="/malltest" method="post">
    赋vip grantExperiencePackage<br>
    用户ID：<input type="text" name="grantExperiencePackage.userid">&nbsp;
    流水号：<input type="text" name="grantExperiencePackage.sn">&nbsp;
    商品ID：<input type="text" name="grantExperiencePackage.prizeid">&nbsp;
    商品名：<input type="text" name="grantExperiencePackage.prizename">&nbsp;
    价格：<input type="text" name="grantExperiencePackage.price">&nbsp;<input type="submit"><br>
</form>

<%
    String grantExperiencePackage_userid = request.getParameter("grantExperiencePackage.userid");
    String grantExperiencePackage_sn = request.getParameter("grantExperiencePackage.sn");
    String grantExperiencePackage_prizeid = request.getParameter("grantExperiencePackage.prizeid");
    String grantExperiencePackage_prizename = request.getParameter("grantExperiencePackage.prizename");
    String grantExperiencePackage_price = request.getParameter("grantExperiencePackage.price");
    if (StringUtils.isNumeric(grantExperiencePackage_userid) && StringUtils.isNumeric(grantExperiencePackage_sn) && StringUtils.isNumeric(grantExperiencePackage_prizeid) && StringUtils.isBlank(grantExperiencePackage_prizename) && StringUtils.isNumeric(grantExperiencePackage_price)) {
        HdUser hdUser = new HdUser();
        hdUser.setUserid(grantExperiencePackage_userid);
        hdUser.setSn(grantExperiencePackage_sn);
        hdUser.setCommodityId(Integer.parseInt(grantExperiencePackage_prizeid));
        hdUser.setCommodity(grantExperiencePackage_prizename);
        hdUser.setCommodityPrice(grantExperiencePackage_price);
        Integer rtn = mallServiceInterface.grantExperiencePackage(hdUser);
%>
<div>
    返回信息：<%=rtn%><br>
    <ul>
        <li>0： 表示操作成功</li>
        <li>-1： 表示用户不存在</li>
        <li>-2： 表示该用礼包对该用户不使用</li>
        <li>-3: 参数信息错误，如sn为空或LogInfo信息不正确</li>
        <li>-4： 数据库操作错误</li>
        <li>-5: 其他错误</li>
        <li>-6：不存在体验包Id为experiencePkgId的体验包</li>
        <li>-7: ID为experiencePkgId的体验包信息已过期或者礼包已失效</li>
        <li>-8: goodsInfo信息错误，必须至少填写name和money（商品名称和商品金额或本次流水号涉及的金额）值</li>
        <li>-9：体验次数超过了最大限制</li>
        <li>-10：体验次数超过了该体验礼包允许的最大体验次数 这个用户已经体验过了</li>
        <li>-11: 流水号在AU端验证失败</li>
        <li>null:参数错误</li>
    </ul>

</div>
<%
} else {
%>
<div>error</div>
<%}%>

<br><br>

<%}%>
</body>
</html>