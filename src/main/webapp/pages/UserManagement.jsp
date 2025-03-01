<%--
  Created by IntelliJ IDEA.
  User: wishal siriwardana
  Date: 2/27/2025
  Time: 1:41 AM
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
    <title>Customer Management</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/style/AdminUser.css">
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

                let id = $(this).data("id");

                let username = $(this).data("username");
                let password = $(this).data("password");

                $("#editUserId").val(id);
                $("#editUsername").val(username);
                $("#editPassword").val(password);

                $("#editUserModal").modal("show");
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

        function confirmDelete(id) {
            Swal.fire({
                title: 'Are you sure?',
                text: "Do you want to delete this User?",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonText: 'Yes, delete it!',
                cancelButtonText: 'No, cancel!',
                reverseButtons: true
            }).then((result) => {
                if (result.isConfirmed) {

                    let form = document.createElement("form");
                    form.method = "post";
                    form.action = "<%= request.getContextPath() %>/users";


                    let actionInput = document.createElement("input");
                    actionInput.type = "hidden";
                    actionInput.name = "action";
                    actionInput.value = "delete";

                    let idInput = document.createElement("input");
                    idInput.type = "hidden";
                    idInput.name = "id";
                    idInput.value = id;


                    form.appendChild(actionInput);
                    form.appendChild(idInput);

                    document.body.appendChild(form);
                    form.submit();
                }
            });
        }
    </script>
</head>
<body style="background-color: #ededede0">
<main id="adminUser" class="container-fluid.p0">
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
            <div><h1><a href="${pageContext.request.contextPath}/vehicles"><i class="fa-solid fa-car"></i> Vehicles</a></h1></div>
            <div><h1><a href="${pageContext.request.contextPath}/drivers"><i class="fas fa-male"></i> Drivers</a></h1>
            </div>
            <div><h1><a href="${pageContext.request.contextPath}/bookings?action=viewBookings"><i class="fa-solid fa-calendar-days"></i> Reserve Bookings</a></h1></div>
            <div><h1><a href="${pageContext.request.contextPath}/bookings?action=availablePayBookings"><i class="fas fa-money-bill"></i> Payment</a></h1></div>
            <div><h1><a href="login.jsp"><i class="fas fa-chart-area"></i> Bills</a></h1></div>
            <div><h1><a href="${pageContext.request.contextPath}/users"><i class="fas fa-male"></i>User</a></h1></div>
            <div><h1 style=" color: darkred;"><a href="${pageContext.request.contextPath}/logout"><i
                    class="fas fa-sign-out-alt"></i> log Out</a></h1>
            </div>
        </div>

    </section>
    <section>
        <div>
            <h1>User Management</h1>
        </div>
        <div>
            <form action="${pageContext.request.contextPath}/users?action=save" method="post">
                <div>
                    <div>
                        <h3>Username</h3>
                        <input type="text" name="username" class="form-control" placeholder="Enter Username">
                    </div>
                    <div>
                        <h3>Password</h3>
                        <input type="password" name="password" class="form-control" placeholder="Enter Password">
                    </div>
                    <div id="buttonField">
                        <button type="submit" class="btn btn-success">Save</button>
                    </div>
                </div>
            </form>

        </div>
        <div style="padding:1rem;">
            <table class="table table-hover" >
                <thead class="table-primary ">
                <tr>
                    <th scope="col">User Id</th>
                    <th scope="col">Username</th>
                    <th scope="col">Password</th>
                    <th scope="col">Role</th>
                    <th scope="col">Created Date</th>
                    <th scope="col">Last Updated Date</th>
                    <th scope="col">Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="user" items="${userList}">
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.username}</td>
                        <td>${user.password}</td>
                        <td>${user.role}</td>
                        <td>${user.getFormattedCreatedDate()}</td>
                        <td>${user.getFormattedUpdatedDate()}</td>
                        <td>
                            <button class="btn btn-warning btn-sm edit-btn"
                                    data-id="${user.id}"
                                    data-username="${user.username}"
                                    data-password="${user.password}">
                                Edit
                            </button>
                            <button class="btn btn-danger btn-sm" onclick="confirmDelete('${user.id}')">Delete</button>
                        </td>

                    </tr>
                </c:forEach>
                <c:if test="${empty userList}">
                    <tr>
                        <td colspan="10">No Users Available</td>
                    </tr>
                </c:if>
                </tbody>
            </table>
        </div>

        <div class="modal fade" id="editUserModal" tabindex="-1" aria-labelledby="editUserModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="editUserModalLabel">Edit User</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <form id="editUserForm" method="post" action="${pageContext.request.contextPath}/users?action=update">
                            <input type="hidden" id="editUserId" name="id">

                            <div class="mb-3">
                                <label for="editUsername" class="form-label">Username</label>
                                <input type="text" class="form-control" id="editUsername" name="username">
                            </div>

                            <div class="mb-3">
                                <label for="editPassword" class="form-label">Password</label>
                                <input type="text" class="form-control" id="editPassword" name="password">
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


