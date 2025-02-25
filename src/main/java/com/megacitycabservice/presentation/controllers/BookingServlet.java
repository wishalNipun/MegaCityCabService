package com.megacitycabservice.presentation.controllers;

import com.megacitycabservice.business.service.BookingService;
import com.megacitycabservice.business.service.VehicleService;
import com.megacitycabservice.business.service.impl.BookingServiceImpl;
import com.megacitycabservice.business.service.impl.VehicleServiceImpl;
import com.megacitycabservice.model.Booking;
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
    private BookingService bookingService;


    @Override
    public void init() {
        try {
            System.out.println("Boooking");
            vehicleService = new VehicleServiceImpl();
            bookingService = new BookingServiceImpl();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        System.out.println("post hit");
        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            System.out.println("delete hit");
            deleteBooking(request, response);
        } else {
            String bookID = request.getParameter("id");

            if (bookID == null || bookID.isEmpty()) {
                System.out.println("add hit");
                addBooking(request, response);
            } else {
                System.out.println("update hit");
                updateBooking(request, response);
            }
        }

    }

    private void addBooking(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String vehicleIdsString = request.getParameter("vehicleIds");
        String pickUpLocation = request.getParameter("pickUpLocation");
        String dropLocation = request.getParameter("dropLocation");
        String distanceStr = request.getParameter("distance");
        String baseFeeStr = request.getParameter("baseFee");

        double distance = Double.parseDouble(distanceStr);
        double baseFee = Double.parseDouble(baseFeeStr);

        Booking booking = new Booking();
        booking.setPickupLocation(pickUpLocation);
        booking.setDropLocation(dropLocation);
        booking.setDistanceKm(distance);
        booking.setFee(baseFee);

        String username = (String) request.getSession().getAttribute("username");

        try {
            String[] vehicleIds = vehicleIdsString.split(",");
            String message = bookingService.addBooking(booking, vehicleIds, username);
            System.out.println(message);

            if ("success".equals(message)) {

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                String jsonResponse = "{\"alertType\":\"" + "success" + "\", \"message\":\"" + message + "\"}";
                response.getWriter().write(jsonResponse);
                response.getWriter().flush();


            } else {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                String jsonResponse = "{\"alertType\":\"" + "error" + "\", \"message\":\"" + message + "\"}";
                response.getWriter().write(jsonResponse);
                response.getWriter().flush();

            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            String jsonResponse = "{\"alertType\":\"" + "error" + "\", \"message\":\"Failed to Place Booking.\"}";
            response.getWriter().write(jsonResponse);
            response.getWriter().flush();

        }
    }

    private void updateBooking(HttpServletRequest request, HttpServletResponse response) {
    }

    private void deleteBooking(HttpServletRequest request, HttpServletResponse response) {
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