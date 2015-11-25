<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.Map"%>
<%@page import="com.david.web.pppppp.common.util.memcached.MemcachedManager"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="org.springframework.web.context.WebApplicationContext" %>
<%--
  Created by IntelliJ IDEA.
  User: zhaozhiyong
  Date: 2011-10-25
  Time: 13:38:56
  完美时空 游戏活动管理平台
  作者：赵智勇
--%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head><title>memcached  Test页</title></head>


<body>

<br>
<%
    WebApplicationContext webApplicationContext =WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
	MemcachedManager manager = webApplicationContext.getBean("memcachedManager",MemcachedManager.class);
	Map stat = 	manager.stats();
	String key = request.getParameter("key");
	String delete = request.getParameter("delete");
	Object value = null;
	if(StringUtils.isNotBlank(delete)){
		manager.delete(key);
	}
	
	if(StringUtils.isNotBlank(key)){
		value = manager.get(key);
	}

    String id=session.getId();
    String localIP=request.getLocalAddr();
    String remoteIp=request.getRemoteAddr();


 %>
<%if(stat==null||stat.size()==0){
	out.print("已停止!");
}else{
	out.print("正常运行!");
} %>

<form action="/example/memcached">
	<input type="text" name="key" value="<%=key==null?"":key %>" /> <br>
	<input type="text" value="<%=value!=null?value.toString():"" %>" disabled="disabled"> 
	<%=value==null?"":"<input type='submit' name='delete' value='删除' > " %>
	<br>
	<input type="submit" >
</form>

<div>
   sessionid:<%=id%>>;
   localIP:<%=localIP%>>;
   remoteIP:<%=remoteIp%>>;

</div>






<table>


	<%
		Iterator entryIt = stat.keySet().iterator();
		while(entryIt.hasNext()){
			String temp = (String)entryIt.next();
	%>
		<tr>
			<td width="80px;"> <%= temp%></td>
			<td > <%
					Map proMap =  (Map)stat.get(temp);
					
					Iterator entryItt = proMap.keySet().iterator();
					
					
					while(entryItt.hasNext()){
					String temmp = (String)entryItt.next();
				 %>
                <%=temmp.equals("limit_maxbytes")?"分配给memcache的内存:"+proMap.get(temmp)+"<br/>":"" %>
				 <%=temmp.equals("bytes")?"使用总内存量:"+proMap.get(temmp)+"<br/>":"" %>
				 <%=temmp.equals("total_items")?"总共含有的key值:"+proMap.get(temmp)+"<br/>":"" %>
				 <%=temmp.equals("version")?"当前版本:"+proMap.get(temmp)+"<br/>":"" %>
				 <%=temmp.equals("bytes_written")?"写入总字节数:"+proMap.get(temmp)+"<br/>":"" %>
				 <%=temmp.equals("bytes_read")?"读取总字节数:"+proMap.get(temmp)+"<br/>":"" %>
				 <%=temmp.equals("total_connections")?"连接总量:"+proMap.get(temmp)+"<br/>":"" %>
                <%=temmp.equals("threads")?"当前线程数:"+proMap.get(temmp)+"<br/>":"" %>
                <%=temmp.equals("cmd_get")?"get命令总请求数:"+proMap.get(temmp)+"<br/>":"" %>
                <%=temmp.equals("cmd_set")?"set命令总请求数:"+proMap.get(temmp)+"<br/>":"" %>
                <%=temmp.equals("get_hits")?"总命中次数:"+proMap.get(temmp)+"<br/>":"" %>
                <%=temmp.equals("get_misses")?"未命中次数:"+proMap.get(temmp)+"<br/>":"" %>
				 <%} %>
			</td>
		</tr>
	<%} %>
</table>


<%--<a href="cacheflush.jsp" target="_blank">清空缓存</a>--%>
</body>
</html>
