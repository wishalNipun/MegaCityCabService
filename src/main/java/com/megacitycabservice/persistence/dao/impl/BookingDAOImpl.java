package com.megacitycabservice.persistence.dao.impl;

import com.megacitycabservice.model.Booking;
import com.megacitycabservice.persistence.dao.BookingDAO;
import com.megacitycabservice.persistence.dao.CustomerDAO;
import com.megacitycabservice.util.DBConnection;

import java.sql.*;
import java.util.List;

public class BookingDAOImpl implements BookingDAO {

    private final Connection conn;
    private CustomerDAO customerDAO;

    public BookingDAOImpl() throws SQLException, ClassNotFoundException {
        conn = DBConnection.getDbConnection().getConnection();
        customerDAO = new CustomerDAOImpl();
    }

    @Override
    public String generateBookId() {
        String latestId = this.getLatestBookId();

        if (latestId == null) {
            return "BOOK-00001";
        }

        int numericPart = Integer.parseInt(latestId.substring(5));
        numericPart++;

        return String.format("BOOK-%05d", numericPart);
    }

    @Override
    public String getLatestBookId() {

        try {
            String latestId = null;
            String sql = "SELECT booking_number FROM bookings ORDER BY id DESC LIMIT 1";

            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                latestId = rs.getString("booking_number");
            }

            return latestId;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String addBooking(Booking booking, String[] vehicles, String username) {
        String bookingId = generateBookId(); // Generate booking number (string)
        String bookingSql = "INSERT INTO bookings (booking_number, customer_id, pickup_location, drop_location, distance_km, fee, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String vehicleBookingSql = "INSERT INTO booking_vehicles (booking_id, vehicle_id) VALUES (?, ?)";

        try {
            conn.setAutoCommit(false);


            String customerIdByUsername = customerDAO.getCustomerIdByUsername(username);
            PreparedStatement bookingStmt = conn.prepareStatement(bookingSql, Statement.RETURN_GENERATED_KEYS);
            bookingStmt.setString(1, bookingId);
            bookingStmt.setString(2, customerIdByUsername);
            bookingStmt.setString(3, booking.getPickupLocation());
            bookingStmt.setString(4, booking.getDropLocation());
            bookingStmt.setDouble(5, booking.getDistanceKm());
            bookingStmt.setDouble(6, booking.getFee());
            bookingStmt.setString(7, "PENDING");
            bookingStmt.executeUpdate();


            ResultSet generatedKeys = bookingStmt.getGeneratedKeys();
            int generatedBookingId = 0;
            if (generatedKeys.next()) {
                generatedBookingId = generatedKeys.getInt(1);
            }


            PreparedStatement vehicleBookingStmt = conn.prepareStatement(vehicleBookingSql);

            for (String vehicleId : vehicles) {
                System.out.println("x2222   "+vehicleId);
                vehicleBookingStmt.setInt(1, generatedBookingId);
                vehicleBookingStmt.setInt(2, Integer.parseInt(vehicleId));
                vehicleBookingStmt.addBatch();
            }
            vehicleBookingStmt.executeBatch();

            // Commit transaction
            conn.commit();
            return "success";
        } catch (SQLException e) {
            try {
                conn.rollback(); // Rollback if there's any error
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return "Error: " + e.getMessage();
        }
    }


    @Override
    public List<Booking> getAllBookings() {
        return List.of();
    }

    @Override
    public String updateBooking(Booking booking) {
        return "";
    }

    @Override
    public Boolean deleteBooking(int id) {
        return null;
    }
}
