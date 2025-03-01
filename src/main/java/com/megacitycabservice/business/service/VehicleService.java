package com.megacitycabservice.business.service;

import com.megacitycabservice.model.Vehicle;

import java.util.List;

public interface VehicleService extends SuperBOService{
    String addVehicle(Vehicle vehicle);
    List<Vehicle> getAllVehicles();
    String updateVehicle(Vehicle vehicle);
    Boolean deleteVehicle(int id);
    List<Vehicle> getAvailableVehicles();
}
