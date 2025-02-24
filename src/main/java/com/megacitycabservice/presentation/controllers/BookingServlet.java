package com.megacitycabservice.presentation.controllers;

import com.megacitycabservice.business.service.VehicleService;
import com.megacitycabservice.business.service.impl.VehicleServiceImpl;
import com.megacitycabservice.model.Vehicle;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@WebServlet("/bookings")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class BookingServlet extends HttpServlet {
    private VehicleService vehicleService;



    @Override
    public void init() {
        try {
            System.out.println("Boooking");
            vehicleService = new VehicleServiceImpl();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] vehicleIds = request.getParameterValues("vehicleIds");
        String pickUpLocation = request.getParameter("pickUpLocation");
        String dropLocation = request.getParameter("dropLocation");
        String distanceStr = request.getParameter("distance");
        String baseFeeStr = request.getParameter("baseFee");

        double distance = Double.parseDouble(distanceStr);
        double baseFee = Double.parseDouble(baseFeeStr);

        System.out.println("Booking Details:");
        System.out.println("Pick Up Location: " + pickUpLocation);
        System.out.println("Drop Location: " + dropLocation);
        System.out.println("Distance: " + distance);
        System.out.println("Base Fee: " + baseFee);
        System.out.println("Selected Vehicle IDs: ");

        if (vehicleIds == null || vehicleIds.length == 0) {

            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No vehicle IDs received.");
            return;
        } else {
            System.out.println("Vehicle IDs: " + Arrays.toString(vehicleIds));
        }


        response.sendRedirect(request.getContextPath() + "/pages/customerBookingManagement.jsp");
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {

            List<Vehicle> vehicleList = vehicleService.getAvailableVehicles();

            if (vehicleList != null && !vehicleList.isEmpty()) {
                request.setAttribute("availableVehicleList", vehicleList);
            } else {
                System.out.println("No vehicles found");
            }

            request.getRequestDispatcher("/pages/customerBookingManagement.jsp").forward(request, response);

        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }

}