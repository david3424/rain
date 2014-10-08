<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*" %>
<%@ page import="org.david.rain.security.tool.SessionUser" %>
<%@ page import="org.david.rain.model.GameBean" %>
<%@ page import="org.david.rain.model.Menu" %>
<%
	HttpSession httpSession = request.getSession(true);
	String currentGameShort = (String)httpSession.getAttribute(SessionUser.SESSION_CURRENT_GAME_SHORT);
	SessionUser sessionUser = (SessionUser) httpSession.getAttribute(SessionUser.SESSION_ROOT_KEY);
    if(null == sessionUser){
        response.sendRedirect("/login");
        return;
    }
	Map<String, GameBean> gameMap = sessionUser.getGameMap();
	GameBean gameBean = gameMap.get(currentGameShort);
	Integer sysGameId = 0;
	Integer gameId = -1;
	String gameName = "";
	Integer isClient = -1; //标识是否分端
	if (gameBean != null) {
		gameId = gameBean.getGameId();
		gameName = gameBean.getGameName();
		isClient = gameBean.getClient();
	}
	
	Map<String, Set<String>> authorityMap = sessionUser.getAuthorityMap();

	Map<Integer, Map<String, List<Menu>>> userMenuMap = sessionUser.getUserMenuMap();

	List<String> authorityGameList = sessionUser.getAuthorityGameList();

	String path = request.getContextPath();
	String basePath;
	String gamePath;
	String menuPath;
    basePath =  path +"/";
    gamePath =  path + "/wanmei/"+currentGameShort+"/";
%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<link rel="stylesheet" type="text/css" href="<c:url value="/js/libs/bootstrap-2.3.2/css/bootstrap.min.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/js/libs/bootstrap-2.3.2/css/bootstrap-responsive.min.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/js/libs/bootstrap-datepicker-master/css/datepicker.css"/>" >
<link rel="stylesheet" type="text/css" href="<c:url value="/js/libs/DataTables-1.9.4/dataTables.bootstrap.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/js/libs/DataTables-1.9.4/extras/media/css/TableTools.css"/>" >
<link rel="stylesheet" type="text/css" href="<c:url value="/js/libs/font-awesome/css/font-awesome.min.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/js/libs/select2-3.3.2/select2.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/style.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/jquery-ui.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/js/libs/jquery-validation/1.10.0/validate.css"/>">

<script type="text/javascript" src="<c:url value="/js/libs/jquery-1.8.3.min.js"/>"></script>
<script type="text/javascript" src="<c:url value='/js/libs/jquery-validation/1.10.0/jquery.validate.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/libs/jquery-validation/1.10.0/messages_bs_zh.js'/>"></script>


<script type="text/javascript" src="<c:url value="/js/libs/bootstrap-2.3.2/js/bootstrap.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/libs/bootstrap-datepicker-master/js/bootstrap-datepicker.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/libs/bootstrap-datepicker-master/js/locales/bootstrap-datepicker.zh-CN.js"/>" charset="UTF-8"></script>


<%--<script type="text/javascript" src="<c:url value="/js/libs/Highstock-1.3.2/js/highstock.js"/>"></script>--%>
<%--<script type="text/javascript" src="<c:url value="/js/libs/Highstock-1.3.2/js/modules/exporting.js"/>"></script>--%>
<%--<script type="text/javascript" src="<c:url value="/js/libs/highcharts/js/highcharts.js"/>"></script> -->--%>
<script type="text/javascript" src="<c:url value="/js/libs/DataTables-1.9.4/jquery.dataTables.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/libs/DataTables-1.9.4/dataTables.bootstrap.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/libs/DataTables-1.9.4/extras/media/js/TableTools.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/libs/DataTables-1.9.4/extras/media/js/FixedColumns.min.js"/>"></script> 
<script type="text/javascript" src="<c:url value="/js/libs/DataTables-1.9.4/extras/media/js/ZeroClipboard.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/libs/select2-3.3.2/select2.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/libs/select2-3.3.2/select2_locale_zh-CN.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/libs/jquery.json-2.2-min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/libs/jquery.dateFormat.js"/>"></script>

<script type="text/javascript" src="<c:url value="/js/libs/jquery.ui.core.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/libs/jquery.ui.widget.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/libs/jquery.ui.position.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/libs/jquery.ui.menu.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/libs/jquery.ui.autocomplete.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/libs/jquery.ui.mouse.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/libs/jquery.ui.draggable.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/apps/common.js"/>"></script>

<script type="text/javascript">
var gameId = '<%=gameId%>';
var gameName = '<%=gameName%>';
var isClient = '<%=isClient%>';

var basePath = '<%=basePath%>';
var gamePath = '<%=gamePath%>';

</script>
