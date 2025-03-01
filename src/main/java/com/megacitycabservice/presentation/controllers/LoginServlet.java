package com.megacitycabservice.presentation.controllers;

import com.megacitycabservice.business.service.BOFactory;
import com.megacitycabservice.model.User;
import com.megacitycabservice.business.service.UserService;
import com.megacitycabservice.business.service.impl.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() {
        try {
            userService = (UserService) BOFactory.getInstance().getBO(BOFactory.BOTypes.USER);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            User user = userService.validateUser(username, password);
            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                session.setAttribute("username", user.getUsername());
                if ("ADMIN".equals(user.getRole())) {
                    response.sendRedirect("pages/adminDashboard.jsp");
                } else {
                    response.sendRedirect("pages/customerDashboard.jsp");
                }
            } else {
                request.setAttribute("error", "Invalid credentials!");
                request.getRequestDispatcher("pages/login.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }
}
