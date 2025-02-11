package com.megacitycabservice.dao;

import com.megacitycabservice.model.User;

import java.sql.SQLException;

public interface UserDAO {
    User getUserByUsername(String username) throws SQLException;
}
