package com.megacitycabservice.business.service.impl;

import com.megacitycabservice.persistence.dao.CustomerDAO;
import com.megacitycabservice.persistence.dao.impl.CustomerDAOImpl;
import com.megacitycabservice.business.service.CustomerService;

import java.sql.SQLException;

public class CustomerServiceImpl implements CustomerService {
    CustomerDAO customerDAO;
    public CustomerServiceImpl() throws SQLException, ClassNotFoundException {
        this.customerDAO = new CustomerDAOImpl();
    }
    @Override
    public Boolean registerCustomerWithUser(String name, String nic, String address, String contactNumber, String username, String password) {
        return customerDAO.registerCustomerWithUser(name, nic, address, contactNumber, username, password);
    }
}
