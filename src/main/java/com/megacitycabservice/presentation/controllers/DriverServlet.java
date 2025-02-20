package com.megacitycabservice.presentation.controllers;

import com.megacitycabservice.model.Driver;
import com.megacitycabservice.business.service.DriverService;
import com.megacitycabservice.business.service.impl.DriverServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/drivers")
public class DriverServlet extends HttpServlet {

    private DriverService driverService;

    @Override
    public void init(){
        try {
            super.init();
            driverService = new DriverServiceImpl();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException | ServletException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String name = request.getParameter("name");
        String nic = request.getParameter("nic");
        String address = request.getParameter("address");
        String licenseNumber = request.getParameter("licenseNumber");
        String dateOfBirth = request.getParameter("dateOfBirth");
        String status = request.getParameter("status");

        java.sql.Date sqlDateOfBirth = java.sql.Date.valueOf(dateOfBirth);

        Driver driver = new Driver();
        driver.setName(name);
        driver.setNic(nic);
        driver.setAddress(address);
        driver.setLicenseNumber(licenseNumber);
        driver.setDateOfBirth(sqlDateOfBirth);
        driver.setStatus(status);

        try {
            driverService.addDriver(driver);
            response.sendRedirect("/drivers");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.getWriter().println("DriverServlet is running!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        response.setContentType("text/html;charset=UTF-8");

        try {
            driverService = new DriverServiceImpl();
            List<Driver> driverList = driverService.getAllDrivers();

            request.setAttribute("driverList", driverList);
            System.out.println("Driver List: " + driverList);

            request.getRequestDispatcher("pages/DriverManagement.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
