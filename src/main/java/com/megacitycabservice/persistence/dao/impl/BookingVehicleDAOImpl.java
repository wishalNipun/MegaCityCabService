package com.megacitycabservice.persistence.dao.impl;

import com.megacitycabservice.persistence.dao.BookingVehicleDAO;
import com.megacitycabservice.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingVehicleDAOImpl implements BookingVehicleDAO {
    private Connection conn;

    public BookingVehicleDAOImpl() {
        try {
            this.conn = DBConnection.getDbConnection().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public List<Integer> getVehicleIdsByBookingId(int bookingId) {
        List<Integer> vehicleIds = new ArrayList<>();
        String sql = "SELECT vehicle_id FROM booking_vehicles WHERE booking_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bookingId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                vehicleIds.add(rs.getInt("vehicle_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicleIds;
    }
}
