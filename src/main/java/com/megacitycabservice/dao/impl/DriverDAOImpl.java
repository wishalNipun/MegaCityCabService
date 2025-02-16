package com.megacitycabservice.dao.impl;

import com.megacitycabservice.dao.DriverDAO;
import com.megacitycabservice.model.Driver;
import com.megacitycabservice.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DriverDAOImpl implements DriverDAO {

    private final Connection conn;

    public DriverDAOImpl() throws SQLException, ClassNotFoundException {
        this.conn = DBConnection.getDbConnection().getConnection();
    }

    @Override
    public String addDriver(Driver driver) {
        try {

            String checkNicSql = "SELECT COUNT(*) FROM drivers WHERE nic = ?";
            try (PreparedStatement checkNicStmt = conn.prepareStatement(checkNicSql)) {
                checkNicStmt.setString(1, driver.getNic());
                try (ResultSet rsNic = checkNicStmt.executeQuery()) {
                    if (rsNic.next() && rsNic.getInt(1) > 0) {
                        return "Driver with this NIC already exists.";
                    }
                }
            }

            String checkLicenseSql = "SELECT COUNT(*) FROM drivers WHERE license_number = ?";
            try (PreparedStatement checkLicenseStmt = conn.prepareStatement(checkLicenseSql)) {
                checkLicenseStmt.setString(1, driver.getLicenseNumber());
                try (ResultSet rsLicense = checkLicenseStmt.executeQuery()) {
                    if (rsLicense.next() && rsLicense.getInt(1) > 0) {
                        return "Driver with this Driving License Number already exists.";
                    }
                }
            }

            String insertSql = "INSERT INTO drivers (name, nic, date_of_birth, address, license_number, contact_number, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setString(1, driver.getName());
                insertStmt.setString(2, driver.getNic());
                insertStmt.setDate(3, new java.sql.Date(driver.getDateOfBirth().getTime()));
                insertStmt.setString(4, driver.getAddress());
                insertStmt.setString(5, driver.getLicenseNumber());
                insertStmt.setString(6, driver.getContactNumber());
                insertStmt.setString(7, driver.getStatus());

                insertStmt.executeUpdate();
            }

        } catch (SQLException e) {
            return "Error: " + e.getMessage();
        }
        return "Driver Saved Successfully.";
    }


    @Override
    public List<Driver> getAllDrivers() {
        List<Driver> driverList = new ArrayList<>();
        String sql = "SELECT * FROM drivers";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Driver driver = new Driver(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("nic"),
                        rs.getDate("date_of_birth"),
                        rs.getString("address"),
                        rs.getString("license_number"),
                        rs.getString("contact_number"),
                        rs.getString("status"),
                        rs.getDate("created_date"),
                        rs.getDate("updated_date")
                );
                driverList.add(driver);
            }
            return driverList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String updateDriver(Driver driver) {
        return null;
    }

    @Override
    public Boolean deleteDriver(int id) {
        return null;
    }
}
