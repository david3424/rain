<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<sec:authentication property="authorities" var="roles" scope="page" />
<c:set  var="menuMap" value="<%=userMenuMap%>" scope="page"/>
<c:set  var="sysGameId" value="<%=sysGameId%>" scope="page"/>
<c:set  var="gameId" value="<%=gameId%>" scope="page"/>
<c:set  var="menuPath" value="<%=menuPath%>" scope="page"/>

<div id="sidebar-nav-menu" class="sidebar-nav">
	<!-- 添加一个显示当前所处的游戏名称 -->
    <c:if test="${not empty menuMap[gameId]}">
    <c:forEach items="${menuMap[gameId]}" var="menuEntityMap" varStatus="status">
    	<div class="accordion-group">
    		<div class="accordion-heading"><a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#collapse${status.count}"><c:out value="${menuEntityMap.key}" />&raquo;</a></div>
    		<div id="collapse${status.count}" class="accordion-body collapse in">
    			<div class="accordion-inner">
    				<ul class="nav nav-list">
    				<c:forEach items="${menuEntityMap.value}" var="menuBean">
    					<li><a href="<c:url value='${menuPath}${menuBean.resourceUrl}'/>"><c:out value="${menuBean.resourceName}" /></a></li>
    				</c:forEach>
    				</ul>
    			</div>
    		</div>
    	</div>
    </c:forEach>
    </c:if>
    <c:if test="${not empty menuMap[sysGameId]}">
    <c:forEach items="${menuMap[sysGameId]}" var="menuEntityMap" varStatus="status">
    	<div class="accordion-group">
    		<div class="accordion-heading"><a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion2" href="#collapseSys${status.count}"><c:out value="${menuEntityMap.key}"></c:out>&raquo;</a></div>
    		<div id="collapseSys${status.count}" class="accordion-body collapse in">
    			<div class="accordion-inner">
    				<ul class="nav nav-list">
    				<c:forEach items="${menuEntityMap.value}" var="menuBean">
    					<li><a href="<c:url value='${menuPath}${menuBean.resourceUrl}'/>"><c:out value="${menuBean.resourceName}" /></a></li>
    				</c:forEach>
    				</ul>
    			</div>
    		</div>
    	</div>
    </c:forEach>
    </c:if>
</div>

<script type="text/javascript">
    // 初始化菜单，增加点击事件
    $(function() {
//    	changeMenu();
    });

    function changeMenu(){
    	var url = window.location.href; //当前页面地址
    	var aTags = $('#sidebar-nav-menu li a');
    	for(var i=0; i< aTags.length; i++){
    		var aTag = aTags[i];
    		var hrefValue = $(aTag).attr('href');
    		if(url == hrefValue){
    			$("#sidebar-nav-menu  li").removeClass("active"); // 清除全部被选中样式
    			$(aTag).parent().addClass("active");
    			var sid = ($(aTag).parent().parent().parent().parent().parent().find('div:first-child').find('a').attr("href")).substr(1); //这是把自己所在的目录隐藏了
    			closeMenu(sid);
    			break;
    		}
    	}
    }
    
    //除了sid的菜单，其他都关闭
    function closeMenu(sid){
    	var accordionBodys = $(".accordion-body");
    	for(var i=0; i< accordionBodys.length; i++){
    		var accordionBody = accordionBodys[i];
    		var accordionBodyId = accordionBody.id;
    		if(accordionBodyId!=sid){ //关闭
    			$(accordionBody).css('height','0px');
    			$(accordionBody).removeClass('in');
    		}else{
    			$(accordionBody).css('height','auto');
    			$(accordionBody).addClass('in');
    		}
    	}
    }
</script>
