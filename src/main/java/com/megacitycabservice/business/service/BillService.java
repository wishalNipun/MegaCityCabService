package com.megacitycabservice.business.service;

import com.megacitycabservice.model.Bill;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface BillService {
    public String addBill(String bookingId, double fee,double TaxPercentage,double discount, double totalAmount) throws SQLException;
    public List<Bill> getAllBills() throws SQLException;
}
