<%--
  Created by IntelliJ IDEA.
  User: wishal siriwardana
  Date: 2/12/2025
  Time: 2:54 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="com.megacitycabservice.model.User" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%
    HttpSession sessionObj = request.getSession(false);
    if (sessionObj == null || sessionObj.getAttribute("user") == null) {
        response.sendRedirect(request.getContextPath() + "/pages/login.jsp");
        return;
    }
    User user = (User) sessionObj.getAttribute("user");

    Integer pendingBookingsCount = (Integer) request.getAttribute("pendingBookingsCount");
    Integer confirmedBookingsCount = (Integer) request.getAttribute("confirmedBookingsCount");
    Integer availableVehicleCount = (Integer) request.getAttribute("availableVehicleCount");
    Integer busyVehicleCount = (Integer) request.getAttribute("busyVehicleCount");
    Integer driverCount = (Integer) request.getAttribute("driverCount");
    Integer customerCount = (Integer) request.getAttribute("customerCount");

    if (pendingBookingsCount == null) pendingBookingsCount = 0;
    if (confirmedBookingsCount == null) confirmedBookingsCount = 0;
    if (availableVehicleCount == null) availableVehicleCount = 0;
    if (busyVehicleCount == null) busyVehicleCount = 0;
    if (driverCount == null) driverCount = 0;
    if (customerCount == null) customerCount = 0;

%>
<html>
<head>
    <title>admin DashBoard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/style/AdminDashBoard.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;700&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Rock+Salt&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
</head>
<body style="background-color: #ededede0">
<main id="adminDashBoard" class="container-fluid.p0">
    <section>
        <div>Mega City Cab Service</div>
        <div>
            <h2>Admin</h2>
            <img src="<%= request.getContextPath() %>/assets/img/adminfaceUser.png">
            <h1><%= user.getUsername() %></h1>
        </div>
        <div>
            <div><h1><a href="${pageContext.request.contextPath}/adminDashBoard"><i class="fas fa-th-large"></i> DashBoard</a></h1></div>
            <div><h1><a href="${pageContext.request.contextPath}/customers"><i class="fa-solid fa-user"></i> Customer</a></h1></div>
            <div><h1><a href="${pageContext.request.contextPath}/vehicles"><i class="fa-solid fa-car"></i> Vehicle</a></h1></div>
            <div><h1><a href="${pageContext.request.contextPath}/drivers"><i class="fas fa-male"></i> Drivers</a></h1></div>
            <div><h1><a href="${pageContext.request.contextPath}/bookings?action=viewBookings"><i class="fa-solid fa-calendar-days"></i> Reserve Bookings</a></h1></div>
            <div><h1><a href="${pageContext.request.contextPath}/bookings?action=availablePayBookings"><i class="fas fa-money-bill"></i> Payment</a></h1></div>
            <div><h1><a href="${pageContext.request.contextPath}/bills"><i class="fas fa-chart-area"></i> Bills</a></h1></div>
            <div><h1><a href="${pageContext.request.contextPath}/users"><i class="fas fa-male"></i> User</a></h1></div>
            <div><h1 style=" color: darkred;"><a href="${pageContext.request.contextPath}/logout"><i class="fas fa-sign-out-alt"></i> log Out</a></h1>
            </div>
        </div>

    </section>
    <section >
        <div>
            <h1>Summery</h1>
        </div>
        <div>
            <div>
                <h2>Pending Bookings Count</h2>
                <h1><%= pendingBookingsCount %></h1>
            </div>
            <div>
                <h2>Confirmed Bookings (Running) Count</h2>
                <h1><%= confirmedBookingsCount %></h1>
            </div>
            <div>
                <h2>Available Vehicle Count</h2>
                <h1><%= availableVehicleCount %></h1>
            </div>
            <div>
                <h2>Busy Vehicle Count</h2>
                <h1><%= busyVehicleCount %></h1>
            </div>
            <div>
                <h2>Drivers Count</h2>
                <h1><%= driverCount %></h1>
            </div>
            <div>
                <h2>Customer Count</h2>
                <h1><%= customerCount %></h1>
            </div>
        </div>

    </section>

</main>
</body>
</html>
