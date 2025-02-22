<%--
  Created by IntelliJ IDEA.
  User: wishal siriwardana
  Date: 2/22/2025
  Time: 5:48 PM
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
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/style/customerBooking.css">
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
            <li><a href="#">Booking Detail</a></li>
            <li><a href="#">Check Out</a></li>
            <li><a style="color: brown" href="${pageContext.request.contextPath}/pages/login.jsp">Log Out</a></li>
        </ul>
    </nav>
</header>
<main id="Booking" class="container-fluid">
    <div>
        <h1>Unbeatable price.</h1>
        <h2><span>Booking Vehicles</span> & Expert Services</h2>
        <p>Recommendation for You</p>
        <div id="cardsStore">
            <c:forEach var="vehicle" items="${availableVehicleList}">
            <div class="card" style="width: 14rem;">
                <img src="data:image/jpeg;base64,${vehicle.getBase64Img()}" class="card-img-top" alt="Vehicle Image">
                <hr>
                <div class="card-body border-bottom-0">
                    <h5 class="card-title"> Model - ${vehicle.getModel()}</h5>
                    <p class="card-text"> Vehicle Type - ${ vehicle.getVehicleType() }</p>
                    <button class="viewDetail btn btn-light">ViewDetail</button>
                </div>
                <hr>
                <div class="card-body text-center">
                    <div>
                        <div>
                            <h5>Number Of Passengers</h5>
                            <p>${vehicle.getNumberOfPassengers() }</p>
                        </div>
                        <div>
                            <h5>Price per KM</h5>
                            <p>${ vehicle.getPricePerKm() }</p>
                        </div>
                    </div>
                    <button class="btn btn-primary">Add To Booking</button>
                </div>

            </div>
            </c:forEach>


        </div>
    </div>

</main>
</body>
</html>
