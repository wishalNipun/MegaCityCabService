package com.megacitycabservice.persistence.dao.impl;

import com.megacitycabservice.model.Driver;
import com.megacitycabservice.model.Vehicle;
import com.megacitycabservice.persistence.dao.VehicleDAO;
import com.megacitycabservice.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class VehicleDAOImpl implements VehicleDAO {
    private final Connection conn;

    public VehicleDAOImpl() throws SQLException, ClassNotFoundException {
        this.conn = DBConnection.getDbConnection().getConnection();
    }

    @Override
    public String addVehicle(Vehicle vehicle) {

        try {

            String checkPlateSql = "SELECT COUNT(*) FROM vehicles WHERE plate_number = ?";
            try (PreparedStatement checkPlateStmt = conn.prepareStatement(checkPlateSql)) {
                checkPlateStmt.setString(1, vehicle.getPlateNumber());
                try (ResultSet rsPlate = checkPlateStmt.executeQuery()) {
                    if (rsPlate.next() && rsPlate.getInt(1) > 0) {
                        return "Vehicle with this plate number already exists.";
                    }
                }
            }

            String checkDriverSql = "SELECT COUNT(*) FROM vehicles WHERE driver_id = ?";
            try (PreparedStatement checkDriverStmt = conn.prepareStatement(checkDriverSql)) {
                checkDriverStmt.setInt(1, vehicle.getDriverId());
                try (ResultSet rsDriver = checkDriverStmt.executeQuery()) {
                    if (rsDriver.next() && rsDriver.getInt(1) > 0) {
                        return "Driver is already assigned to another vehicle.";
                    }
                }
            }

            String insertSql = "INSERT INTO vehicles (vehicle_type, model, plate_number, driver_id, number_of_passenger, price_per_km, status, img) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setString(1, vehicle.getVehicleType());
                insertStmt.setString(2, vehicle.getModel());
                insertStmt.setString(3, vehicle.getPlateNumber());
                insertStmt.setInt(4, vehicle.getDriverId());
                insertStmt.setInt(5, vehicle.getNumberOfPassengers());
                insertStmt.setDouble(6, vehicle.getPricePerKm());
                insertStmt.setString(7, vehicle.getStatus());
                insertStmt.setBytes(8, vehicle.getImg());

                insertStmt.executeUpdate();
                System.out.println("Vehicle added successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
        return "success";
    }


    @Override
    public List<Vehicle> getAllVehicles() {
        List<Vehicle> vehicleList = new ArrayList<>();
        String sql = "SELECT * FROM vehicles order by updated_date DESC";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                byte[] imgBytes = rs.getBytes("img");
                String imgBase64 = null;
                if (imgBytes != null) {

                    imgBase64 = Base64.getEncoder().encodeToString(imgBytes);
                }
                Vehicle vehicle = new Vehicle(
                        rs.getInt("id"),
                        rs.getString("vehicle_type"),
                        rs.getString("model"),
                        rs.getString("plate_number"),
                        rs.getInt("driver_id"),
                        rs.getInt("number_of_passenger"),
                        rs.getDouble("price_per_km"),
                        rs.getString("status"),
                        rs.getBytes("img"),
                        rs.getTimestamp("created_date"),
                        rs.getTimestamp("updated_date")
                );
                vehicle.setBase64Img(imgBase64);
                vehicleList.add(vehicle);
            }
            return vehicleList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String updateVehicle(Vehicle vehicle) {

        try {

            String checkPlateSql = "SELECT COUNT(*) FROM vehicles WHERE plate_number = ? AND id != ?";
            try (PreparedStatement checkPlateStmt = conn.prepareStatement(checkPlateSql)) {
                checkPlateStmt.setString(1, vehicle.getPlateNumber());
                checkPlateStmt.setInt(2, vehicle.getId());
                try (ResultSet rsPlate = checkPlateStmt.executeQuery()) {
                    if (rsPlate.next() && rsPlate.getInt(1) > 0) {
                        return "Vehicle with this plate number already exists.";
                    }
                }
            }


            String checkDriverSql = "SELECT COUNT(*) FROM vehicles WHERE driver_id = ? AND id != ?";
            try (PreparedStatement checkDriverStmt = conn.prepareStatement(checkDriverSql)) {
                checkDriverStmt.setInt(1, vehicle.getDriverId());
                checkDriverStmt.setInt(2, vehicle.getId());
                try (ResultSet rsDriver = checkDriverStmt.executeQuery()) {
                    if (rsDriver.next() && rsDriver.getInt(1) > 0) {
                        return "Driver is already assigned to another vehicle.";
                    }
                }
            }

            if(vehicle.getDriverId() == 0){
                return "Driver is not selected !.";
            }

            String insertSql;
            if (vehicle.getImg() != null) {
                insertSql = "UPDATE vehicles SET vehicle_type = ?, model = ?, plate_number = ?, driver_id = ?, number_of_passenger = ?, price_per_km = ?, status = ?, img = ?, updated_date = NOW() WHERE id = ?";
            } else {
                insertSql = "UPDATE vehicles SET vehicle_type = ?, model = ?, plate_number = ?, driver_id = ?, number_of_passenger = ?, price_per_km = ?, status = ?, updated_date = NOW() WHERE id = ?";
            }

            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setString(1, vehicle.getVehicleType());
                insertStmt.setString(2, vehicle.getModel());
                insertStmt.setString(3, vehicle.getPlateNumber());
                insertStmt.setInt(4, vehicle.getDriverId());
                insertStmt.setInt(5, vehicle.getNumberOfPassengers());
                insertStmt.setDouble(6, vehicle.getPricePerKm());
                insertStmt.setString(7, vehicle.getStatus());

                if (vehicle.getImg() != null) {
                    insertStmt.setBytes(8, vehicle.getImg());
                    insertStmt.setInt(9, vehicle.getId());
                } else {
                    insertStmt.setInt(8, vehicle.getId());
                }

                insertStmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
        return "success";
    }

    @Override
    public Boolean deleteVehicle(int id) {
        String query = "DELETE FROM vehicles WHERE id = ?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, id);
            int executeUpdate = preparedStatement.executeUpdate();
            return executeUpdate > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
