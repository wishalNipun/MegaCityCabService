package com.megacitycabservice.service.impl;

import com.megacitycabservice.dao.CustomerDAO;
import com.megacitycabservice.dao.impl.CustomerDAOImpl;
import com.megacitycabservice.service.CustomerService;

import java.sql.SQLException;

public class CustomerServiceImpl implements CustomerService {
    CustomerDAO customerDAO;
    public CustomerServiceImpl() throws SQLException, ClassNotFoundException {
        this.customerDAO = new CustomerDAOImpl(); // ✅ Initialize the DAO
    }
    @Override
    public Boolean registerCustomerWithUser(String name, String nic, String address, String contactNumber, String username, String password) {
        return customerDAO.registerCustomerWithUser(name, nic, address, contactNumber, username, password);
    }
}
