package com.megacitycabservice.business.service.impl;

import com.megacitycabservice.model.Driver;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DriverServiceImplTest {

    private static DriverServiceImpl driverService;

    @BeforeAll
    public static void setUp() throws ClassNotFoundException, SQLException {

        driverService = new DriverServiceImpl();
    }

    @Test
    @Order(1)
    public void testAddDriver() {
        Driver driver = new Driver();
        driver.setName("TestDriver");
        driver.setNic("200012321116");
        driver.setAddress("Colombo");
        driver.setLicenseNumber("B1234567");
        driver.setContactNumber("0771234567");
        driver.setDateOfBirth(java.sql.Date.valueOf("1979-02-01"));
        driver.setStatus("Available");

        String result = driverService.addDriver(driver);
        assertEquals("success", result, "Driver should be added successfully");
        System.out.println("Driver added successfully");
    }

    @Test
    @Order(2)
    public void testGetAllDrivers() {
        List<Driver> drivers = driverService.getAllDrivers();
        assertNotNull(drivers, "The Driver List should not be null");
        assertTrue(drivers.size() > 0, "There should be at least one driver in the database");
        System.out.println("Get All Drivers successfully");
    }

    @Test
    @Order(3)
    public void testDeleteDriver() {
        boolean result = driverService.deleteDriver(13);
        assertTrue(result, "Driver should be deleted successfully");
        System.out.println("Driver Deleted successfully");
    }

    @Test
    @Order(4)
    public void testUpdateDriver() {
        Driver driver = new Driver();
        driver.setId(14);
        driver.setName("Updated Driver");
        driver.setAddress("Kandy");
        driver.setNic("992345678V");
        driver.setLicenseNumber("B7654321");
        driver.setContactNumber("0777654321");
        driver.setDateOfBirth(java.sql.Date.valueOf("1979-02-01"));
        driver.setStatus("Busy");

        String result = driverService.updateDriver(driver);
        assertEquals("success", result, "Driver should be updated successfully");
        System.out.println("Driver Updated successfully");
    }

    @Test
    @Order(5)
    public void testGetDriverById() {
        Driver result = driverService.getDriverById(14);
        assertNotNull(result, "The result should not be null");
        System.out.println("Get Driver By Id successfully");
    }
}
