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
                        let driverCell = row.insertCell(7);
                        let button = document.createElement("button");
                        button.textContent = "Show Details";
                        button.className = "btn btn-primary btn-sm";
                        button.onclick = function () {
                            fetchDriverDetails(vehicle.driverId);
                        };
                        driverCell.appendChild(button)
                        row.insertCell(8).textContent = vehicle.assignedDate
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


        function fetchDriverDetails(driverId) {
            $.ajax({
                url: "${pageContext.request.contextPath}/drivers?action=getDriverDetailUsingID",
                type: "GET",
                data: {id: driverId},
                dataType: "json",
                success: function (response) {
                    // Populate modal fields with the fetched customer data
                    $('#id').text(response.id);
                    $('#driverName').text(response.name);
                    $('#driverAddress').text(response.address);
                    $('#driverLicenseNumber').text(response.licenseNumber);
                    $('#driverNic').text(response.nic);
                    $('#driverDateOfBirth').text(response.dateOfBirth);
                    $('#driverContactNumber').text(response.contactNumber);
                    // Show the modal
                    var myModal = new bootstrap.Modal(document.getElementById('driverModal'));
                    myModal.show();
                },
                error: function () {
                    Swal.fire({
                        icon: "error",
                        title: "Error!",
                        text: "Failed to fetch Driver details.",
                        showConfirmButton: true
                    });
                }
            });
        }

        function confirmCancelBooking(bookingNumber) {
            Swal.fire({
                title: "Are you sure?",
                text: "Do you really want to Cancel this booking?",
                icon: "warning",
                showCancelButton: true,
                confirmButtonColor: "#d33",
                cancelButtonColor: "#3085d6",
                confirmButtonText: "Yes, Cancel it!"
            }).then((result) => {
                if (result.isConfirmed) {

                    let form = document.createElement('form');
                    form.method = "POST";
                    form.action = "${pageContext.request.contextPath}/bookings";
                    let actionInput = document.createElement('input');
                    actionInput.type = "hidden";
                    actionInput.name = "action";
                    actionInput.value = "removeBooking";
                    form.appendChild(actionInput);

                    let bookingInput = document.createElement('input');
                    bookingInput.type = "hidden";
                    bookingInput.name = "bookingNumber";
                    bookingInput.value = bookingNumber;
                    form.appendChild(bookingInput);

                    let bookingStatusInput = document.createElement('input');
                    bookingStatusInput.type = "hidden";
                    bookingStatusInput.name = "status";
                    bookingStatusInput.value = "CANCELLED";
                    form.appendChild(bookingStatusInput);


                    document.body.appendChild(form);
                    form.submit();
                }
            });
        }

    </script>
</head>
<body>
<header>
    <nav id="Nav">
        <ul>
            <li><a href="${pageContext.request.contextPath}/pages/customerDashboard.jsp">Home</a></li>
            <li><a href="${pageContext.request.contextPath}/bookings?action=availableVehicles">Booking</a></li>
            <li>
                <a href="${pageContext.request.contextPath}/bookings?action=bookingDetail&username=<%= user.getUsername() %>">Booking
                    Detail</a></li>
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
                <th scope="col">Action</th>
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
                    <td>
                        <button type="button" class="btn btn-primary btn-sm"
                                onclick="fetchVehicleDetails('${booking.bookingNumber}')">
                            Show Details
                        </button>
                    </td>
                    <td>${booking.formattedCreatedDate}</td>
                    <td>${booking.formattedUpdatedDate}</td>
                    <td>
                        <c:if test="${booking.status == 'PENDING' || booking.status == 'CONFIRMED'}">
                            <button type="button" class="btn btn-danger btn-sm"
                                    onclick="confirmCancelBooking('${booking.bookingNumber}')">
                                Cancel
                            </button>
                        </c:if>
                    </td>
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
<main>
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
                            <th>Driver Detail</th>
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

    <div class="modal fade" id="driverModal" tabindex="-1" aria-labelledby="driverModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="driverModalLabel">Driver Details</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <p><strong>Driver ID:</strong> <span id="id"></span></p>
                    <p><strong>Name:</strong> <span id="driverName"></span></p>
                    <p><strong>Address:</strong> <span id="driverAddress"></span></p>
                    <p><strong>Address:</strong> <span id="driverLicenseNumber"></span></p>
                    <p><strong>Address:</strong> <span id="driverDateOfBirth"></span></p>
                    <p><strong>NIC:</strong> <span id="driverNic"></span></p>
                    <p><strong>Contact Number:</strong> <span id="driverContactNumber"></span></p>
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
