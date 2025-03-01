<%--
  Created by IntelliJ IDEA.
  User: wishal siriwardana
  Date: 2/21/2025
  Time: 4:00 AM
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
    <title>Vehicle Management</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/style/AdminVehicle.css">
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
            $(".edit-btn").click(function () {
                let vehicleId = $(this).data("id");
                let vehicleType = $(this).data("type");
                let model = $(this).data("model");
                let plateNumber = $(this).data("plate");
                let passengerCount = $(this).data("numberOfPassengers");
                let pricePerKm = $(this).data("price");
                let status = $(this).data("status");
                let driverId = $(this).data("driver");

                console.log("Passenger Count: ", passengerCount);

                $("#editVehicleId").val(vehicleId);
                $("#editVehicleType").val(vehicleType);
                $("#editModel").val(model);
                $("#editPlateNumber").val(plateNumber);
                $("#editPassengerCount").val(passengerCount);
                $("#editPricePerKm").val(pricePerKm);
                $("#editStatus").val(status);
                $("#editDriverId").val(driverId);

                $("#editVehicleModal").modal("show");
            });

            <% if (alertType != null && message != null) { %>
            Swal.fire({
                icon: '<%= alertType.equals("success") ? "success" : "error" %>',
                title: '<%= alertType.equals("success") ? "Success!" : "Error!" %>',
                text: '<%= message %>',
                showConfirmButton: true
            });
            <% } %>
        });

        function confirmDelete(vehicleId) {
            Swal.fire({
                title: 'Are you sure?',
                text: "Do you want to delete this Vehicle?",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonText: 'Yes, delete it!',
                cancelButtonText: 'No, cancel!',
                reverseButtons: true
            }).then((result) => {
                if (result.isConfirmed) {

                    let form = document.createElement("form");
                    form.method = "post";
                    form.action = "<%= request.getContextPath() %>/vehicles";


                    let actionInput = document.createElement("input");
                    actionInput.type = "hidden";
                    actionInput.name = "action";
                    actionInput.value = "delete";

                    let idInput = document.createElement("input");
                    idInput.type = "hidden";
                    idInput.name = "id";
                    idInput.value = vehicleId;


                    form.appendChild(actionInput);
                    form.appendChild(idInput);

                    document.body.appendChild(form);
                    form.submit();
                }
            });
        }

        function previewImage(imagePath) {
           let imgsrc = "data:image/jpeg;base64,"+imagePath
            document.getElementById("previewImage").src = imgsrc;

            $('#imagePreviewModal').modal('show');
        }
    </script>
</head>
<body style="background-color: #ededede0">
<main id="adminVehicle" class="container-fluid.p0">
    <section>
        <div>Mega City Cab Service</div>
        <div>
            <h2>Admin</h2>
            <img src="<%= request.getContextPath() %>/assets/img/adminfaceUser.png">
            <h1><%= user.getUsername() %></h1>
        </div>
        <div>
            <div><h1><a href="${pageContext.request.contextPath}/pages/adminDashboard.jsp"><i
                    class="fas fa-th-large"></i> DashBoard</a></h1></div>
            <div><h1><a href="${pageContext.request.contextPath}/customers"><i class="fa-solid fa-user"></i> Customer</a></h1></div>
            <div><h1><a href="${pageContext.request.contextPath}/vehicles"><i
                    class="fa-solid fa-car"></i> Vehicles</a></h1></div>
            <div><h1><a href="${pageContext.request.contextPath}/drivers"><i class="fas fa-male"></i> Drivers</a></h1>
            </div>
            <div><h1><a href="${pageContext.request.contextPath}/bookings?action=viewBookings"><i class="fa-solid fa-calendar-days"></i> Reserve Bookings</a></h1></div>
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
            <h1>Vehicle Management</h1>
        </div>
        <div>
            <form action="${pageContext.request.contextPath}/vehicles" method="post" enctype="multipart/form-data">
                <div>
                    <div>
                        <h3>Vehicle Type</h3>
                        <select class="form-control" name="vehicleType" aria-label="Default select example">
                            <option value="" selected disabled>-- Select a Vehicle --</option>
                            <option selected value="CAR">Car</option>
                            <option value="VAN">Van</option>
                            <option value="MOTORCYCLE">Motorcycle</option>
                            <option value="TUK">Tuk Tuk</option>
                        </select>
                    </div>
                    <div>
                        <h3>Model</h3>
                        <input type="text" name="model" class="form-control" placeholder="Enter Model">
                    </div>
                    <div>
                        <h3>Plate Number</h3>
                        <input type="text" name="plateNumber" class="form-control" placeholder="Enter Plate Number">
                    </div>
                </div>
                <div>
                    <div>
                        <h3>Number Of Passenger</h3>
                        <input type="number" name="numberOfPassengers" class="form-control"
                               placeholder="Enter Number Of Passenger">
                    </div>
                    <div>
                        <h3>Price Per Km</h3>
                        <input type="text" name="pricePerKm" class="form-control"
                               placeholder="Enter Price Per Km">
                    </div>

                    <div>
                        <h3>Status</h3>
                        <select class="form-control" name="status" aria-label="Default select example">
                            <option selected value="AVAILABLE">Available</option>
                            <option value="BUSY">BUSY</option>
                        </select>
                    </div>

                </div>
                <div>
                    <div>
                        <h3>Assign Driver</h3>
                        <select class="form-control" name="driverId">
                            <c:forEach var="driver" items="${driverList}">
                                <option value="${driver.id}">${driver.id } - ${ driver.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div>
                        <h3>Image Upload</h3>
                        <input type="file" name="img" accept="image/*" class="form-control"
                               placeholder="Upload Image">
                    </div>
                    <div id="buttonField">
                        <%--                        <button type="submit" class="btn btn-primary">Search</button>--%>
                        <button type="submit" class="btn btn-success">Save</button>
                    </div>
                </div>
            </form>

        </div>
        <div style="padding:1rem;">
            <table class="table table-hover">
                <thead class="table-primary ">
                <tr>
                    <th scope="col">Vehicle ID</th>
                    <th scope="col">Type</th>
                    <th scope="col">Model</th>
                    <th scope="col">Plate No</th>
                    <th scope="col">Passengers</th>
                    <th scope="col">Price Per Km</th>
                    <th scope="col">Driver</th>
                    <th scope="col">Status</th>
                    <th scope="col">Created Date</th>
                    <th scope="col">Last Updated Date</th>
                    <th scope="col">Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="vehicle" items="${vehicleList}">
                    <tr>
                        <td>${vehicle.id}</td>
                        <td>${vehicle.vehicleType}</td>
                        <td>${vehicle.model}</td>
                        <td>${vehicle.plateNumber}</td>
                        <td>${vehicle.numberOfPassengers}</td>
                        <td>${vehicle.pricePerKm}</td>
                        <td>${vehicle.driverId}</td>
                        <td>${vehicle.status}</td>
                        <td>${vehicle.formattedCreatedDate}</td>
                        <td>${vehicle.formattedUpdatedDate}</td>
                        <td>
                            <button class="btn btn-primary btn-sm" onclick="previewImage('${vehicle.base64Img}')">Preview Image</button>

                            <button class="btn btn-warning btn-sm edit-btn"
                                    data-id="${vehicle.id}"
                                    data-type="${vehicle.vehicleType}"
                                    data-model="${vehicle.model}"
                                    data-plate="${vehicle.plateNumber}"
                                    data-number-of-passengers="${vehicle.numberOfPassengers}"
                                    data-price="${vehicle.pricePerKm}"
                                    data-driver="${vehicle.driverId}"
                                    data-status="${vehicle.status}">
                                Edit
                            </button>
                            <button class="btn btn-danger btn-sm" onclick="confirmDelete(${vehicle.id})">Delete
                            </button>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty vehicleList}">
                    <tr>
                        <td colspan="11">No Vehicles Available</td>
                    </tr>
                </c:if>
                </tbody>

                </tbody>
            </table>
        </div>

        <div class="modal fade" id="imagePreviewModal" tabindex="-1" aria-labelledby="imagePreviewModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="imagePreviewModalLabel">Vehicle Image Preview</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <img id="previewImage" class="img-fluid" alt="Vehicle Image" />
                    </div>
                </div>
            </div>
        </div>

        <div class="modal fade" id="editVehicleModal" tabindex="-1" aria-labelledby="editVehicleModalLabel"
             aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="editVehicleModalLabel">Edit Vehicle</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <form id="editVehicleForm" method="post" action="${pageContext.request.contextPath}/vehicles" enctype="multipart/form-data">
                            <input type="hidden" id="editVehicleId" name="id">

                            <div class="mb-3">
                                <label for="editVehicleType" class="form-label">Vehicle Type</label>
                                <select class="form-control" id="editVehicleType" name="vehicleType">
                                    <option value="CAR">Car</option>
                                    <option value="VAN">Van</option>
                                    <option value="MOTORCYCLE">Motorcycle</option>
                                    <option value="TUK">Tuk</option>
                                </select>
                            </div>

                            <div class="mb-3">
                                <label for="editModel" class="form-label">Model</label>
                                <input type="text" class="form-control" id="editModel" name="model">
                            </div>

                            <div class="mb-3">
                                <label for="editPlateNumber" class="form-label">Plate Number</label>
                                <input type="text" class="form-control" id="editPlateNumber" name="plateNumber">
                            </div>

                            <div class="mb-3">
                                <label for="editPassengerCount" class="form-label">Number of Passengers</label>
                                <input type="number" class="form-control" id="editPassengerCount"
                                       name="numberOfPassengers">
                            </div>

                            <div class="mb-3">
                                <label for="editPricePerKm" class="form-label">Price per Km</label>
                                <input type="text" class="form-control" id="editPricePerKm" name="pricePerKm">
                            </div>

                            <div class="mb-3">
                                <label for="editStatus" class="form-label">Status</label>
                                <select class="form-control" id="editStatus" name="status">
                                    <option value="AVAILABLE">Available</option>
                                    <option value="BUSY">Busy</option>
                                </select>
                            </div>

                            <div class="mb-3">
                                <label for="editDriverId" class="form-label">Driver</label>
                                <select class="form-control" id="editDriverId" name="driverId">
                                    <option value="0" ${vehicle.driverId == null ? 'selected' : ''}>-- No Driver Assigned --</option>
                                    <c:forEach var="driver" items="${driverList}">
                                        <option value="${driver.id}">${driver.id } - ${ driver.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label for="editImg" class="form-label">Upload Image</label>
                                <input id="editImg" type="file" name="img" accept="image/*" class="form-control" placeholder="Upload Image">
                            </div>
                            <button type="submit" class="btn btn-warning">Update</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>


    </section>
</main>
</body>
</html>

