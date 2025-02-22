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


    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {

            System.out.println("doGet method called");
            List<Vehicle> vehicleList = vehicleService.getAvailableVehicles();
            System.out.println("Vehicle List Size: " + vehicleList.size()); // Log the size

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