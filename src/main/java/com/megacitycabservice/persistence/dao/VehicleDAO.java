package com.megacitycabservice.persistence.dao;

import com.megacitycabservice.model.Vehicle;

import java.util.List;

public interface VehicleDAO {
    String addVehicle(Vehicle vehicle);
    List<Vehicle> getAllVehicles();
    String updateVehicle(Vehicle vehicle);
    Boolean deleteVehicle(int id);
}
