package com.megacitycabservice.business.service.impl;

import com.megacitycabservice.persistence.dao.DAOFactory;
import com.megacitycabservice.persistence.dao.UserDAO;
import com.megacitycabservice.model.User;
import com.megacitycabservice.business.service.UserService;
import com.megacitycabservice.util.Validation.UserValidation;

import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDAO userDAO;

    public UserServiceImpl() throws SQLException, ClassNotFoundException {
        userDAO = (UserDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.USER);
    }

    @Override
    public User validateUser(String username, String password) throws SQLException {
        User user = userDAO.getUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    @Override
    public String addUser(User user) {

        try {
            String validationMessage = UserValidation.validateUser(user.getUsername(),user.getPassword());
            if (validationMessage != null) {
                return validationMessage;
            }

            User userByUsername = userDAO.getUserByUsername(user.getUsername());
            if (userByUsername == null){
                user.setRole("ADMIN");
                return userDAO.addUser(user);
            }else{
                return "Error: Username Already exist!.";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    public String deleteUser(int id) {

        try {
            User userByUsername = userDAO.getUserById(id);
            if (userByUsername != null){
                userDAO.deleteUser(id);
                return "success";
            }else{
                return "Error: User Not Found!.";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public String updateUser(User user) {
        try {

            String validationMessage = UserValidation.validateUser(user.getUsername(),user.getPassword());
            if (validationMessage != null) {
                return validationMessage;
            }

            User existingUser = userDAO.getUserById(user.getId());
            if (existingUser == null) {
                return "Error: User Not Found!";
            }

            User userWithSameUsername = userDAO.getUserByUsername(user.getUsername());
            if (userWithSameUsername != null && userWithSameUsername.getId() != user.getId()) {
                return "Error: Username Already Exists!";
            }

            user.setRole(existingUser.getRole());
            return userDAO.updateUser(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
