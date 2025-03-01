package com.megacitycabservice.persistence.dao.impl;

import com.megacitycabservice.model.Booking;
import com.megacitycabservice.model.Vehicle;
import com.megacitycabservice.persistence.dao.BookingDAO;
import com.megacitycabservice.persistence.dao.CustomerDAO;
import com.megacitycabservice.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
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
                vehicleBookingStmt.setInt(1, generatedBookingId);
                vehicleBookingStmt.setInt(2, Integer.parseInt(vehicleId));
                vehicleBookingStmt.addBatch();
            }
            vehicleBookingStmt.executeBatch();


            conn.commit();
            return "success";
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return "Error: " + e.getMessage();
        }
    }


    @Override
    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings where status IN ('PENDING', 'CONFIRMED', 'CANCELLED') order by updated_date DESC";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Booking booking = new Booking();
                booking.setId(rs.getInt("id"));
                booking.setBookingNumber(rs.getString("booking_number"));
                booking.setCustomerId(rs.getString("customer_id"));
                booking.setPickupLocation(rs.getString("pickup_location"));
                booking.setDropLocation(rs.getString("drop_location"));
                booking.setDistanceKm(rs.getDouble("distance_km"));
                booking.setFee(rs.getDouble("fee"));
                booking.setStatus(rs.getString("status"));
                booking.setCreatedDate(rs.getTimestamp("created_date"));
                booking.setUpdatedDate(rs.getTimestamp("updated_date"));
                bookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookings;
    }

    @Override
    public List<Booking> getBookingsByCustomerId(String customerId) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE customer_id = ? ORDER BY updated_date DESC";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, customerId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Booking booking = new Booking();
                    booking.setId(rs.getInt("id"));
                    booking.setBookingNumber(rs.getString("booking_number"));
                    booking.setCustomerId(rs.getString("customer_id"));
                    booking.setPickupLocation(rs.getString("pickup_location"));
                    booking.setDropLocation(rs.getString("drop_location"));
                    booking.setDistanceKm(rs.getDouble("distance_km"));
                    booking.setFee(rs.getDouble("fee"));
                    booking.setStatus(rs.getString("status"));
                    booking.setCreatedDate(rs.getTimestamp("created_date"));
                    booking.setUpdatedDate(rs.getTimestamp("updated_date"));
                    bookings.add(booking);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }


    @Override
    public String updateBooking(Booking booking) {
        String sql = "UPDATE bookings SET status = ?, updated_date = NOW() WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, booking.getStatus());
            stmt.setInt(2, booking.getId());
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                return "success";
            } else {
                return "Error: Booking not found.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    @Override
    public boolean doesBookingExist(int bookingId) {
        String sql = "SELECT COUNT(*) FROM bookings WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bookingId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Integer doesBookingExistCheck(String bookingNumber) {
        String sql = "SELECT id FROM bookings WHERE booking_number = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, bookingNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id"); // Return the booking ID
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if no booking found
    }
    @Override
    public void updateBookingStatus(int bookingId, String status) {
        String sql = "UPDATE bookings SET status = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, bookingId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBookingStatus(int bookingId) {
        String sql = "SELECT status FROM bookings WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bookingId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("status");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public List<Vehicle> getVehiclesByBookingNumber(String bookingNumber) {
        List<Vehicle> vehicleList = new ArrayList<>();
        String sql = "SELECT v.id, v.vehicle_type, v.model, v.plate_number, v.number_of_passenger, " +
                "v.price_per_km, v.status, v.img, bv.assigned_date, v.driver_id " +
                "FROM booking_vehicles bv " +
                "JOIN vehicles v ON bv.vehicle_id = v.id " +
                "JOIN bookings b ON bv.booking_id = b.id " +
                "WHERE b.booking_number = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, bookingNumber);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Vehicle vehicle = new Vehicle();
                vehicle.setId(rs.getInt("id"));
                vehicle.setVehicleType(rs.getString("vehicle_type"));
                vehicle.setModel(rs.getString("model"));
                vehicle.setPlateNumber(rs.getString("plate_number"));
                vehicle.setNumberOfPassengers(rs.getInt("number_of_passenger"));
                vehicle.setPricePerKm(rs.getDouble("price_per_km"));
                vehicle.setStatus(rs.getString("status"));
                vehicle.setImg(rs.getBytes("img"));
                vehicle.setAssignedDate(rs.getTimestamp("assigned_date"));
                vehicle.setDriverId(rs.getInt("driver_id"));
                vehicleList.add(vehicle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicleList;
    }

    @Override
    public List<Booking> getBookingsByStatus(String status) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE status = ? ORDER BY updated_date DESC";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Booking booking = new Booking();
                    booking.setId(rs.getInt("id"));
                    booking.setBookingNumber(rs.getString("booking_number"));
                    booking.setCustomerId(rs.getString("customer_id"));
                    booking.setPickupLocation(rs.getString("pickup_location"));
                    booking.setDropLocation(rs.getString("drop_location"));
                    booking.setDistanceKm(rs.getDouble("distance_km"));
                    booking.setFee(rs.getDouble("fee"));
                    booking.setStatus(rs.getString("status"));
                    booking.setCreatedDate(rs.getTimestamp("created_date"));
                    booking.setUpdatedDate(rs.getTimestamp("updated_date"));
                    bookings.add(booking);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookings;
    }

}
