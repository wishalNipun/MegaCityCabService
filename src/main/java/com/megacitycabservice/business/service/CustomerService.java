package com.megacitycabservice.business.service;

import com.megacitycabservice.model.Customer;

import java.util.List;

public interface CustomerService {
    Boolean registerCustomerWithUser(String name,String nic,String address,String contactNumber, String username, String password);
    List<Customer> getAllCustomers();
    boolean deleteCustomer(String customerId);
    String updateCustomer(Customer customer);
}
