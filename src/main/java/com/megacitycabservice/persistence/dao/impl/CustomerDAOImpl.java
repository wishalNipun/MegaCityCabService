package com.megacitycabservice.persistence.dao.impl;

import com.megacitycabservice.model.Customer;
import com.megacitycabservice.persistence.dao.CustomerDAO;
import com.megacitycabservice.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {

    private final Connection conn;

    public CustomerDAOImpl() throws SQLException, ClassNotFoundException {
        conn = DBConnection.getDbConnection().getConnection();
    }

    @Override
    public String getLatestCustomerId() {

        try {
            String latestId = null;
            String sql = "SELECT customer_id FROM customers ORDER BY customer_id DESC LIMIT 1";

            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                latestId = rs.getString("customer_id");
            }

            return latestId;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public String generateCustomerId() {
        String latestId = this.getLatestCustomerId();
        if (latestId == null) {
            return "C0000001";
        }

        int numericPart = Integer.parseInt(latestId.substring(1));
        numericPart++;

        return String.format("C%07d", numericPart);
    }

    @Override
    public Boolean registerCustomerWithUser(String name, String nic, String address, String contactNumber, String username, String password) {
        try {
            conn.setAutoCommit(false);

            System.out.println(name);
            String userSql = "INSERT INTO users (username, password, role) VALUES (?, ?, 'CUSTOMER')";
            PreparedStatement userStmt = conn.prepareStatement(userSql, Statement.RETURN_GENERATED_KEYS);
            userStmt.setString(1, username);
            userStmt.setString(2, password);

            int userRows = userStmt.executeUpdate();
            if (userRows == 0) {
                throw new SQLException("Failed to create user.");
            }

            ResultSet rs = userStmt.getGeneratedKeys();
            int userId = 0;
            if (rs.next()) {
                userId = rs.getInt(1);
            } else {
                throw new SQLException("Failed to retrieve user ID.");
            }

            String customerId = generateCustomerId();


            String customerSql = "INSERT INTO customers (customer_id, user_id, name, address, nic, contact_number) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement customerStmt = conn.prepareStatement(customerSql);
            customerStmt.setString(1, customerId);
            customerStmt.setInt(2, userId);
            customerStmt.setString(3, name);
            customerStmt.setString(4, address);
            customerStmt.setString(5, nic);
            customerStmt.setString(6, contactNumber);

            int customerRows = customerStmt.executeUpdate();
            if (customerRows == 0) {
                throw new SQLException("Failed to create customer.");
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getCustomerIdByUsername(String username) {
        String sql = "SELECT c.customer_id FROM customers c " +
                "JOIN users u ON c.user_id = u.id " +
                "WHERE u.username = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("customer_id");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching customer ID for username: " + username, e);
        }
        return null;
    }


    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> customerList = new ArrayList<>();
        String sql = "SELECT u.username, u.password, c.customer_id, c.user_id, c.name, c.nic, c.address, c.contact_number, c.created_date, c.updated_date " +
                "FROM customers c " +
                "JOIN users u ON c.user_id = u.id " +
                "ORDER BY c.updated_date DESC";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Customer customer = new Customer(
                        rs.getString("customer_id"),
                        rs.getInt("user_id"),  // Ensure user_id is retrieved correctly
                        rs.getString("name"),
                        rs.getString("nic"),
                        rs.getString("address"),
                        rs.getString("contact_number"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getTimestamp("created_date"),
                        rs.getTimestamp("updated_date")
                );
                customerList.add(customer);
            }
            return customerList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteCustomer(String customerId) {
        String deleteUserSQL = "DELETE FROM users WHERE id = (SELECT user_id FROM customers WHERE customer_id = ?)";

        try (PreparedStatement stmt = conn.prepareStatement(deleteUserSQL)) {
            stmt.setString(1, customerId);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting customer: " + e.getMessage(), e);
        }
    }


}
