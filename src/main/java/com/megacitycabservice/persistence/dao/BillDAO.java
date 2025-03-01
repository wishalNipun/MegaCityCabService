package com.megacitycabservice.persistence.dao;

import com.megacitycabservice.model.Bill;

import java.sql.SQLException;
import java.util.List;

public interface BillDAO {
    public String addBill(Bill bill) throws SQLException;
    public List<Bill> getAllBills() throws SQLException;
}
