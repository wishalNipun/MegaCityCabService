package com.megacitycabservice.persistence.dao.impl;

import com.megacitycabservice.model.Bill;
import com.megacitycabservice.persistence.dao.BillDAO;
import com.megacitycabservice.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BillDAOImpl implements BillDAO {

    private final Connection connection;

    public BillDAOImpl() throws SQLException, ClassNotFoundException {
        this.connection = DBConnection.getDbConnection().getConnection();
    }

    @Override
    public String addBill(Bill bill) throws SQLException {
        String query = "INSERT INTO bills (booking_id, base_fee, tax_percentage,tax_price, discount, total_amount) VALUES (?, ?, ?, ?, ?,?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, bill.getBookingId());
            stmt.setBigDecimal(2, bill.getBaseFee());
            stmt.setBigDecimal(3, bill.getTaxPercentage());
            stmt.setBigDecimal(4, bill.getTaxPrice());
            stmt.setBigDecimal(5, bill.getDiscount());
            stmt.setBigDecimal(6, bill.getTotalAmount());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                return "success";
            }
        }
        return "Error: Failed to add bill.";
    }

    @Override
    public List<Bill> getAllBills() throws SQLException {
        String query = "SELECT * FROM bills  ORDER BY createdDate DESC";
        List<Bill> bills = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Bill bill = new Bill();
                bill.setId(rs.getInt("id"));
                bill.setBookingId(rs.getInt("booking_id"));
                bill.setBaseFee(rs.getBigDecimal("base_fee"));
                bill.setTaxPercentage(rs.getBigDecimal("tax_percentage"));
                bill.setTaxPrice(rs.getBigDecimal("tax_price"));
                bill.setDiscount(rs.getBigDecimal("discount"));
                bill.setTotalAmount(rs.getBigDecimal("total_amount"));
                bills.add(bill);
            }
        }
        return bills;
    }
}
