<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
    <div class="navbar navbar-inverse navbar-fixed-top">
        <div class="navbar-inner">
            <div class="container">
                <a class="brand" href="#">后台发奖系统</a>
                <ul class="nav">
                    <li class=""><a href="${ctx}/task">首页</a>
                    </li>
                    <%--<li><a href="${ctx}/task/create">添加定时器</a></li>--%>
                    <li class="dropdown">
                        <a href="#" id="drop2" role="button" class="dropdown-toggle" data-toggle="dropdown">补发奖品入口<b
                                class="caret"></b></a>
                        <ul class="dropdown-menu" role="menu" aria-labelledby="drop2">
                            <li><a tabindex="-1" href="${ctx}/task?search_is_kefu=1">客服补发定时器</a></li>
                            <li><a tabindex="-1" href="${ctx}/resend/add">添加补发单子</a></li>
                            <li><a tabindex="-1" href="${ctx}/resend/orderlist">补发列表</a></li>
                            <li class="divider"></li>
                            <li><a tabindex="-1" href="${ctx}/resend/">补发奖品数据</a></li>
                        </ul>
                    </li>
                    <li class="dropdown">
                        <a href="#" id="drop3" role="button" class="dropdown-toggle" data-toggle="dropdown">功能测试页<b
                                class="caret"></b></a>
                        <ul class="dropdown-menu" role="menu" aria-labelledby="drop3">
                            <li><a tabindex="-1" href="${ctx}/test/message/toSend">活动短信接口1</a></li>
                        </ul>
                    </li>

                    <li><a href="${ctx}/task/mainSwitch">发奖总开关</a></li>
                </ul>
                <%--<form class="navbar-form pull-right">
                    <input class="span2" type="text" placeholder="邮箱">
                    <input class="span2" type="password" placeholder="密码">
                    <button type="submit" class="btn">登录</button>
                </form>--%>
                <!--</div>&lt;!&ndash;/.nav-collapse &ndash;&gt;-->
            </div>
        </div>
    </div>