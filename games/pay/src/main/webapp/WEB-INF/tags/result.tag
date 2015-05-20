<%@ tag import="org.apache.commons.lang.StringUtils" %>
<%@tag pageEncoding="UTF-8" %>
<%@ attribute name="result" type="java.lang.String" required="true" %>
<%@ attribute name="flag" type="java.lang.Integer" required="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
        if (flag == 0) {
            if (result.equals("1")) {
                result += "请求不合法，即数字签名不一致";
            } else if (result.equals("2")) {
                result += "客户端标识不存在";
            } else if (result.equals("3")) {
                result += "兑换请求已保存，但进入JMS队列失败";
            } else if (result.equals("4")) {
                result += "超出物品发送数目上限。";
            } else if (result.equals("5")) {
                result += "数字签名异常";
            } else if (result.equals("6")) {
                result += "数据接收异常";
            } else if (result.equals("7")) {
                result += "数据不合法，比如奖品ID<=0等";
            } else if (result.equals("8")) {
                result += "兑换请求保存失败";
            } else if (result.equals("9")) {
                result += "白名单验证异常";
            } else if (result.equals("10")) {
                result += "发送物品不在白名单内";
            } else if (result.equals("11")) {
                result += "发送物品不合法(物品白名单兑换请求被拒绝)";
            } else if (result.equals("12")) {
                result += "发送物品不合法(客户端兑换请求被拒绝)";
            } else if (result.equals("-1")) {
                result += "客户端签名异常";
            } else if (result.equals("-2")) {
                result += "网络错误，请稍候重发。";
            } else if (result.equals("-3")) {
                result += "https连接失败";
            } else if (result.equals("0")) {
                result += "请求成功";
            } else {
//                result += "请求失败，返回HTTP请求状态码";
            }
        } else if (flag == 1) {
            if (result.equals("1")) {
                result += "帐号不存在";
            } else if (result.equals("2")) {
                result += "密码错误";
            } else if (result.equals("3")) {
                result += "服务器不存在";
            } else if (result.equals("4")) {
                result += "奖品不存在";
            } else if (result.equals("5")) {
                result += "积分不够";
            } else if (result.equals("6")) {
                result += "角色名不存在";
            } else if (result.equals("7")) {
                result += "角色不属于该帐号";
            } else if (result.equals("8")) {
                result += "给角色发送奖品时失败";
            } else if (result.equals("-1")) {
                result += "其他错误，很可能是网络通讯错误";
            } else if (result.equals("-2")) {
                result += "当前应用下的物品兑换请求处于拒绝状态";
            } else if (result.equals("-3")) {
                result += "客户端兑换请求处于拒绝状态";
            } else if (result.equals("-4")) {
                result += "白名单验证异常";
            } else if (result.equals("-5")) {
                result += "AU接口调用异常";
            } else if (result.equals("0")) {
                result += "发送成功";
            } else {
//                result += "请求失败，返回HTTP请求状态码";
            }
        }
      request .setAttribute("result_r",result);
%>
${result_r}