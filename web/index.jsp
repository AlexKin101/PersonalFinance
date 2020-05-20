<%@ page import="dao.SystemUser" %>
<%@ page import="dao.SystemRecord" %><%--
  Created by IntelliJ IDEA.
  User: Eden
  Date: 20/4/14
  Time: 22:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
    This is Web Server Test page <br>
    <%=SystemUser.getAllUserInfo()%><br>
    <%=SystemRecord.getAllRecordInfo("1")%><br>
    <%=SystemRecord.recordQuery("1")%><br>
    <%=SystemRecord.recordCount_Total("1")%>
  </body>
</html>
