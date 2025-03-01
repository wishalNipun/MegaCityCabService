package com.megacitycabservice.presentation.controllers;

import com.megacitycabservice.business.service.UserService;
import com.megacitycabservice.business.service.impl.UserServiceImpl;
import com.megacitycabservice.model.User;
import com.megacitycabservice.util.ResponseUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/users")
public class UserServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() {
        try {
            userService = new UserServiceImpl();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("save".equals(action)) {
            addUser(request, response);
        } else if ("delete".equals(action)) {
            deleteUser(request, response);
        } else if ("update".equals(action)) {
            updateUser(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> userList = userService.getAllUsers();
        request.setAttribute("userList", userList);
        request.getRequestDispatcher("/pages/UserManagement.jsp").forward(request, response);
    }

    public void addUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        String message = userService.addUser(user);

        if ("success".equals(message)) {
            ResponseUtil.setResponseMessage(request, "success", "User added successfully.");
        } else {
            ResponseUtil.setResponseMessage(request, "error", message);
        }
        response.sendRedirect(request.getContextPath() + "/users");
    }

    public void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userId = request.getParameter("id");

        if (userId != null && !userId.isEmpty()) {
            String message = userService.deleteUser(Integer.parseInt(userId));
            if ("success".equals(message)) {
                ResponseUtil.setResponseMessage(request, "success", "User deleted successfully.");
            } else {
                ResponseUtil.setResponseMessage(request, "error", message);
            }
        }
        response.sendRedirect(request.getContextPath() + "/users");
    }

    public void updateUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userId = request.getParameter("id");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (userId == null || userId.isEmpty()) {
            ResponseUtil.setResponseMessage(request, "error", "Error: User ID is missing.");
            response.sendRedirect(request.getContextPath() + "/users");
            return;
        }

        User user = new User();
        user.setId(Integer.parseInt(userId));
        user.setUsername(username);
        user.setPassword(password);

        String message = userService.updateUser(user);

        if ("success".equals(message)) {
            ResponseUtil.setResponseMessage(request, "success", "User updated successfully!");
        } else {
            ResponseUtil.setResponseMessage(request, "error", message);
        }
        response.sendRedirect(request.getContextPath() + "/users");
    }
}
