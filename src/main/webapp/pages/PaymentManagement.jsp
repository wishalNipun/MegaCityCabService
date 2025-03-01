<%--
  Created by IntelliJ IDEA.
  User: wishal siriwardana
  Date: 2/28/2025
  Time: 1:32 AM
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
    <title>Payment Management</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/style/AdminPayment.css">
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

            $("#bookingSelect").change(function () {
                var selectedOption = $(this).find(":selected");
                fetchVehicleDetails(selectedOption.data("id"))

                $("#customerId").text(selectedOption.data("customer") || "-");
                $("#pickupLocation").text(selectedOption.data("pickup") || "-");
                $("#dropLocation").text(selectedOption.data("drop") || "-");
                $("#distance").text(selectedOption.data("distance") || "-");
                $("#fee").text(selectedOption.data("fee") || "-");
            });

            $("#tax").val("");
            $("#discount").val("");
            $("#totalAmount").text("-");
            $("#placeBooking").prop('disabled', true);

            $("#tax, #discount").on('input', function () {
                calculateTotal();
            });
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
                        row.insertCell(6).textContent = vehicle.assignedDate
                    });
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

        function calculateTotal() {
            var baseFee = parseFloat($("#fee").text()) || 0;
            var taxPercentage = parseFloat($("#tax").val()) || 0;
            var discount = parseFloat($("#discount").val()) || 0;

            var taxAmount = (baseFee * taxPercentage) / 100;

            var totalAmount = baseFee + taxAmount - discount;

            $("#baseFeeInput").val(baseFee.toFixed(2));
            $("#totalAmount").text(totalAmount.toFixed(2));
            $("#totalAmountInput").val(totalAmount.toFixed(2));

            if (totalAmount > 0) {
                $("#placeBooking").prop('disabled', false);
            } else {
                $("#placeBooking").prop('disabled', true);
            }
        }

    </script>
</head>
<body style="background-color: #ededede0">
<main id="AdminPayment" class="container-fluid.p0">
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
        <div>
            <form action="${pageContext.request.contextPath}/bills?action=payBook" method="post">
                <div>
                    <div>
                        <h3>Booking Id</h3>
                        <select class="form-control" id="bookingSelect" name="bookingId" required>
                            <option value="" disabled selected>Select a Booking</option>
                            <c:forEach var="booking" items="${bookingList}">
                                <option value="${booking.bookingNumber}"
                                        data-id="${booking.bookingNumber}"
                                        data-customer="${booking.customerId}"
                                        data-pickup="${booking.pickupLocation}"
                                        data-drop="${booking.dropLocation}"
                                        data-distance="${booking.distanceKm}"
                                        data-fee="${booking.fee}">
                                    ${booking.bookingNumber}
                                </option>
                            </c:forEach>

                        </select>
                    </div>
                    <div>
                        <h3>Customer Id</h3>
                        <div id="customerId">-</div>
                    </div>
                    <div>
                        <h3>Pickup Location</h3>
                        <div id="pickupLocation">-</div>
                    </div>
                    <div>
                        <h3>Drop Location</h3>
                        <div id="dropLocation">-</div>
                    </div>
                    <div>
                        <h3>Distance (Km)</h3>
                        <div id="distance">-</div>
                    </div>
                    <div>
                        <h3>Base Fee</h3>
                        <div id="fee">-</div>
                        <input type="hidden" id="baseFeeInput" name="baseFee">
                    </div>
                </div>
                <div>
                    <table class="table table-hover">
                        <thead class="table-primary ">
                        <tr>
                            <th scope="col">Vehicle ID</th>
                            <th scope="col">Type</th>
                            <th scope="col">Model</th>
                            <th scope="col">Plate No</th>
                            <th scope="col">Passengers</th>
                            <th scope="col">Price Per Km</th>
                            <th scope="col">Assigned Date</th>
                        </tr>
                        </thead>
                        <tbody id="vehicleDetailsTableBody">
                        </tbody>
                    </table>
                </div>
                <div>

                    <div>
                        <div class="payDiv">
                            <h1>Tax (%)</h1>
                            <input type="number" id="tax" name="tax" class="form-control" placeholder="Enter Tax (%)" step="0.01">
                        </div>
                        <div class="payDiv">
                            <h1>Discount</h1>
                            <input type="number" id="discount" name="discount" class="form-control" placeholder="Enter Discount" step="0.01">
                        </div>
                        <div class="payDiv">
                            <h1>Total Amount</h1>
                            <div id="totalAmount">-</div>
                            <input type="hidden" id="totalAmountInput" name="totalAmount">
                        </div>
                        <div id="payBtn">
                            <button type="submit" id="placeBooking" class="btn btn-success" disabled>Pay Booking</button>
                        </div>
                    </div>

                </div>

            </form>

        </div>

    </section>

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

