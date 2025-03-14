<%--
  Created by IntelliJ IDEA.
  User: wishal siriwardana
  Date: 2/11/2025
  Time: 2:03 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String alertType = (String) session.getAttribute("alert");
    String message = (String) session.getAttribute("message");

    if (alertType != null && message != null) {
        session.removeAttribute("alert");
        session.removeAttribute("message");
    }
%>
<html>
<head>
    <title>Login Page</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/style/userLogin.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;700&display=swap" rel="stylesheet">
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
    </script>
</head>
<body>
<main id="UserLoginAccount" class="container-fluid">
    <div></div>
    <div></div>
    <div>
        <h1>Login Your Account.</h1>
        <form action="${pageContext.request.contextPath}/login" method="post">
            <input type="text" class="form-control" name="username" placeholder="User Name">
            <input type="password" class="form-control" name="password" placeholder="Password">
            <button id="btnSignnUp" type="submit" class="btn btn-primary">Sign In</button>

            <div id="orr">
                <div id="linee1"></div>
                <h1>OR</h1>
                <div id="linee2"></div>
            </div>
            <a href="register.jsp" style="color: white; text-decoration: none;">
                <button id="btnSignnIn" type="button" class="btn btn-primary">
                    Sign Up
                </button>
            </a>

        </form>
    </div>

</main>

</body>
</html>
