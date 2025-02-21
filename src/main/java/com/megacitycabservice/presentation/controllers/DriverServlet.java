package com.megacitycabservice.presentation.controllers;

import com.megacitycabservice.business.service.DriverService;
import com.megacitycabservice.business.service.impl.DriverServiceImpl;
import com.megacitycabservice.model.Driver;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/drivers")
public class DriverServlet extends HttpServlet {

    private DriverService driverService;

    @Override
    public void init() {
        try {
            driverService = new DriverServiceImpl();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            deleteDriver(request, response);
        }else{
            String driverId = request.getParameter("id");

            if (driverId == null || driverId.isEmpty()) {

                addDriver(request, response);
            } else {

                updateDriver(request, response);
            }
        }


    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        try {
            driverService = new DriverServiceImpl();
            List<Driver> driverList = driverService.getAllDrivers();

            request.setAttribute("driverList", driverList);
            System.out.println("Driver List: " + driverList);

            request.getRequestDispatcher("/pages/DriverManagement.jsp").forward(request, response);

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


    public void addDriver(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String nic = request.getParameter("nic");
        String address = request.getParameter("address");
        String licenseNumber = request.getParameter("licenseNumber");
        String dateOfBirth = request.getParameter("dateOfBirth");
        String contactNumber = request.getParameter("contactNumber");
        String status = request.getParameter("status");

        java.sql.Date sqlDateOfBirth = java.sql.Date.valueOf(dateOfBirth);

        Driver driver = new Driver();
        driver.setName(name);
        driver.setNic(nic);
        driver.setAddress(address);
        driver.setLicenseNumber(licenseNumber);
        driver.setDateOfBirth(sqlDateOfBirth);
        driver.setContactNumber(contactNumber);
        driver.setStatus(status);

        try {
            driverService.addDriver(driver);
            HttpSession session = request.getSession();
            session.setAttribute("alert", "success");
            session.setAttribute("message", "Driver added successfully!");
            response.sendRedirect(request.getContextPath() + "/drivers");
        } catch (Exception e) {
            e.printStackTrace();
            HttpSession session = request.getSession();
            session.setAttribute("alert", "error");
            session.setAttribute("message", "Failed to add driver.");
        }


    }

    public void updateDriver(HttpServletRequest request, HttpServletResponse response) {

        String driverId = request.getParameter("id");
        String name = request.getParameter("name");
        String nic = request.getParameter("nic");
        String address = request.getParameter("address");
        String licenseNumber = request.getParameter("licenseNumber");
        String dateOfBirth = request.getParameter("dateOfBirth");
        String contactNumber = request.getParameter("contactNumber");
        String status = request.getParameter("status");

        java.sql.Date sqlDateOfBirth = java.sql.Date.valueOf(dateOfBirth);

        Driver driver = new Driver();
        driver.setId(Integer.parseInt(driverId));
        driver.setName(name);
        driver.setNic(nic);
        driver.setAddress(address);
        driver.setLicenseNumber(licenseNumber);
        driver.setDateOfBirth(sqlDateOfBirth);
        driver.setContactNumber(contactNumber);
        driver.setStatus(status);

        try {
            driverService.updateDriver(driver);
            HttpSession session = request.getSession();
            session.setAttribute("alert", "success");
            session.setAttribute("message", "Driver updated successfully!");
            response.sendRedirect(request.getContextPath() + "/drivers");
        } catch (IOException e) {
            HttpSession session = request.getSession();
            session.setAttribute("alert", "error");
            session.setAttribute("message", "Failed to update driver.");
            throw new RuntimeException(e);
        }
    }

    public void deleteDriver(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String driverId = request.getParameter("id");

        if (driverId != null && !driverId.isEmpty()) {

            boolean isDeleted = driverService.deleteDriver(Integer.parseInt(driverId));

            if (isDeleted) {
                HttpSession session = request.getSession();
                session.setAttribute("alert", "success");
                session.setAttribute("message", "Driver deleted successfully!");
                response.sendRedirect(request.getContextPath() + "/drivers");
            } else {
                HttpSession session = request.getSession();
                session.setAttribute("alert", "error");
                session.setAttribute("message", "Failed to delete driver.");
                response.sendRedirect(request.getContextPath() + "/drivers");
            }
        }
    }
}
