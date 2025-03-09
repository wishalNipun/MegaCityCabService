package com.megacitycabservice.business.service.impl;

import com.megacitycabservice.model.User;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceTest {
    private static UserServiceImpl userService;

    @BeforeAll
    public static void setUp() throws SQLException, ClassNotFoundException {
        userService = new UserServiceImpl();
    }

    @Test
    @Order(1)
    public void testAddUser() {
        User user = new User();
        user.setUsername( "testUser");
        user.setPassword("password123");
        user.setRole("ADMIN");
        String result = userService.addUser(user);
        assertEquals("success", result, "User should be added successfully");
        System.out.println("User added successfully");
    }

    @Test
    @Order(2)
    public void testValidateUser() throws SQLException {
        User user = userService.validateUser("testUser", "password123");
        assertNotNull(user, "User should be validated successfully");
        assertEquals("testUser", user.getUsername());
        System.out.println("User validated successfully");
    }

    @Test
    @Order(3)
    public void testGetAllUsers() {
        List<User> users = userService.getAllUsers();
        assertNotNull(users, "User list should not be null");
        assertTrue(users.size() > 0, "There should be at least one user in the database");
        System.out.println("Get All Users successfully");
    }

    @Test
    @Order(4)
    public void testUpdateUser() {
        User user = new User(27, "testUser", "newPassword123", "CUSTOMER");
        String result = userService.updateUser(user);
        assertEquals("success", result, "User should be updated successfully");
        System.out.println("User Updated successfully");
    }

    @Test
    @Order(5)
    public void testDeleteUser() {
        String result = userService.deleteUser(26);
        assertEquals("success", result, "User should be deleted successfully");
        System.out.println("User Deleted successfully");
    }
}
