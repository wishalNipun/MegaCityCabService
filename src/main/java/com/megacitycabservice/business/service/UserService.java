package com.megacitycabservice.business.service;

import com.megacitycabservice.model.Customer;
import com.megacitycabservice.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserService extends SuperBOService{
    User validateUser(String username, String password) throws SQLException;
    String addUser(User user);
    List<User> getAllUsers();
    String deleteUser(int id);
    String updateUser(User user);
}
