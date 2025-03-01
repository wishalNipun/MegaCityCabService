package com.megacitycabservice.business.service.impl;

import com.megacitycabservice.model.Customer;
import com.megacitycabservice.persistence.dao.CustomerDAO;
import com.megacitycabservice.persistence.dao.impl.CustomerDAOImpl;
import com.megacitycabservice.business.service.CustomerService;

import java.sql.SQLException;
import java.util.List;

public class CustomerServiceImpl implements CustomerService {
    CustomerDAO customerDAO;
    public CustomerServiceImpl() throws SQLException, ClassNotFoundException {
        this.customerDAO = new CustomerDAOImpl();
    }
    @Override
    public Boolean registerCustomerWithUser(String name, String nic, String address, String contactNumber, String username, String password) {
        return customerDAO.registerCustomerWithUser(name, nic, address, contactNumber, username, password);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerDAO.getAll();
    }

    @Override
    public boolean deleteCustomer(String customerId) {
        return customerDAO.delete(customerId);
    }

    @Override
    public String updateCustomer(Customer customer) {
       return customerDAO.update(customer);
    }

    @Override
    public Customer getCustomerById(String customerId) {
      return customerDAO.getCustomerById(customerId);
    }

}
