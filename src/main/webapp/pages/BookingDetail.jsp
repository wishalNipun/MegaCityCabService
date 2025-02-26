<%--
  Created by IntelliJ IDEA.
  User: wishal siriwardana
  Date: 2/25/2025
  Time: 12:26 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    <title>Car Service Web Page</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/style/CustomerBookingDeatils.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;700&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Rock+Salt&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<body>
<header>
    <nav id="Nav">
        <ul>
            <li><a href="${pageContext.request.contextPath}/pages/customerDashboard.jsp">Home</a></li>
            <li><a href="${pageContext.request.contextPath}/bookings">Booking</a></li>
            <li><a href="${pageContext.request.contextPath}/pages/BookingDetail.jsp">Booking Detail</a></li>
            <li><a style="color: brown" href="${pageContext.request.contextPath}/logout">Log Out</a></li>
        </ul>
    </nav>
</header>
<main id="bookingDetail" class="container-fluid">
    <div>
        <h2>Booking Details</h2>
    </div>
    <div>
        <table class="table table-hover">
            <table class="table table-hover">
                <thead class="table-primary">
                <tr>
                    <th scope="col">Booking Number</th>
                    <th scope="col">Customer</th>
                    <th scope="col">Pickup Location</th>
                    <th scope="col">Drop Location</th>
                    <th scope="col">Distance (KM)</th>
                    <th scope="col">Fee</th>
                    <th scope="col">Status</th>
                    <th scope="col">Vehicle Details</th>
                    <th scope="col">Created Date</th>
                    <th scope="col">Last Updated Date</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="booking" items="${bookingList}">
                    <tr>
                        <td>${booking.bookingNumber}</td>
                        <td>${booking.customerId}</td>
                        <td>${booking.pickupLocation}</td>
                        <td>${booking.dropLocation}</td>
                        <td>${booking.distanceKm}</td>
                        <td>${booking.fee}</td>
                        <td>${booking.status}</td>
                        <td>vehicle details</td>
                        <td>${booking.formattedCreatedDate}</td>
                        <td>${booking.formattedUpdatedDate}</td>
                    </tr>
                </c:forEach>
                <c:if test="${empty bookingList}">
                    <tr>
                        <td colspan="11" class="text-center">No Bookings Available</td>
                    </tr>
                </c:if>
                </tbody>
        </table>
    </div>
</main>
</body>
</html>
