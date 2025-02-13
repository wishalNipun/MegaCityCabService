package com.megacitycabservice.controllers;

import com.megacitycabservice.model.Customer;
import com.megacitycabservice.service.CustomerService;
import com.megacitycabservice.service.impl.CustomerServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/customers")
public class CustomerServlet extends HttpServlet {

    private CustomerService customerService;

    @Override
    public void init() {
        System.out.println("hit");
        try {
            customerService = new CustomerServiceImpl();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String nic = request.getParameter("nic");
        String address = request.getParameter("address");
        String contactNumber = request.getParameter("contactNumber");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        boolean isRegistered = customerService.registerCustomerWithUser(name, nic, address, contactNumber, username, password);

        if (isRegistered) {
            request.setAttribute("message", "Customer registered successfully.");
            request.getRequestDispatcher("pages/customerDashboard.jsp").forward(request, response);
        } else {
            request.setAttribute("message", "Registration failed. Please try again.");
            request.getRequestDispatcher("pages/register.jsp").forward(request, response);
        }
    }
}
