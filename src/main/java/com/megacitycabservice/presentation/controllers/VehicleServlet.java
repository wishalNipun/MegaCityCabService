package com.megacitycabservice.presentation.controllers;


import com.megacitycabservice.business.service.DriverService;
import com.megacitycabservice.business.service.VehicleService;
import com.megacitycabservice.business.service.impl.DriverServiceImpl;
import com.megacitycabservice.business.service.impl.VehicleServiceImpl;
import com.megacitycabservice.model.Driver;
import com.megacitycabservice.model.Vehicle;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/vehicles")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class VehicleServlet extends HttpServlet {
    private VehicleService vehicleService;
    private DriverService driverService;

    @Override
    public void init() {
        try {
            System.out.println("vehicle runnn");
            vehicleService = new VehicleServiceImpl();
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
        addVehicle(request, response);
        System.out.println("added click");


//        if ("delete".equals(action)) {
//           // deleteVehicle(request, response);
//        } else {
//            String driverId = request.getParameter("id");
//
//            if (driverId == null || driverId.isEmpty()) {
//
//               addVehicle(request, response);
//            } else {
//
//               // updateVehicle(request, response);
//            }
//        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {


            List<Driver> driverList = driverService.getAllAvailableDrivers();
            List<Vehicle> vehicleList = vehicleService.getAllVehicles();

            request.setAttribute("driverList", driverList);
            request.setAttribute("vehicleList", vehicleList);

            request.getRequestDispatcher("/pages/VehicleManagement.jsp").forward(request, response);

        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void addVehicle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String vehicleType = request.getParameter("vehicleType");
        String model = request.getParameter("model");
        String plateNumber = request.getParameter("plateNumber");
        String driverIdStr = request.getParameter("driverId");
        String passengerCountStr = request.getParameter("numberOfPassengers");
        String pricePerKmStr = request.getParameter("pricePerKm");
        String status = request.getParameter("status");

        int driverId = driverIdStr != null && !driverIdStr.isEmpty() ? Integer.parseInt(driverIdStr) : 0;
        int numberOfPassengers = Integer.parseInt(passengerCountStr);
        double pricePerKm = Double.parseDouble(pricePerKmStr);

        Part imagePart = request.getPart("img");
        byte[] imageBytes = null;
        if (imagePart != null && imagePart.getSize() > 0) {
            try (InputStream inputStream = imagePart.getInputStream()) {
                imageBytes = inputStream.readAllBytes();
            }
        }

        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleType(vehicleType);
        vehicle.setModel(model);
        vehicle.setPlateNumber(plateNumber);
        vehicle.setDriverId(driverId);
        vehicle.setNumberOfPassengers(numberOfPassengers);
        vehicle.setPricePerKm(pricePerKm);
        vehicle.setStatus(status);
        vehicle.setImg(imageBytes);
        System.out.println(vehicleType);
        System.out.println(model);
        System.out.println(plateNumber);
        System.out.println(driverId);
        System.out.println(numberOfPassengers);
        System.out.println(pricePerKm);
        System.out.println(status);


        try {
            String message = vehicleService.addVehicle(vehicle);
            HttpSession session = request.getSession();

            if ("success".equals(message)) {
                session.setAttribute("alert", "success");
                session.setAttribute("message", "Vehicle added successfully!");
            } else {
                session.setAttribute("alert", "error");
                session.setAttribute("message", message);
            }
            response.sendRedirect(request.getContextPath() + "/vehicles");

        } catch (Exception e) {
            e.printStackTrace();  // This will help capture any unexpected errors
            response.getWriter().write("Error occurred: " + e.getMessage());
        }

    }
}
