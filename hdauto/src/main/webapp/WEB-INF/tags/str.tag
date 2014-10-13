<%@ tag import="org.apache.commons.lang.time.DateFormatUtils" %>
<%@ tag import="org.apache.commons.lang.StringUtils" %>
<%@tag pageEncoding="UTF-8" %>
<%@ attribute name="str" type="java.lang.String" required="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String str_new = StringUtils.indexOf(str, "&") != -1 ? StringUtils.substringBefore(str, "&") : str;
      request .setAttribute("str_new",str_new);
%>
${str_new}