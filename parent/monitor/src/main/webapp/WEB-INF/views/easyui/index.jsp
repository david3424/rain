<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<jsp:include page="/WEB-INF/layouts/default_easy.jsp"/>
<html>
    <head>
        <meta charset="utf-8">
        <title>Demo</title>

        <script type="text/javascript">
            $(function(){
                $('#tt').tree({
                        lines: true,
                        fit: true,
                        url: '/demo/left_menu_data.json',
                        onClick: function(node){
                            if(node.attributes && node.attributes.url){
                                $('#ifrm').attr('src', node.attributes.url);
                            }
                        },
                        customAttr: {
                        expandOnNodeClick: true
                    }
                }).tree('followCustomHandle');
            });

            function doExpand(){
                $('#tt').tree('expandAll');
            }

            function doCollapse(){
                $('#tt').tree('collapseAll');
            }
        </script>
    </head>
    <body class="easyui-layout">
        <div data-options="region:'west', split: true, minWidth: 250, maxWidth:300">
            <div class="easyui-panel" data-options="fit: true, border: false">
                <div class="easyui-layout" data-options="fit: true">
                    <div data-options="region: 'north', border: false, minHeight: 30, maxHeight: 30">
                        <div class="datagrid-toolbar" style="text-align: right">
                            <a href="javascript: void(0)" class="easyui-linkbutton" data-options="plain: true, iconCls: 'icon-expand'" onclick="doExpand()">展开</a>
                            <a href="javascript: void(0)" class="easyui-linkbutton" data-options="plain: true, iconCls: 'icon-collapse'" onclick="doCollapse()">收缩</a>
                        </div>
                    </div>
                    <div data-options="region: 'center', border: false">
                        <ul id="tt"></ul>
                    </div>
                </div>
            </div>
        </div>
        <div data-options="region:'south', minHeight: 30, maxHeight: 30">
            <div style="text-align: center; padding-top: 5px;">
                作者：爱看书不识字
            </div>
        </div>
        <div data-options="region:'center'">
            <iframe id="ifrm" src="" frameborder="0" height="96%" width="98%" marginheight="0" marginwidth="0" style="padding: 10px;"></iframe>
        </div>
    </body>
</html>