package com.megacitycabservice.business.service.impl;

import com.megacitycabservice.model.Customer;
import com.megacitycabservice.persistence.dao.CustomerDAO;
import com.megacitycabservice.persistence.dao.DAOFactory;
import com.megacitycabservice.business.service.CustomerService;
import com.megacitycabservice.util.Validation.CustomerValidation;

import java.sql.SQLException;
import java.util.List;

public class CustomerServiceImpl implements CustomerService {
    CustomerDAO customerDAO;
    public CustomerServiceImpl() throws SQLException, ClassNotFoundException {
        this.customerDAO = (CustomerDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    }
    @Override
    public String registerCustomerWithUser(String name, String nic, String address, String contactNumber, String username, String password) {
        String validationMessage = CustomerValidation.validateCustomer(name, nic, address, contactNumber, username, password);
        if (validationMessage != null) {
            return validationMessage;
        }
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
        String validationMessage = CustomerValidation.validateCustomer(customer.getName(), customer.getNic(),customer.getAddress(), customer.getContactNumber(), customer.getUsername(), customer.getPassword());
        if (validationMessage != null) {
            return validationMessage;
        }
       return customerDAO.update(customer);
    }

    @Override
    public Customer getCustomerById(String customerId) {
      return customerDAO.getCustomerById(customerId);
    }

}
