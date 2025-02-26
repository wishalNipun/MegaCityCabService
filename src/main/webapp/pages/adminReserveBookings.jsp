<%--
  Created by IntelliJ IDEA.
  User: wishal siriwardana
  Date: 2/25/2025
  Time: 10:36 PM
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

    String alertType = (String) session.getAttribute("alert");
    String message = (String) session.getAttribute("message");

    if (alertType != null && message != null) {
        session.removeAttribute("alert");
        session.removeAttribute("message");
    }
%>
<html>
<head>
    <title>Reserve Bookings Management</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/style/AdminReserveBookings.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;700&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Rock+Salt&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <script>
        $(document).ready(function () {
            <% if (alertType != null && message != null) { %>
            Swal.fire({
                icon: '<%= alertType.equals("success") ? "success" : "error" %>',
                title: '<%= alertType.equals("success") ? "Success!" : "Error!" %>',
                text: '<%= message %>',
                showConfirmButton: true
            });
            <% } %>

        });
        function submitForm(selectElement) {
            var form = selectElement.closest('form'); // Get the form closest to the select element
            form.submit(); // Submit the form
        }
        <%--function updateBookingStatus(select) {--%>
        <%--    var bookingId = select.getAttribute('data-id');--%>
        <%--    var newStatus = select.value;--%>
        <%--    $.ajax({--%>
        <%--        url: '${pageContext.request.contextPath}/bookings',--%>
        <%--        type: 'POST',--%>
        <%--        data: {--%>
        <%--            id: bookingId,--%>
        <%--            status: newStatus--%>
        <%--        },--%>
        <%--        success: function(response) {--%>
        <%--            if (response === 'success') {--%>
        <%--                Swal.fire('Status Updated!', '', 'success');--%>
        <%--            } else {--%>
        <%--                Swal.fire('Error!', response, 'error');--%>
        <%--            }--%>
        <%--        }--%>
        <%--    });--%>
        <%--}--%>

    </script>
</head>
<body style="background-color: #ededede0">
<main id="AdminReserveBookings" class="container-fluid.p0">
    <section>
        <div>Mega City Cab Service</div>
        <div>
            <h2>Admin</h2>
            <img src="<%= request.getContextPath() %>/assets/img/adminfaceUser.png">
            <h1>Wishal Nipun Siriwardana</h1>
        </div>
        <div>
            <div><h1><a href="${pageContext.request.contextPath}/pages/adminDashboard.jsp"><i
                    class="fas fa-th-large"></i> DashBoard</a></h1></div>
            <div><h1><a href="login.jsp"><i class="fa-solid fa-user"></i> Customer</a></h1></div>
            <div><h1><a href="${pageContext.request.contextPath}/vehicles"><i
                    class="fa-solid fa-car"></i> Vehicles</a></h1></div>
            <div><h1><a href="${pageContext.request.contextPath}/drivers"><i class="fas fa-male"></i> Drivers</a></h1>
            </div>
            <div><h1><a href="${pageContext.request.contextPath}/bookings?action=viewBookings"><i
                    class="fa-solid fa-calendar-days"></i> Reserve Bookings</a></h1></div>
            <div><h1><a href="login.jsp"><i class="fas fa-money-bill"></i> Payment</a></h1></div>
            <div><h1><a href="login.jsp"><i class="fas fa-chart-area"></i> Bills</a></h1></div>
            <div><h1><a href="login.jsp"><i class="fas fa-male"></i>User</a></h1></div>
            <div><h1 style=" color: darkred;"><a href="${pageContext.request.contextPath}/logout"><i
                    class="fas fa-sign-out-alt"></i> log Out</a></h1>
            </div>
        </div>

    </section>
    <section>
        <div>
            <h1>Reserve Bookings Management</h1>
        </div>

        <div class="table-responsive" style="padding: 1rem">
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
                        <td>
                            <form action="${pageContext.request.contextPath}/bookings" method="POST">
                                <input type="hidden" name="id" value="${booking.id}">
                                <select class="form-select form-select-sm" name="status" onchange="submitForm(this)">
                                    <option value="PENDING" <c:if test="${booking.status == 'PENDING'}">selected</c:if>>
                                        PENDING
                                    </option>
                                    <option value="CONFIRMED"
                                            <c:if test="${booking.status == 'CONFIRMED'}">selected</c:if>>CONFIRMED
                                    </option>
                                    <option value="CANCELLED"
                                            <c:if test="${booking.status == 'CANCELLED'}">selected</c:if>>CANCELLED
                                    </option>
                                </select>
                            </form>
                        </td>
                        <td>vehicle details</td>
                        <td>${booking.formattedCreatedDate}</td>
                        <td>${booking.formattedUpdatedDate}</td>

                            <%--                        <td>--%>
                            <%--                            <!-- Show vehicle details in a nested table -->--%>
                            <%--                            <button class="btn btn-info btn-sm" data-bs-toggle="collapse" data-bs-target="#vehicleDetails${booking.id}">--%>
                            <%--                                View Vehicle--%>
                            <%--                            </button>--%>
                            <%--                            <div id="vehicleDetails${booking.id}" class="collapse mt-3">--%>
                            <%--                                <table class="table table-sm table-bordered">--%>
                            <%--                                    <thead>--%>
                            <%--                                    <tr>--%>
                            <%--                                        <th>Vehicle Type</th>--%>
                            <%--                                        <th>Model</th>--%>
                            <%--                                        <th>Plate Number</th>--%>
                            <%--                                        <th>Driver</th>--%>
                            <%--                                    </tr>--%>
                            <%--                                    </thead>--%>
                            <%--                                    <tbody>--%>
                            <%--                                    <c:forEach var="vehicle" items="${booking.vehicleList}">--%>
                            <%--                                        <tr>--%>
                            <%--                                            <td>${vehicle.vehicleType}</td>--%>
                            <%--                                            <td>${vehicle.model}</td>--%>
                            <%--                                            <td>${vehicle.plateNumber}</td>--%>
                            <%--                                            <td>${vehicle.driverName}</td>--%>
                            <%--                                        </tr>--%>
                            <%--                                    </c:forEach>--%>
                            <%--                                    </tbody>--%>
                            <%--                                </table>--%>
                            <%--                            </div>--%>
                            <%--                        </td>--%>

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
    </section>
</main>
</body>
</html>
