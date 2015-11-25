<%@ page import="com.david.web.pppppp.entity.Task" %>
<%@ page import="java.util.List" %>
<%@ page import="org.springframework.web.context.WebApplicationContext" %>
<%@ page import="org.springframework.web.context.ContextLoader" %>
<%@ page import="com.david.web.pppppp.service.task.TaskService" %>
<%--
  Created by IntelliJ IDEA.
  User: czw
  Date: 13-7-15
  Time: 下午5:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<%
    WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();

    TaskService taskService = (TaskService) wac.getBean("taskService");

    List<Task> tasks = taskService.getAllTask();
    System.out.println(tasks.size());

%>
<textarea>
    <% for (Task task : tasks) {
        String tg = task.getTrigger_group();
        tg = tg.replace('|',',');
        String[] ps = tg.split(",");
        String dataSource = ps[0];
        String type = ps[1];
        int itype = Integer.parseInt(type);
        String trigger_name = task.getTrigger_name();

        int roleIdType = 1;
        int sent_type = 1;


        if (itype == 2) {
            roleIdType = 2;
        } else if (itype == 3) {
            sent_type = 2;
            roleIdType =2;
        } else if (itype == 4) {
            sent_type = 3;
        }

    %>
    insert into event_send_prize_properties
    (table_name,datasource,role_id_type,send_type,send_interface,send_check,create_time,status)
    values ('<%=trigger_name%>','<%=dataSource%>',<%=roleIdType%>,<%=sent_type%>,1,0,'2013-07-15 18:00:00',0) on
    duplicate key update status = 1;
<%}%>
</textarea>
</body>
</html>