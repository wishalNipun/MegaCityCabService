<%--
  Created by IntelliJ IDEA.
  User: wishal siriwardana
  Date: 2/12/2025
  Time: 2:54 AM
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
            <h1>Wishal Nipun Siriwardana</h1>
        </div>
        <div>
            <div><h1><a href="${pageContext.request.contextPath}/pages/adminDashboard.jsp"><i class="fas fa-th-large"></i> DashBoard</a></h1></div>
            <div><h1><a href="login.jsp"><i class="fa-solid fa-user"></i> Customer</a></h1></div>
            <div><h1><a href="${pageContext.request.contextPath}/pages/VehicleManagement.jsp"><i class="fa-solid fa-car"></i> Vehicle</a></h1></div>
            <div><h1><a href="${pageContext.request.contextPath}/drivers"><i class="fas fa-male"></i> Drivers</a></h1></div>
            <div><h1><a href="login.jsp"><i class="fa-solid fa-calendar-days"></i> Bookings</a></h1></div>
            <div><h1><a href="login.jsp"><i class="fas fa-money-bill"></i> Payment</a></h1></div>
            <div><h1><a href="login.jsp"><i class="fas fa-chart-area"></i> Bills</a></h1></div>
            <div><h1><a href="login.jsp"><i class="fas fa-male"></i>User</a></h1></div>
            <div><h1 style=" color: darkred;"><a href="${pageContext.request.contextPath}/logout"><i class="fas fa-sign-out-alt"></i> log Out</a></h1>
            </div>
        </div>

    </section>
    <section>
        <div>
            <h1>Summery</h1>
        </div>
        <div>
            <div>
                <h2>Register Users</h2>
                <h1>100</h1>
            </div>
            <div>
                <h2>Bookings</h2>
                <h1>30</h1>
            </div>
            <div>
                <h2>Available Cars</h2>
                <h1>40</h1>
            </div>
            <div>
                <h2>Receive Cars</h2>
                <h1>50</h1>
            </div>
            <div>
                <h2>Active Bookings</h2>
                <h1>25</h1>
            </div>
            <div>
                <h2>Available Drivers</h2>
                <h1>30</h1>
            </div>

        </div>

    </section>
</main>
</body>
</html>
