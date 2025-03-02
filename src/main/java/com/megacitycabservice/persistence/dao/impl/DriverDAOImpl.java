package com.megacitycabservice.persistence.dao.impl;

import com.megacitycabservice.model.Driver;
import com.megacitycabservice.persistence.dao.DriverDAO;
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
    public String insert(Driver driver) {
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
        return "success";
    }

    @Override
    public List<Driver> getAll() {
        List<Driver> driverList = new ArrayList<>();
        String sql = "SELECT * FROM drivers order by updated_date DESC";
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
                        rs.getTimestamp("created_date"),
                        rs.getTimestamp("updated_date")
                );
                driverList.add(driver);
            }
            return driverList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String update(Driver driver) {
        String checkSql = "SELECT COUNT(*) FROM drivers WHERE id = ?";
        String checkNicSql = "SELECT COUNT(*) FROM drivers WHERE nic = ? AND id != ?";
        String checkLicenseSql = "SELECT COUNT(*) FROM drivers WHERE license_number = ? AND id != ?";
        String updateSql = "UPDATE drivers SET name = ?, nic = ?, date_of_birth = ?, address = ?, license_number = ?, contact_number = ?, status = ?, updated_date = NOW() WHERE id = ?";

        try {

            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setInt(1, driver.getId());
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) == 0) {
                        return "Error: Driver not found.";
                    }
                }
            }


            try (PreparedStatement checkNicStmt = conn.prepareStatement(checkNicSql)) {
                checkNicStmt.setString(1, driver.getNic());
                checkNicStmt.setInt(2, driver.getId());
                try (ResultSet rsNic = checkNicStmt.executeQuery()) {
                    if (rsNic.next() && rsNic.getInt(1) > 0) {
                        return "Error: Another driver with this NIC already exists.";
                    }
                }
            }


            try (PreparedStatement checkLicenseStmt = conn.prepareStatement(checkLicenseSql)) {
                checkLicenseStmt.setString(1, driver.getLicenseNumber());
                checkLicenseStmt.setInt(2, driver.getId());
                try (ResultSet rsLicense = checkLicenseStmt.executeQuery()) {
                    if (rsLicense.next() && rsLicense.getInt(1) > 0) {
                        return "Error: Another driver with this License Number already exists.";
                    }
                }
            }


            try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                updateStmt.setString(1, driver.getName());
                updateStmt.setString(2, driver.getNic());
                updateStmt.setDate(3, new java.sql.Date(driver.getDateOfBirth().getTime()));
                updateStmt.setString(4, driver.getAddress());
                updateStmt.setString(5, driver.getLicenseNumber());
                updateStmt.setString(6, driver.getContactNumber());
                updateStmt.setString(7, driver.getStatus());
                updateStmt.setInt(8, driver.getId());

                int rowsAffected = updateStmt.executeUpdate();
                if (rowsAffected > 0) {
                    return "success";
                } else {
                    return "Error: Driver update failed.";
                }
            }
        } catch (SQLException e) {
            return "Database Error: " + e.getMessage();
        }
    }

    @Override
    public Boolean delete(Integer id) {
        String query = "DELETE FROM drivers WHERE id = ?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, id);
            int executeUpdate = preparedStatement.executeUpdate();
            return executeUpdate > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Driver> getAllAvailableDrivers() {
        List<Driver> availableDriverList = new ArrayList<>();
        String sql = "SELECT * FROM drivers WHERE status = 'AVAILABLE' ORDER BY updated_date DESC";
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
                        rs.getTimestamp("created_date"),
                        rs.getTimestamp("updated_date")
                );
                availableDriverList.add(driver);
            }
            return availableDriverList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Driver getDriverById(int driverId)  {
        Driver driver = null;
        String sql = "SELECT * FROM drivers WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, driverId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                driver = new Driver();
                driver.setId(rs.getInt("id"));
                driver.setName(rs.getString("name"));
                driver.setNic(rs.getString("nic"));
                driver.setAddress(rs.getString("address"));
                driver.setLicenseNumber(rs.getString("license_number"));
                driver.setDateOfBirth(rs.getDate("date_of_birth"));
                driver.setContactNumber(rs.getString("contact_number"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return driver;
    }

}
