package com.megacitycabservice.persistence.dao;

import com.megacitycabservice.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO {
    User getUserByUsername(String username) throws SQLException;
    User getUserById(int id) throws SQLException;
    boolean addUser(User user) throws SQLException;
    List<User> getAllUsers();
    boolean deleteUser(int id) throws SQLException;
    boolean updateUser(User user) throws SQLException;
}
