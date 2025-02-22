package com.megacitycabservice.presentation.controllers;


import com.megacitycabservice.business.service.DriverService;
import com.megacitycabservice.business.service.VehicleService;
import com.megacitycabservice.business.service.impl.DriverServiceImpl;
import com.megacitycabservice.business.service.impl.VehicleServiceImpl;
import com.megacitycabservice.model.Driver;
import com.megacitycabservice.model.Vehicle;
import com.megacitycabservice.util.ResponseUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

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

        if ("delete".equals(action)) {
            deleteVehicle(request, response);
        } else {
            String vehicleId = request.getParameter("id");
            if (vehicleId == null || vehicleId.isEmpty()) {
                addVehicle(request, response);
            } else {
                updateVehicle(request, response);
            }

        }

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


        try {
            String message = vehicleService.addVehicle(vehicle);

            if ("success".equals(message)) {
                ResponseUtil.setResponseMessage(request, "success", "Vehicle added successfully!");
            } else {
                ResponseUtil.setResponseMessage(request, "error", message);
            }
            response.sendRedirect(request.getContextPath() + "/vehicles");

        } catch (Exception e) {
            e.printStackTrace();
            ResponseUtil.setResponseMessage(request, "error", "Failed to added vehicle.");
        }

    }

    private void updateVehicle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String vehicleId = request.getParameter("id");
        String vehicleType = request.getParameter("vehicleType");
        String model = request.getParameter("model");
        String plateNumber = request.getParameter("plateNumber");
        String driverIdStr = request.getParameter("driverId");
        String passengerCountStr = request.getParameter("numberOfPassengers");
        String pricePerKmStr = request.getParameter("pricePerKm");
        String status = request.getParameter("status");

        int id = Integer.parseInt(vehicleId);
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
        vehicle.setId(id);
        vehicle.setVehicleType(vehicleType);
        vehicle.setModel(model);
        vehicle.setPlateNumber(plateNumber);
        vehicle.setDriverId(driverId);
        vehicle.setNumberOfPassengers(numberOfPassengers);
        vehicle.setPricePerKm(pricePerKm);
        vehicle.setStatus(status);
        vehicle.setImg(imageBytes);

        System.out.println("ID: " + id);
        System.out.println("vehicleType: " + vehicleType);
        System.out.println("model: " + model);
        System.out.println("Driver ID: " + driverId);
        System.out.println("Number of Passengers: " + numberOfPassengers);
        System.out.println("Price Per Km: " + pricePerKm);
        System.out.println("status: " + status);
        System.out.println("Price Per Km: " + pricePerKm);

        try {
            String message = vehicleService.updateVehicle(vehicle);

            if ("success".equals(message)) {
                ResponseUtil.setResponseMessage(request, "success", "Vehicle updated successfully!");
            } else {
                ResponseUtil.setResponseMessage(request, "error", message);
            }
            response.sendRedirect(request.getContextPath() + "/vehicles");
        } catch (Exception e) {
            e.printStackTrace();
            ResponseUtil.setResponseMessage(request, "error", "Failed to update vehicle.");
        }
    }

    private void deleteVehicle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String vehicleId = request.getParameter("id");
        System.out.println(vehicleId);

        if (vehicleId != null && !vehicleId.isEmpty()) {
            boolean isDeleted = vehicleService.deleteVehicle(Integer.parseInt(vehicleId));

            if (isDeleted) {
                ResponseUtil.setResponseMessage(request, "success", "Vehicle deleted successfully!");
            } else {
                ResponseUtil.setResponseMessage(request, "error", "Failed to delete vehicle.");
            }
            response.sendRedirect(request.getContextPath() + "/vehicles");
        }
    }

}
