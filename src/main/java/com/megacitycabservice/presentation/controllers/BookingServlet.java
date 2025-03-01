package com.megacitycabservice.presentation.controllers;

import com.megacitycabservice.business.service.BookingService;
import com.megacitycabservice.business.service.VehicleService;
import com.megacitycabservice.business.service.impl.BookingServiceImpl;
import com.megacitycabservice.business.service.impl.VehicleServiceImpl;
import com.megacitycabservice.model.Booking;
import com.megacitycabservice.model.Vehicle;
import com.megacitycabservice.util.ResponseUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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





            String bookID = request.getParameter("id");

            if (bookID == null || bookID.isEmpty()) {
                System.out.println("add hit");
                addBooking(request, response);
            } else {
                System.out.println("update hit");
                updateBooking(request, response);
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
        String bookingId = request.getParameter("id");
        String status = request.getParameter("status");

        Booking booking = new Booking();
        booking.setId(Integer.parseInt(bookingId));
        booking.setStatus(status);
        System.out.println(booking.getId());

        try {
            String message = bookingService.updateBooking(booking);
            if ("success".equals(message)) {
                ResponseUtil.setResponseMessage(request, "success", "Booking updated successfully!");
            } else {
                ResponseUtil.setResponseMessage(request, "error", message);
            }
            response.sendRedirect(request.getContextPath() + "/bookings?action=viewBookings");
        } catch (Exception e) {
            e.printStackTrace();
            ResponseUtil.setResponseMessage(request, "error", "Failed to update Booking.");
        }

    }



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            String action = request.getParameter("action");

            if ("viewBookings".equals(action)) {
                System.out.println("view bookings");
                List<Booking> bookingList = bookingService.getAllBookings();
                request.setAttribute("bookingList", bookingList);
                request.getRequestDispatcher("/pages/adminReserveBookings.jsp").forward(request, response);
            } else if ("availableVehicles".equals(action)){

                List<Vehicle> vehicleList = vehicleService.getAvailableVehicles();

                if (vehicleList != null && !vehicleList.isEmpty()) {
                    request.setAttribute("availableVehicleList", vehicleList);
                }

                request.getRequestDispatcher("/pages/customerBookingManagement.jsp").forward(request, response);
            }else if ("getVehicleDetailBooking".equals(action)){
                getVehicleDetailBooking(request,response);
            }else if ("bookingDetail".equals(action)){

                String username = request.getParameter("username");
                List<Booking> bookingList = bookingService.getBookingsByUsername(username);
                request.setAttribute("bookingList", bookingList);
                request.getRequestDispatcher("/pages/BookingDetail.jsp").forward(request, response);
            }else if ("availablePayBookings".equals(action)){
                List<Booking> bookingList = bookingService.getBookingsByStatus("CONFIRMED");
                request.setAttribute("bookingList", bookingList);
                request.getRequestDispatcher("/pages/PaymentManagement.jsp").forward(request, response);
            }


        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void getVehicleDetailBooking(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String bookingNumber = request.getParameter("bookingNumber");

        if (bookingNumber == null || bookingNumber.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing booking number");
            return;
        }

        List<Vehicle> vehicles = bookingService.getVehiclesByBookingNumber(bookingNumber);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (vehicles != null && !vehicles.isEmpty()) {
            StringBuilder json = new StringBuilder("{\"vehicles\":[");

            for (int i = 0; i < vehicles.size(); i++) {
                Vehicle vehicle = vehicles.get(i);
                json.append("{")
                        .append("\"id\":\"").append(vehicle.getId()).append("\",")
                        .append("\"vehicleType\":\"").append(vehicle.getVehicleType()).append("\",")
                        .append("\"model\":\"").append(vehicle.getModel()).append("\",")
                        .append("\"plateNumber\":\"").append(vehicle.getPlateNumber()).append("\",")
                        .append("\"numberOfPassenger\":\"").append(vehicle.getNumberOfPassengers()).append("\",")
                        .append("\"pricePerKm\":\"").append(vehicle.getPricePerKm()).append("\",")
                        .append("\"status\":\"").append(vehicle.getStatus()).append("\",")
                        .append("\"driverId\":\"").append(vehicle.getDriverId()).append("\",")
                        .append("\"assignedDate\":\"").append(vehicle.getAssignedDate()).append("\"")
                        .append("}");

                if (i < vehicles.size() - 1) {
                    json.append(",");
                }
            }

            json.append("]}");
            response.getWriter().write(json.toString());
        } else {
            response.getWriter().write("{\"error\": \"No vehicles assigned to this booking\"}");
        }
    }

}