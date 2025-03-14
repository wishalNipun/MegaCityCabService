package com.megacitycabservice.persistence.dao;

import com.megacitycabservice.model.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerDAO extends CrudDAO<Customer,String>,SuperDAO{
    String getLatestCustomerId() ;
    String generateCustomerId() ;
    String registerCustomerWithUser(String name,String nic,String address,String contactNumber, String username, String password);
    String getCustomerIdByUsername(String username);
    Customer getCustomerById(String customerId);
}
