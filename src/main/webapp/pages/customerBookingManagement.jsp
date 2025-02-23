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
            <li><a style="color: brown" href="${pageContext.request.contextPath}/logout">Log Out</a></li>
        </ul>
    </nav>
</header>
<main id="vehicleStore" class="container-fluid">
    <div>
        <h2><span>Booking Vehicles</span> & Expert Services</h2>
        <p>Recommendation for You</p>

    </div>
    <div id="cardsStore">
        <c:forEach var="vehicle" items="${availableVehicleList}">
            <div class="card" style="width: 14rem;">
                <img src="data:image/jpeg;base64,${vehicle.getBase64Img()}" class="card-img-top" alt="Vehicle Image">
                <hr>
                <div class="card-body border-bottom-0">
                    <h5 class="card-title"> Model - ${vehicle.getModel()}</h5>
                    <p class="card-text"> Vehicle Type - ${ vehicle.getVehicleType() }</p>
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
                    <button class="btn btn-primary"
                            data-id="${vehicle.getId()}"
                            data-model="${vehicle.getModel()}"
                            data-type="${vehicle.getVehicleType()}"
                            data-passengers="${vehicle.getNumberOfPassengers()}"
                            data-price="${vehicle.getPricePerKm()}"
                            data-driver="${vehicle.getDriverId()}"
                            data-plate="${vehicle.getPlateNumber()}"
                            data-status="${vehicle.getStatus()}"
                            onclick="addToBooking(this)">
                        Add To Booking
                    </button>

                </div>

            </div>
        </c:forEach>
    </div>
</main>
<main id="booking" class="container-fluid">
    <div>
        <h2>Booking Cart</h2>
    </div>
    <div>
        <form>
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
                    <th scope="col">Actions</th>
                </tr>
                </thead>
                <tbody id="tableCheckOutCart">

                </tbody>
            </table>
            <div>
                <h1>Pick Up Location</h1>
                <input type="text" class="form-control" placeholder="Enter Pick Up Location">
            </div>
            <div>
                <h1>Drop Location</h1>
                <input type="text" class="form-control" placeholder="Enter Drop Location">
            </div>
            <div>
                <h1>Distance Km</h1>
                <input type="text" class="form-control" id="distanceInput" placeholder="Enter Distance (Km)" onchange="checkBtnTextFieldValidation()">
            </div>
            <div>
                <h1>Base Fee (Without tax/discount)</h1>
                <h2 id="baseFeeDisplay"></h2>
            </div>
            <div>
                <button type="submit" id="placeBooking" class="btn btn-success" disabled>Place Booking</button>
            </div>
        </form>
    </div>
</main>
<script>
    let bookingCart = [];
    checkBtnTextFieldValidation();

    function checkBtnTextFieldValidation(){
        if (bookingCart.length === 0) {
            document.getElementById('distanceInput').disabled = true;
            document.getElementById('placeBooking').disabled = true;
        } else {
            document.getElementById('distanceInput').disabled = false;
            document.getElementById('placeBooking').disabled = false;


        }
    }


    document.getElementById('distanceInput').addEventListener('input', function() {
        const distance = parseFloat(this.value);
        if (!isNaN(distance) && distance > 0) {
            updateBaseFee(distance);
        } else {
            document.getElementById('baseFeeDisplay').innerText = 'Invalid Distance';
        }
    });

    function updateBaseFee(distance) {
        let totalBaseFee = 0;

        bookingCart.forEach(vehicle => {
            totalBaseFee += distance * vehicle.pricePerKm;
        });
        console.log(totalBaseFee)

        document.getElementById('baseFeeDisplay').innerText =  "LKR " + totalBaseFee.toFixed(2);
    }


    function addToBooking(button) {
        const vehicleId = button.getAttribute('data-id');
        const model = button.getAttribute('data-model');
        const type = button.getAttribute('data-type');
        const passengers = button.getAttribute('data-passengers');
        const pricePerKm = button.getAttribute('data-price');
        const driver = button.getAttribute('data-driver');
        const plateNumber = button.getAttribute('data-plate');
        const status = button.getAttribute('data-status');


        if (bookingCart.some(v => v.vehicleId === vehicleId)) {
            Swal.fire("Already Added", "This vehicle is already in your booking list!", "warning");
            return;
        }


        bookingCart.push({ vehicleId, model, type, passengers, pricePerKm, driver, plateNumber, status});
        console.log(bookingCart);

        updateBookingTable();
        checkBtnTextFieldValidation();
    }


    function updateBookingTable() {
        let tableBody = document.getElementById("tableCheckOutCart");
        tableBody.innerHTML = "";


        bookingCart.forEach((vehicle, index) => {
            let row = tableBody.insertRow();

            row.insertCell(0).textContent = vehicle.vehicleId;
            row.insertCell(1).textContent = vehicle.type;
            row.insertCell(2).textContent = vehicle.model;
            row.insertCell(3).textContent = vehicle.plateNumber;
            row.insertCell(4).textContent = vehicle.passengers;
            row.insertCell(5).textContent = vehicle.pricePerKm;
            row.insertCell(6).textContent = vehicle.driver;
            row.insertCell(7).textContent = vehicle.status;

            let removeCell = row.insertCell(8);
            let removeButton = document.createElement("button");
            removeButton.classList.add("btn","btn-sm");
            let deleteImg = document.createElement("img");
            deleteImg.src = `<%= request.getContextPath() %>/assets/img/circleDelete.png`;
            deleteImg.alt = "Delete";
            deleteImg.style.width = "16px";
            deleteImg.style.height = "16px";
            removeButton.appendChild(deleteImg);
            removeButton.onclick = function() { removeFromCart(index); };
            removeCell.appendChild(removeButton);
        });
    }

    function removeFromCart(index) {
        bookingCart.splice(index, 1);
        updateBookingTable();
        checkBtnTextFieldValidation();
    }
</script>
</body>
</html>
