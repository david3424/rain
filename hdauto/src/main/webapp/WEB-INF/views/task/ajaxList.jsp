<%@ page language="java"
         pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<%--<head>
    <meta charset="utf-8">
    <title>调度任务ajax列表</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="Content-Type" content="text/html; charset=urf-8">
    <meta name="description" content="">
    <meta name="author" content="">

    <link href="${ctx}/components/bootstrap22/docs/assets/css/bootstrap.css" rel="stylesheet">
    <style type="text/css">
            /*  body {
              padding-top: 60px;
              padding-bottom: 40px;
            }*/
    </style>
    <link href="${ctx}/components/bootstrap22/docs/assets/css/bootstrap-responsive.css" rel="stylesheet">
    &lt;%&ndash;<link href="${ctx}/components/bootstrap22/docs/assets/css/docs.css" rel="stylesheet">&ndash;%&gt;
    &lt;%&ndash;<link href="${ctx}/components/bootstrap22/docs/assets/js/google-code-prettify/prettify.css" rel="stylesheet">&ndash;%&gt;

    <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <!--<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

    <!-- Le fav and touch icons -->
    <link rel="shortcut icon" href="${ctx}/components/bootstrap22/docs/assets/ico/favicon.ico">
    <link rel="apple-touch-icon-precomposed" sizes="144x144"
          href="${ctx}/components/bootstrap22/docs/assets/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114"
          href="${ctx}/components/bootstrap22/docs/assets/ico/apple-touch-icon-114-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="72x72"
          href="${ctx}/components/bootstrap22/docs/assets/ico/apple-touch-icon-72-precomposed.png">
    <link rel="apple-touch-icon-precomposed"
          href="${ctx}/components/bootstrap22/docs/assets/ico/apple-touch-icon-57-precomposed.png">
</head>--%>
<body>

<%--  <p>
    <a href="${pageContext.request.contextPath}/add.jsp">添加调度任务</a>
</p>--%>

<%--<div class="navbar navbar-inverse navbar-fixed-top">
    <div class="navbar-inner">
        <div class="container">
            <a class="brand" href="list.jsp#">后台发奖系统</a>
            <ul class="nav">
                <li class="active"><a href="${pageContext.request.contextPath}/WEB-INF/views/quartz/add.jsp">首页</a>
                </li>
                <li><a href="${pageContext.request.contextPath}/JobProcessServlet?jobtype=100&action=query">列表</a></li>
                <li><a href="test.html#contact">状态</a></li>
            </ul>
            <form class="navbar-form pull-right">
                <input class="span2" type="text" placeholder="邮箱">
                <input class="span2" type="password" placeholder="密码">
                <button type="submit" class="btn">登录</button>
            </form>
            <!--</div>&lt;!&ndash;/.nav-collapse &ndash;&gt;-->
        </div>
    </div>
</div>--%>
<%--<div class="container">
    <!-- Main hero unit for a primary marketing message or call to action -->
    <div class="hero-unit">

        <h1>定时器列表管理</h1>

        <p>定时器可自定义添加，不用的定时器请暂停或者直接删除。corn表达式请移步到具体<abbr title="corn表达式说明">说明页</abbr>。</p>

        <p><a class="btn btn-primary btn-inverse">查看更多 &raquo;</a></p>
    </div>--%>

    <%--<table class="table table-bordered table-hover">
        <caption>状态跟踪表</caption>
        <thead>
        <tr>
            <th>名称</th>
            <th>分组</th>
            <th>下次执行时间</th>
            <th>上次执行时间</th>
            <th>优先级</th>
            <th>状态</th>
            <th>类型</th>
            <th>开始时间</th>
            <th>结束时间</th>
            <th>动作命令</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="map" items="${list}">
            <tr>
                <td> ${map.trigger_name}</td>
                <td>${map.trigger_group}</td>
                <td><tags:util date="${map.next_fire_time}"/></td>
                <td><tags:util date="${map.prev_fire_time}"/></td>
                <td>${map.priority}</td>
                <td>${map.statu}</td>
                <td>${map.trigger_type}</td>
                <td><tags:util date="${map.start_time}"/></td>
                <td>${map.end_time}</td>
                <td>
                    <input type="button" id="pause" value="暂停"
                           onclick="doCmd('pause','${map.trigger_name}','${map.trigger_group}','${map.trigger_state}')">
                    <input type="button" id="resume" value="恢复"
                           onclick="doCmd('resume','${map.trigger_name}','${map.trigger_group}','${map.trigger_state}')">
                    <input type="button" id="remove" value="删除"
                           onclick="doCmd('remove','${map.trigger_name}','${map.trigger_group}','${map.trigger_state}')">
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>--%>
    <!--数据显示-->
    <div id="data"></div>
    <div>
        <div id="page"></div>
        <div><input type="text" max="2" id="page_number" style=" width:30px"/><a href="javascript:void(0)"
                                                                                 id="jump">跳转</a></div>
        <div><input type="text" max="10" id="search_text"/><a href="javascript:void(0)"
                                                              id="search">搜索</a></div>
    </div>

    <footer>
        <p>&copy; Company 2012</p>
    </footer>

<%--</div>--%>


<%--<script src="${ctx}/components/bootstrap22/docs/assets/js/jquery.js"></script>--%>
<script src="${ctx}/modual/js/jquery-1.7.2.min.js"></script>
<script src="${ctx}/js/jquery.tmpl.js"></script>
<script src="${ctx}/js/jquery.pagination.js"></script>
<script src="${ctx}/js/ajaxpage-jqplugin.js"></script>
<script src="${ctx}/components/bootstrap22/docs/assets/js/bootstrap.min.js"></script>

<script type="text/x-jquery-tmpl" id="tmpl">
    alert(111)
    <ul>
        {{each(i,a) records}}
        <li>
            alert(${a.trigger_name})
            <p>trigger_name：${a.trigger_name}</p>

            <p>trigger_group：${a.trigger_group}</p>

            <p>next_fire_time：${a.next_fire_time}</p>

            <p>priority：${a.priority}</p>

            <p>statu：${a.statu}</p>

            <p>trigger_type：${a.trigger_type}</p>
        </li>
        {{/each}}
    </ul>
</script>
<!--分页数据html模板结束-->
<script type="text/javascript">
    //分页参数配置
    var paginationOptions = {
        url:"/task/ajax",
        pageSize:1,
        current_page:0,
        data_area_id: "data",
        tmpl_script: "tmpl",
        page_area_id: "page",
        num_display_entries:10,
        num_edge_entries:0,
        prev_text:"上一页",
        next_text:"下一页",
        link_to:"javascript:void(0);",
        ellipse_text:""
    }

    $(function() {
        $.hd.pagination(paginationOptions);
        bindPageJump();
        bindSearchButton();
    });
    //绑定跳转按钮函数
    function bindPageJump() {
        $.hd.jump({
            pagination_options : paginationOptions,
            jump_button:"jump",
            page_number:"page_number"
        });
    }
    //绑定搜索按钮函数
    function bindSearchButton() {
        $("#search").click(function() {
            paginationOptions.params = {searchText:$("#search_text").val()};
            $.hd.refreshPagination(paginationOptions);
        });
    }

</script>

</body>
</html>