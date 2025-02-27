<%--
  Created by IntelliJ IDEA.
  User: wishal siriwardana
  Date: 2/12/2025
  Time: 2:56 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="com.megacitycabservice.model.User" %>
<%
    HttpSession sessionObj = request.getSession(false);
    if (sessionObj == null || sessionObj.getAttribute("user") == null) {
        response.sendRedirect(request.getContextPath() + "/pages/login.jsp");
        return;
    }
    User user = (User) sessionObj.getAttribute("user");
%>
<html>
<head>
    <title>Car Service Web Page</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/style/customerDashboard.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;700&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Rock+Salt&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
</head>
<body>
<header>
    <nav id="Nav">
        <ul>
            <li><a href="${pageContext.request.contextPath}/pages/customerDashboard.jsp">Home</a></li>
            <li><a href="${pageContext.request.contextPath}/bookings?action=availableVehicles">Booking</a></li>
            <li><a href="${pageContext.request.contextPath}/bookings?action=bookingDeatil&username=<%= user.getUsername() %>">Booking Detail</a></li>
            <li><a style="color: brown" href="${pageContext.request.contextPath}/logout">Log Out</a></li>
        </ul>
    </nav>
</header>
<main id="UserDashBoard">
    <div></div>
    <div></div>
    <div></div>
    <h1>Save Big With
        Our Vehicle Service.</h1>
    <p>Discover <b>Mega City Cab Service</b>
        Your trusted ride! Enjoy reliable, affordable, and comfortable cab services with a variety of car options and
        exclusive local deals.</p>

</main>
</body>
</html>
