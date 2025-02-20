package com.megacitycabservice.business.service;

import com.megacitycabservice.model.User;

import java.sql.SQLException;

public interface UserService {
    User validateUser(String username, String password) throws SQLException;
}
