<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="org.apache.commons.httpclient.HttpClient"%>
<%@page import="org.apache.commons.httpclient.methods.PostMethod"%>
<%@page import="org.apache.commons.httpclient.params.HttpMethodParams"%>
<%@page import="org.apache.commons.httpclient.methods.GetMethod"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="urlconn.jsp" method="post">
url:<input type="text" name="url" size="100"/>
<br/>
<input type="radio" name="type" value="0" checked="checked">get 
<input type="radio" name="type" value="1">post
<input type="submit" value="确定">
</form>
<%
String url = request.getParameter("url");
String typeStr = request.getParameter("type");
if(null == url || url=="" || typeStr==null || typeStr == "")
	return;
int type = Integer.parseInt(typeStr);
HttpClient httpClient = new HttpClient();

if(type==1){
	PostMethod postMethod = new PostMethod(url);
	// 设置请求超时时间(ms)
	//postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 2000);
	
	int statusCode = -1;
	try {
		statusCode = httpClient.executeMethod(postMethod);
		System.out.println(postMethod.getResponseBodyAsString());
		out.write(postMethod.getResponseBodyAsString());
	} catch (Exception ex) {
		
		out.write(ex.getMessage());
	} finally {
		// 释放连接
		postMethod.releaseConnection();
	}
	
}else{
	
	GetMethod getMethod = new GetMethod(url);
	int statusCode = -1;
	try {
		statusCode = httpClient.executeMethod(getMethod);
		System.out.println(getMethod.getResponseBodyAsString());
		out.write(getMethod.getResponseBodyAsString());
	} catch (Exception ex) {
		
		out.write(ex.getMessage());
	} finally {
		// 释放连接
		getMethod.releaseConnection();
	}
	
}
%>
</body>
</html>