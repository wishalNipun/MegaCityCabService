<%--
  Created by IntelliJ IDEA.
  User: wishal siriwardana
  Date: 2/11/2025
  Time: 2:03 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>User Create Account</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/style/userRegister.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;700&display=swap" rel="stylesheet">
</head>
<body>
<main id="UserAccount" class="container-fluid">
    <div></div>
    <div></div>
    <div>
        <h1>Create Your Account.</h1>
        <p>Since this your first trip. You’ll need to provide us
            with some information for Sign Up.</p>
        <form action="${pageContext.request.contextPath}/customers?action=register" method="post">
            <div>
                <input type="text" class="form-control" name="name"  placeholder="Enter Name">
                <input type="text" class="form-control" name="nic"  placeholder="Enter NIC Number">

            </div>
            <div>
                <input type="text" class="form-control" name="address"  placeholder="Enter Address">
                <input type="text" class="form-control" name="contactNumber" placeholder="Enter Contact Number">
            </div>
            <div>
                <input type="text" class="form-control" name="username" placeholder="Enter New User Name">
                <input type="text" class="form-control" name="password" placeholder="Enter New Password">
            </div>
            <div>
                <button id="btnSignUp" type="submit" class="btn btn-primary">Sign Up</button>
            </div>
        </form>
    </div>
    <div>
        <p>Already have an account sign in <a href="login.jsp">here</a></p>
    </div>
</main>
</body>
</html>
