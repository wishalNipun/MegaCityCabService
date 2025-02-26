package com.megacitycabservice.persistence.dao;

import com.megacitycabservice.model.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerDAO {
    String getLatestCustomerId() ;
    String generateCustomerId() ;
    Boolean registerCustomerWithUser(String name,String nic,String address,String contactNumber, String username, String password);
    String getCustomerIdByUsername(String username);
    List<Customer> getAllCustomers();
    boolean deleteCustomer(String customerId);
    String updateCustomer(Customer customer);
}
