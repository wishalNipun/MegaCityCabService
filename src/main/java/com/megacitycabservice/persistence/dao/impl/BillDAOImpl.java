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
        List<Bill> bills = new ArrayList<>();
        String query = "SELECT b.id, bk.booking_number, bk.customer_id, b.base_fee, " +
                "b.tax_percentage, b.tax_price, b.discount, b.total_amount, b.created_date " +
                "FROM bills b JOIN bookings bk ON b.booking_id = bk.id ORDER BY b.created_date DESC";

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Bill bill = new Bill();
                bill.setId(rs.getInt("id"));
                bill.setBookingNumber(rs.getString("booking_number"));
                bill.setCustomerId(rs.getString("customer_id"));
                bill.setBaseFee(rs.getBigDecimal("base_fee"));
                bill.setTaxPercentage(rs.getBigDecimal("tax_percentage"));
                bill.setTaxPrice(rs.getBigDecimal("tax_price"));
                bill.setDiscount(rs.getBigDecimal("discount"));
                bill.setTotalAmount(rs.getBigDecimal("total_amount"));
                bill.setCreatedDate(rs.getTimestamp("created_date"));

                bills.add(bill);
            }
        }
        return bills;
    }
}
