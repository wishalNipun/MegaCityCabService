package com.megacitycabservice.service;

import com.megacitycabservice.model.Customer;

public interface CustomerService {
    Boolean registerCustomerWithUser(String name,String nic,String address,String contactNumber, String username, String password);
}
