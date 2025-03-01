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
<%@ page import="com.megacitycabservice.model.Customer" %>
<%@ page import="com.megacitycabservice.model.User" %>
<%
    HttpSession sessionObj = request.getSession(false);
    if (sessionObj == null || sessionObj.getAttribute("user") == null) {
        response.sendRedirect(request.getContextPath() + "/pages/login.jsp");
        return;
    }

    User user = (User) sessionObj.getAttribute("user");

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
            var form = selectElement.closest('form');
            form.submit();
        }

        function fetchCustomerDetails(customerId) {
            $.ajax({
                url: "${pageContext.request.contextPath}/customers?action=getCustomerDetail",
                type: "GET",
                data: {customerId: customerId},
                dataType: "json",
                success: function (response) {
                    // Populate modal fields with the fetched customer data
                    $('#customerId').text(response.customerId);
                    $('#customerName').text(response.name);
                    $('#customerAddress').text(response.address);
                    $('#customerContactNumber').text(response.contactNumber);
                    $('#customerNic').text(response.nic);

                    // Show the modal
                    var myModal = new bootstrap.Modal(document.getElementById('customerModal'));
                    myModal.show();
                },
                error: function () {
                    Swal.fire({
                        icon: "error",
                        title: "Error!",
                        text: "Failed to fetch customer details.",
                        showConfirmButton: true
                    });
                }
            });
        }

        function fetchVehicleDetails(bookingNumber) {
            fetch(`${pageContext.request.contextPath}/bookings?action=getVehicleDetailBooking&bookingNumber=` + bookingNumber)
                .then(response => response.json())
                .then(data => {


                    let tableBody = document.getElementById("vehicleDetailsTableBody");
                    tableBody.innerHTML = "";

                    if (!data.vehicles || data.vehicles.length === 0) {

                        Swal.fire({
                            icon: "info",
                            title: "No Vehicles Found",
                            text: "No vehicles are assigned to this booking.",
                            showConfirmButton: true
                        });
                        return;
                    }
                    data.vehicles.forEach(vehicle => {
                        let row = tableBody.insertRow();
                        row.insertCell(0).textContent = vehicle.id
                        row.insertCell(1).textContent = vehicle.vehicleType
                        row.insertCell(2).textContent = vehicle.model
                        row.insertCell(3).textContent = vehicle.plateNumber
                        row.insertCell(4).textContent = vehicle.numberOfPassenger
                        row.insertCell(5).textContent = vehicle.pricePerKm
                        row.insertCell(6).textContent = vehicle.status
                        row.insertCell(7).textContent = vehicle.assignedDate
                    });

                    let myModal = new bootstrap.Modal(document.getElementById('vehicleDetailsModal'));
                    myModal.show();
                })
                .catch(error => {
                    console.error('Error:', error);
                    Swal.fire({
                        icon: "error",
                        title: "Error!",
                        text: "An error occurred while fetching vehicle details.",
                        showConfirmButton: true
                    });
                });
        }

    </script>
</head>
<body style="background-color: #ededede0">
<main id="AdminReserveBookings" class="container-fluid.p0">
    <section>
        <div>Mega City Cab Service</div>
        <div>
            <h2>Admin</h2>
            <img src="<%= request.getContextPath() %>/assets/img/adminfaceUser.png">
            <h1><%= user.getUsername() %>
            </h1>
        </div>
        <div>
            <div><h1><a href="${pageContext.request.contextPath}/pages/adminDashboard.jsp"><i
                    class="fas fa-th-large"></i> DashBoard</a></h1></div>
            <div><h1><a href="${pageContext.request.contextPath}/customers"><i class="fa-solid fa-user"></i>
                Customer</a></h1></div>
            <div><h1><a href="${pageContext.request.contextPath}/vehicles"><i
                    class="fa-solid fa-car"></i> Vehicles</a></h1></div>
            <div><h1><a href="${pageContext.request.contextPath}/drivers"><i class="fas fa-male"></i> Drivers</a></h1>
            </div>
            <div><h1><a href="${pageContext.request.contextPath}/bookings?action=viewBookings"><i
                    class="fa-solid fa-calendar-days"></i> Reserve Bookings</a></h1></div>
            <div><h1><a href="${pageContext.request.contextPath}/bookings?action=availablePayBookings"><i class="fas fa-money-bill"></i> Payment</a></h1></div>
            <div><h1><a href="${pageContext.request.contextPath}/bills"><i class="fas fa-chart-area"></i> Bills</a></h1></div>
            <div><h1><a href="${pageContext.request.contextPath}/users"><i class="fas fa-male"></i> User</a></h1></div>
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
                        <td>
                            <button type="button" class="btn btn-primary btn-sm"
                                    onclick="fetchCustomerDetails('${booking.customerId}')">
                                Show Details
                            </button>
                        </td>
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
                        <td>
                            <button type="button" class="btn btn-primary btn-sm"
                                    onclick="fetchVehicleDetails('${booking.bookingNumber}')">
                                Show Details
                            </button>
                        </td>
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
    </section>
</main>

<main>
    <div class="modal fade" id="customerModal" tabindex="-1" aria-labelledby="customerModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="customerModalLabel">Customer Details</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <p><strong>Customer ID:</strong> <span id="customerId"></span></p>
                    <p><strong>Name:</strong> <span id="customerName"></span></p>
                    <p><strong>Address:</strong> <span id="customerAddress"></span></p>
                    <p><strong>Contact Number:</strong> <span id="customerContactNumber"></span></p>
                    <p><strong>NIC:</strong> <span id="customerNic"></span></p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="vehicleDetailsModal" tabindex="-1" aria-labelledby="vehicleDetailsModalLabel"
         aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="vehicleDetailsModalLabel">Assigned Vehicles</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>Type</th>
                            <th>Model</th>
                            <th>Plate No</th>
                            <th>Passengers</th>
                            <th>Price/km</th>
                            <th>Status</th>
                            <th>Assigned Date</th>
                        </tr>
                        </thead>
                        <tbody id="vehicleDetailsTableBody">

                        </tbody>
                    </table>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>
</main>

</body>
</html>
