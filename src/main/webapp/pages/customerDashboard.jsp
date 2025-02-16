<%--
  Created by IntelliJ IDEA.
  User: wishal siriwardana
  Date: 2/12/2025
  Time: 2:56 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%
    HttpSession sessionObj = request.getSession(false);
    if (sessionObj == null || sessionObj.getAttribute("user") == null) {
        response.sendRedirect(request.getContextPath() + "/pages/login.jsp");
        return;
    }
%>
<html>
<head>
    <title>customer Dashboard</title>
</head>
<body>
    <h1>customer DashBoard</h1>

</body>
</html>
