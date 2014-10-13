<%@ tag import="org.apache.commons.lang.time.DateFormatUtils" %>
<%@tag pageEncoding="UTF-8"%>
<%@ attribute name="date" type="java.lang.Long" required="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String datetime = DateFormatUtils.format(date.longValue(), "yyyy-MM-dd HH:mm:ss");
    request .setAttribute("datetime",datetime);
%>
${datetime}