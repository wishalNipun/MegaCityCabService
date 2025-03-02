package com.megacitycabservice.presentation.controllers;

import com.megacitycabservice.business.service.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/adminDashBoard")
public class AdminDashBoardServlet extends HttpServlet {
    private BookingService bookingService;
    private VehicleService vehicleService;
    private DriverService driverService;
    private CustomerService customerService;

    @Override
    public void init() {
        try {
            vehicleService = (VehicleService) BOFactory.getInstance().getBO(BOFactory.BOTypes.VEHICLE);
            bookingService = (BookingService) BOFactory.getInstance().getBO(BOFactory.BOTypes.BOOKING);
            driverService = (DriverService) BOFactory.getInstance().getBO(BOFactory.BOTypes.DRIVER);
            customerService = (CustomerService) BOFactory.getInstance().getBO(BOFactory.BOTypes.CUSTOMER);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response){

        try {

            int pendingBookingsCount = bookingService.getBookingsCountByStatus("PENDING");
            int confirmedBookingsCount = bookingService.getBookingsCountByStatus("CONFIRMED");
            int availableVehicleCount = vehicleService.getVehicleCountByStatus("AVAILABLE");
            int busyVehicleCount = vehicleService.getVehicleCountByStatus("BUSY");
            int driverCount = driverService.getAllDrivers().size();
            int customerCount = customerService.getAllCustomers().size();


            request.setAttribute("pendingBookingsCount", pendingBookingsCount);
            request.setAttribute("confirmedBookingsCount", confirmedBookingsCount);
            request.setAttribute("availableVehicleCount", availableVehicleCount);
            request.setAttribute("busyVehicleCount", busyVehicleCount);
            request.setAttribute("driverCount", driverCount);
            request.setAttribute("customerCount", customerCount);


            request.getRequestDispatcher("/pages/adminDashboard.jsp").forward(request, response);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
