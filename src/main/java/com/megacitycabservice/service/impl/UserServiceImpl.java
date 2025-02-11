package com.megacitycabservice.service.impl;

import com.megacitycabservice.dao.UserDAO;
import com.megacitycabservice.dao.impl.UserDAOImpl;
import com.megacitycabservice.model.User;
import com.megacitycabservice.service.UserService;

import java.sql.SQLException;

public class UserServiceImpl implements UserService {
    private UserDAO userDAO;

    public UserServiceImpl() throws SQLException, ClassNotFoundException {
        this.userDAO = new UserDAOImpl();
    }

    @Override
    public User validateUser(String username, String password) throws SQLException {
        User user = userDAO.getUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}
