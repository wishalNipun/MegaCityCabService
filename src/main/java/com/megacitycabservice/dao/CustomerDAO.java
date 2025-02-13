package com.megacitycabservice.dao;

import com.megacitycabservice.model.Customer;

import java.sql.SQLException;

public interface CustomerDAO {
    String getLatestCustomerId() ;
    String generateCustomerId() ;
    Boolean registerCustomerWithUser(String name,String nic,String address,String contactNumber, String username, String password) ;
}
