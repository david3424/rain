<%--
  Created by IntelliJ IDEA.
  User: czw
  Date: 13-12-13
  Time: 上午11:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>首页</title>
    <%@include file="top.jsp"%>
</head>
<body class="easyui-layout">
<%--导航条--%>
<%@include file="north.jsp"%>
<%--树导航--%>
<div data-options="region:'west', title:'导航',split: true, minWidth: 150, maxWidth:250">
                        <ul id="tt" class="easyui-tree"></ul>
                </div>
                <div data-options="region:'south', minHeight: 30, maxHeight: 30">
                    <div style="text-align: center; padding-top: 5px;">
                        @完美世界
                    </div>
                </div>
<%--tabs--%>
<div region="center" border="false">
			<div id="ttx" class="easyui-tabs" fit="true" border="false" plain="true">
				<div title="welcome" href="">welcome</div>
			</div>
		</div>

</body>
</html>