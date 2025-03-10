package com.megacitycabservice.business.service.impl;

import com.megacitycabservice.model.Vehicle;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VehicleServiceImplTest {

    private static VehicleServiceImpl vehicleService;

    @BeforeAll
    public static void setUp() throws SQLException, ClassNotFoundException {

        vehicleService = new VehicleServiceImpl();
    }

    @Test
    @Order(1)
    public void testAddVehicle() {
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleType("Car");
        vehicle.setModel("Toyota Prius");
        vehicle.setPlateNumber("WP XYZ 1234");
        vehicle.setDriverId(14);
        vehicle.setNumberOfPassengers(4);
        vehicle.setPricePerKm(20.50);
        vehicle.setStatus("Available");

        String result = vehicleService.addVehicle(vehicle);
        assertEquals("success", result, "Vehicle should be added successfully");

    }

    @Test
    @Order(2)
    public void testGetAllVehicles() {
        List<Vehicle> vehicles = vehicleService.getAllVehicles();
        assertNotNull(vehicles, "Vehicle list should not be null");
        assertTrue(vehicles.size() > 0, "There should be at least one vehicle in the database");

        System.out.println("Get All Vehicles successfully");
    }

    @Test
    @Order(3)
    public void testUpdateVehicle() throws SQLException {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(10);
        vehicle.setVehicleType("CAR");
        vehicle.setModel("Honda Civic");
        vehicle.setPlateNumber("WP ZZZ 5678");
        vehicle.setDriverId(14);
        vehicle.setNumberOfPassengers(4);
        vehicle.setPricePerKm(22.00);
        vehicle.setStatus("Available");

        String result = vehicleService.updateVehicle(vehicle);
        assertEquals("success", result, "Vehicle should be updated successfully");

        System.out.println("Vehicle Updated successfully");
    }

    @Test
    @Order(4)
    public void testDeleteVehicle() {
        boolean result = vehicleService.deleteVehicle(10);
        assertTrue(result, "Vehicle should be deleted successfully");
        System.out.println("Vehicle deleted successfully");
    }

    @Test
    @Order(5)
    public void testGetAvailableVehicles() {
        List<Vehicle> availableVehicles = vehicleService.getAvailableVehicles();
        assertNotNull(availableVehicles, "Available vehicles list should not be null");

        System.out.println("Get available vehicles successfully");
    }

    @Test
    @Order(6)
    public void testGetVehicleCountByStatus() {
        Integer count = vehicleService.getVehicleCountByStatus("Available");
        assertNotNull(count, "Vehicle count should not be null");
        assertTrue(count >= 0, "Vehicle count should be non-negative");

        System.out.println("Vehicle count by status retrieved successfully");
    }
}