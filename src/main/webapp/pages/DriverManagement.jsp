<%--
  Created by IntelliJ IDEA.
  User: wishal siriwardana
  Date: 2/14/2025
  Time: 6:24 PM
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
    <title>Driver Management</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/style/AdminDrivers.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;700&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Rock+Salt&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body style="background-color: #ededede0">
<main id="adminDriver" class="container-fluid.p0">
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
            <div><h1><a href="login.jsp"><i class="fa-solid fa-car"></i> Cars</a></h1></div>
            <div><h1><a href="${pageContext.request.contextPath}/drivers"><i class="fas fa-male"></i> Drivers</a></h1></div>
            <div><h1><a href="login.jsp"><i class="fa-solid fa-calendar-days"></i> Bookings</a></h1></div>
            <div><h1><a href="login.jsp"><i class="fas fa-money-bill"></i> Payment</a></h1></div>
            <div><h1><a href="login.jsp"><i class="fas fa-chart-area"></i> Bills</a></h1></div>
            <div><h1><a href="login.jsp"><i class="fas fa-male"></i>User</a></h1></div>
            <div><h1 style=" color: darkred;"><a href="${pageContext.request.contextPath}/pages/login.jsp"><i class="fas fa-sign-out-alt"></i> log Out</a></h1>
            </div>
        </div>

    </section>
    <section>
        <div>
            <h1>Driver Manager</h1>
        </div>
        <div>
            <form action="${pageContext.request.contextPath}/drivers" method="post">
                <div>
                    <div>
                        <h3>Name</h3>
                        <input type="text" name="name" class="form-control" placeholder="Enter Name">
                    </div>
                    <div>
                        <h3>NIC</h3>
                        <input type="text" name="nic" class="form-control" placeholder="Enter Nic">
                    </div>
                    <div>
                        <h3>Address</h3>
                        <input type="text" name="address" class="form-control" placeholder="Enter Address">
                    </div>
                </div>
                <div>
                    <div>
                        <h3>Date Of Birth</h3>
                        <input type="date" name="dateOfBirth" class="form-control" placeholder="Enter Date Of Birth">
                    </div>
                    <div>
                        <h3>Driving License</h3>
                        <input type="text" name="licenseNumber" class="form-control" placeholder="Enter Driving License">
                    </div>

                    <div>
                        <h3>Contact Number</h3>
                        <input type="text" name="contactNumber" class="form-control" placeholder="Enter Contact Number">
                    </div>


                </div>
                <div>
                    <div>
                        <h3>Status</h3>
                        <select class="form-control" name="status" aria-label="Default select example">
                            <option selected value="AVAILABLE">Available</option>
                            <option value="BUSY">BUSY</option>
                        </select>
                    </div>
                    <div id="driverButtonField">
                        <button type="submit" class="btn btn-primary">Search</button>
                        <button type="submit" class="btn btn-success">Save</button>
                    </div>
                </div>
            </form>

        </div>
        <div>
            <table class="table table-hover">
                <thead class="table-primary ">
                <tr>
                    <th scope="col">Driver Id</th>
                    <th scope="col">Name</th>
                    <th scope="col">NIC</th>
                    <th scope="col">Address</th>
                    <th scope="col">Driving License</th>
                    <th scope="col">Date Of Birth</th>
                    <th scope="col">Status</th>
                    <th scope="col">Created Date</th>
                    <th scope="col">Last Updated Date</th>
                    <th scope="col">Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="driver" items="${driverList}">
                    <tr>
                        <td>${driver.id}</td>
                        <td>${driver.name}</td>
                        <td>${driver.nic}</td>
                        <td>${driver.address}</td>
                        <td>${driver.licenseNumber}</td>
                        <td>${driver.dateOfBirth}</td>
                        <td>${driver.status}</td>
                        <td>${driver.createdDate}</td>
                        <td>${driver.updatedDate}</td>
                        <td>
                            <button class="btn btn-warning btn-sm">Update</button>
                            <button class="btn btn-danger btn-sm">Delete</button></td>

                    </tr>
                </c:forEach>
                <c:if test="${empty driverList}">
                    <tr><td colspan="10">No Drivers Available</td></tr>
                </c:if>
                </tbody>

                </tbody>
            </table>
        </div>
    </section>
</main>
</body>
</html>

